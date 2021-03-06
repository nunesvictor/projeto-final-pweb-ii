package br.edu.ifto.pweb.ecommerce.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class ClientePessoaFisica extends Cliente {
    @NotBlank(message = "Esse campo é obrigatório")
    private String nome;

    @Column(unique = true)
    @NotBlank(message = "Esse campo é obrigatório")
    @Size(min = 11, max = 11, message = "Esse campo exige {max} caracteres")
    private String cpf;

    @Column(unique = true)
    @NotBlank(message = "Esse campo é obrigatório")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Esse campo é obrigatório")
    @Size(min = 11, max = 11, message = "Esse campo exige {max} caracteres")
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFormattedCpf() {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
