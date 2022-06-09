package br.edu.ifto.pweb.ecommerce.utils;

import br.com.caelum.stella.boleto.*;
import br.com.caelum.stella.boleto.bancos.Bradesco;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import br.edu.ifto.pweb.ecommerce.model.entity.ClientePessoaFisica;
import br.edu.ifto.pweb.ecommerce.model.entity.Pagamento;
import br.edu.ifto.pweb.ecommerce.model.entity.Venda;

import java.time.LocalDateTime;
import java.util.Locale;

public class BoletoUtil {
    public static BoletoUtil getInstance() {
        return new BoletoUtil();
    }

    public Boleto createBoletoObject(Pagamento pagamento, ClientePessoaFisica clientePessoaFisica) {
        return Boleto.novoBoleto()
                .comBanco(new Bradesco())
                .comDatas(getDatas(pagamento))
                .comBeneficiario(getBeneficiario())
                .comPagador(getPagador(pagamento, clientePessoaFisica))
                .comValorBoleto(String.format(Locale.US, "%.2f", pagamento.getValorTotal()))
                .comNumeroDoDocumento("1234")
                .comInstrucoes("Senhor(a) caixa,", "favor não receber após o vencimento.")
                .comLocaisDePagamento("Pagável em qualquer correspondente bancário até o vencimento");
    }

    public byte[] asPDF(Boleto boleto) {
        return new GeradorDeBoleto(boleto).geraPDF();
    }

    public byte[] asPDF(Pagamento pagamento, ClientePessoaFisica clientePessoaFisica) {
        return asPDF(createBoletoObject(pagamento, clientePessoaFisica));
    }

    public byte[] asPNG(Boleto boleto) {
        return new GeradorDeBoleto(boleto).geraPNG();
    }

    public byte[] asPNG(Pagamento pagamento, ClientePessoaFisica clientePessoaFisica) {
        return asPNG(createBoletoObject(pagamento, clientePessoaFisica));
    }

    private Pagador getPagador(Pagamento pagamento, ClientePessoaFisica clientePessoaFisica) {
        Endereco enderecoPagador = getEnderecoPagador(pagamento);

        return Pagador.novoPagador()
                .comNome(clientePessoaFisica.getNome())
                .comDocumento(clientePessoaFisica.getFormattedCpf())
                .comEndereco(enderecoPagador);
    }

    private Endereco getEnderecoPagador(Pagamento pagamento) {
        Venda venda = pagamento.getVenda();

        return Endereco.novoEndereco()
                .comLogradouro(venda.getEndereco().getLogradouro())
                .comBairro(venda.getEndereco().getBairro())
                .comCep(venda.getEndereco().getFormattedCep())
                .comCidade(venda.getEndereco().getLocalidade())
                .comUf(venda.getEndereco().getUf());
    }

    private Beneficiario getBeneficiario() {
        Endereco enderecoBeneficiario = getEnderecoBeneficiario();

        return Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("NightShop Comércio Eletrônico LTDA")
                .comDocumento("00.000.000/0000-00")
                .comAgencia("1824").comDigitoAgencia("4")
                .comCodigoBeneficiario("76000")
                .comDigitoCodigoBeneficiario("5")
                .comNumeroConvenio("1207113")
                .comCarteira("18")
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("9000206");
    }

    private Endereco getEnderecoBeneficiario() {
        return Endereco.novoEndereco()
                .comLogradouro("Av dos testes, 111 apto 333")
                .comBairro("Bairro Teste")
                .comCep("01234-111")
                .comCidade("São Paulo")
                .comUf("SP");
    }

    private Datas getDatas(Pagamento pagamento) {
        LocalDateTime dataCompra = pagamento.getCreatedAt();
        LocalDateTime proximoDia = pagamento.getCreatedAt().plusDays(1);

        return Datas.novasDatas()
                .comDocumento(dataCompra.getDayOfMonth(),
                        dataCompra.getMonthValue(),
                        dataCompra.getYear())
                .comProcessamento(dataCompra.getDayOfMonth(),
                        dataCompra.getMonthValue(),
                        dataCompra.getYear())
                .comVencimento(proximoDia.getDayOfMonth(),
                        proximoDia.getMonthValue(),
                        proximoDia.getYear());
    }
}
