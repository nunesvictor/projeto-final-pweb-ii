package br.edu.ifto.pweb.ecommerce.model.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class ClientePessoaFisica extends Cliente {
    @NotBlank(message = "Esse campo é obrigatório")
    private String nome;

    @NotBlank(message = "Esse campo é obrigatório")
    @Size(min = 11, max = 11, message = "Esse campo exige {max} caracteres")
    private String cpf;

    @NotBlank(message = "Esse campo é obrigatório")
    private String email;

    @NotBlank(message = "Esse campo é obrigatório")
    private String telefone;
}