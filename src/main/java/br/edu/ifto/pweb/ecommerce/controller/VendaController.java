package br.edu.ifto.pweb.ecommerce.controller;

import br.com.caelum.stella.boleto.*;
import br.com.caelum.stella.boleto.bancos.Bradesco;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.edu.ifto.pweb.ecommerce.model.entity.Cliente;
import br.edu.ifto.pweb.ecommerce.model.entity.ClientePessoaFisica;
import br.edu.ifto.pweb.ecommerce.model.entity.Venda;
import br.edu.ifto.pweb.ecommerce.model.repository.ClientePessoaFisicaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
@Transactional
@RequestMapping("vendas")
public class VendaController {
    private final VendaRepository repository;
    private final ClientePessoaFisicaRepository clientePessoaFisicaRepository;

    @Autowired
    public VendaController(VendaRepository repository, ClientePessoaFisicaRepository clientePessoaFisicaRepository) {
        this.repository = repository;
        this.clientePessoaFisicaRepository = clientePessoaFisicaRepository;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, ModelMap modelMap, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cliente cliente = clientePessoaFisicaRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Venda venda = repository.findById(id).orElseThrow();

        if (!venda.getCliente().getId().equals(cliente.getId())) {
            // HTTP 403 caso a venda não seja vinculada ao cliente
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        modelMap.addAttribute("venda", venda);
        return new ModelAndView("/vendas/detail", modelMap);
    }

    @GetMapping("/list")
    public ModelAndView list(ModelMap modelMap, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cliente cliente = clientePessoaFisicaRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        modelMap.addAttribute("vendas", repository.findByCliente(cliente));
        return new ModelAndView("/vendas/list", modelMap);
    }

    @GetMapping("/pagamento/{id}/selecionar")
    public ModelAndView paymentSelect(@PathVariable("id") Long id, ModelMap modelMap) {
        Venda venda = repository.findById(id).orElseThrow();
        modelMap.addAttribute("venda", venda);

        return new ModelAndView("/vendas/pagamento/selecionar", modelMap);
    }

    @GetMapping("/pagamento/{id}/{forma}")
    public ModelAndView paymentMethodSelected(@PathVariable("id") Long id,
                                              @PathVariable("forma") String forma, ModelMap modelMap) {
        Venda venda = repository.findById(id).orElseThrow();
        modelMap.addAttribute("venda", venda);

        return new ModelAndView(String.format("/vendas/pagamento/%s", forma), modelMap);
    }

    @GetMapping("/pagamento/{id}/boleto/gerar")
    public ResponseEntity<byte[]> paymentGerarBoleto(@PathVariable("id") Long id, ModelMap modelMap) {
        Venda venda = repository.findById(id).orElseThrow();
        ClientePessoaFisica clientePessoaFisica = clientePessoaFisicaRepository.findById(
                venda.getCliente().getId()).orElseThrow();
        LocalDateTime dataCompra = venda.getCreatedAt();
        LocalDateTime proximoDia = venda.getCreatedAt().plusDays(1);

        Datas datas = Datas.novasDatas()
                .comDocumento(dataCompra.getDayOfMonth(),
                        dataCompra.getMonthValue(),
                        dataCompra.getYear())
                .comProcessamento(dataCompra.getDayOfMonth(),
                        dataCompra.getMonthValue(),
                        dataCompra.getYear())
                .comVencimento(proximoDia.getDayOfMonth(),
                        proximoDia.getMonthValue(),
                        proximoDia.getYear());

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro("Av dos testes, 111 apto 333")
                .comBairro("Bairro Teste")
                .comCep("01234-111")
                .comCidade("São Paulo")
                .comUf("SP");

        //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("NightShop Comércio Eletrônico LTDA")
                .comAgencia("1824").comDigitoAgencia("4")
                .comCodigoBeneficiario("76000")
                .comDigitoCodigoBeneficiario("5")
                .comNumeroConvenio("1207113")
                .comCarteira("18")
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");

        Endereco enderecoPagador = Endereco.novoEndereco()
                .comLogradouro(venda.getEndereco().getLogradouro())
                .comBairro(venda.getEndereco().getBairro())
                .comCep(venda.getEndereco().getFormattedCep())
                .comCidade(venda.getEndereco().getLocalidade())
                .comUf(venda.getEndereco().getUf());

        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()
                .comNome(clientePessoaFisica.getNome())
                .comDocumento(clientePessoaFisica.getFormattedCpf())
                .comEndereco(enderecoPagador);

        Banco banco = new Bradesco();

        Boleto boleto = Boleto.novoBoleto()
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto(String.format(Locale.US, "%.2f", venda.total()))
                .comNumeroDoDocumento("1234")
                .comInstrucoes("Senhor(a) caixa,", "favor não receber após o vencimento.")
                .comLocaisDePagamento("Pagável em qualquer correspondente bancário até o vencimento");

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", MediaType.APPLICATION_PDF_VALUE);
        headers.set("Content-Disposition", "inline; filename=\"boleto.pdf\""); // to view in browser change attachment to inline

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(gerador.geraPDF());
    }
}
