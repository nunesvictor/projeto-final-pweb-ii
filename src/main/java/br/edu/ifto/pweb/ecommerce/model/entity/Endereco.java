package br.edu.ifto.pweb.ecommerce.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apelido;

    @Column(nullable = false)
    @NotBlank(message = "Esse campo é obrigatório.")
    private String logradouro;
    private String complemento;

    @Column(nullable = false)
    @NotBlank(message = "Esse campo é obrigatório.")
    private String numero;

    @Column(nullable = false)
    @NotBlank(message = "Esse campo é obrigatório.")
    private String bairro;

    @Column(nullable = false)
    @NotBlank(message = "Esse campo é obrigatório.")
    private String localidade;

    @Column(nullable = false)
    @Length(message = "O campo deve ter {max} caracteres.", min = 2, max = 2)
    private String uf;

    @Column(nullable = false)
    @Length(message = "O campo deve ter {max} caracteres.", min = 8, max = 8)
    private String cep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFormattedCep() {
        return cep.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
    }
}
