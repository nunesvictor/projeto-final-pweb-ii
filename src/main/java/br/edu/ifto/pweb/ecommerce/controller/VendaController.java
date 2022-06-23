package br.edu.ifto.pweb.ecommerce.controller;

import br.edu.ifto.pweb.ecommerce.model.entity.*;
import br.edu.ifto.pweb.ecommerce.model.repository.ClientePessoaFisicaRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.FormaPagamentoRepository;
import br.edu.ifto.pweb.ecommerce.model.repository.VendaRepository;
import br.edu.ifto.pweb.ecommerce.utils.BoletoUtil;
import br.edu.ifto.pweb.ecommerce.utils.PercentUtil;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@Transactional
@RequestMapping("vendas")
public class VendaController {
    private final VendaRepository repository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final ClientePessoaFisicaRepository clientePessoaFisicaRepository;

    public VendaController(VendaRepository repository,
                           FormaPagamentoRepository formaPagamentoRepository,
                           ClientePessoaFisicaRepository clientePessoaFisicaRepository) {
        this.repository = repository;
        this.formaPagamentoRepository = formaPagamentoRepository;
        this.clientePessoaFisicaRepository = clientePessoaFisicaRepository;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id, ModelMap modelMap, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Cliente cliente = clientePessoaFisicaRepository.findByUsuarioUsername(
                userDetails.getUsername()).orElseThrow();
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
        Cliente cliente = clientePessoaFisicaRepository.findByUsuarioUsername(
                userDetails.getUsername()).orElseThrow();

        modelMap.addAttribute("vendas", repository.findAllByCliente(cliente));
        return new ModelAndView("/vendas/list", modelMap);
    }

    @GetMapping("/pagamento/{id}/selecionar")
    public ModelAndView paymentSelect(@PathVariable("id") Long id, ModelMap modelMap) {
        Venda venda = repository.findById(id).orElseThrow();
        List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll(
                Sort.by(Sort.DEFAULT_DIRECTION, "id"));

        modelMap.addAttribute("venda", venda);
        modelMap.addAttribute("formasPagamento", formasPagamento);

        return new ModelAndView("/vendas/pagamento/selecionar", modelMap);
    }

    @GetMapping("/pagamento/{id}/{slug}")
    public ModelAndView paymentMethodSelected(@PathVariable("id") Long id,
                                              @PathVariable("slug") String slug, ModelMap modelMap) {
        Venda venda = repository.findById(id).orElseThrow();
        FormaPagamento formaPagamento = formaPagamentoRepository.findBySlug(slug).orElseThrow();
        Pagamento pagamento = new Pagamento();

        pagamento.setFormaPagamento(formaPagamento);
        pagamento.setVenda(venda);

        pagamento.setNumeroParcelas(1);
        pagamento.setDescontoTotal(PercentUtil.percentOf(
                formaPagamento.getPercentualDescontoAVista(), venda.total()));
        pagamento.setJurosTotal(PercentUtil.percentOf(
                formaPagamento.getPercentualJurosParcelado(), venda.total()));
        pagamento.setValorTotal(venda.total() - pagamento.getDescontoTotal() + pagamento.getJurosTotal());
        pagamento.setValorParcela(pagamento.getValorTotal() / pagamento.getNumeroParcelas());

        modelMap.addAttribute("pagamento", pagamento);
        return new ModelAndView(String.format("/vendas/pagamento/%s", slug), modelMap);
    }

    @PostMapping("/pagamento/processar")
    public ModelAndView paymentPix(@Valid Pagamento pagamento, @RequestParam("nextUrl") String nextUrl) {
        Venda venda = pagamento.getVenda();

        venda.setPagamento(pagamento);
        repository.save(venda);

        if (!StringUtils.hasText(nextUrl)) {
            nextUrl = "/vendas/list";
        }

        return new ModelAndView("redirect:" + nextUrl);
    }

    @GetMapping("/pagamento/{id}/boleto/gerar")
    public ResponseEntity<byte[]> paymentGerarBoleto(@PathVariable("id") Long id, ModelMap modelMap) {
        Venda venda = repository.findById(id).orElseThrow();
        ClientePessoaFisica clientePessoaFisica = clientePessoaFisicaRepository.findById(
                venda.getCliente().getId()).orElseThrow();
        BoletoUtil boletoUtil = BoletoUtil.getInstance();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-type", MediaType.APPLICATION_PDF_VALUE);
        headers.set("Content-Disposition", "inline; filename=\"boleto.pdf\"");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers).body(boletoUtil.asPDF(venda.getPagamento(), clientePessoaFisica));
    }
}
