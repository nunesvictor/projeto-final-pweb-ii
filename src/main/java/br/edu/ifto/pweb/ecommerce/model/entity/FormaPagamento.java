package br.edu.ifto.pweb.ecommerce.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class FormaPagamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Esse campo é obrigatório")
    private String descricao;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Esse campo é obrigatório")
    private String slug;

    @Column(nullable = false)
    private String imagem;

    @Column(nullable = false, columnDefinition = "integer default 1")
    @Min(value = 1, message = "O valor mínimo é {value}")
    private Integer parcelamentoMaximo = 1;

    @Column(nullable = false, columnDefinition = "decimal(10,2) default 0")
    @DecimalMin(value = "0.00", message = "O valor mínimo é {value}")
    private Double percentualDescontoAVista = 0.00;

    @Column(nullable = false, columnDefinition = "decimal(10,2) default 0")
    @DecimalMin(value = "0.00", message = "O valor mínimo é {value}")
    private Double percentualJurosParcelado = 0.00;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Integer getParcelamentoMaximo() {
        return parcelamentoMaximo;
    }

    public void setParcelamentoMaximo(Integer parcelamentoMaximo) {
        this.parcelamentoMaximo = parcelamentoMaximo;
    }

    public Double getPercentualDescontoAVista() {
        return percentualDescontoAVista;
    }

    public void setPercentualDescontoAVista(Double percentualDescontoAVista) {
        this.percentualDescontoAVista = percentualDescontoAVista;
    }

    public Double getPercentualJurosParcelado() {
        return percentualJurosParcelado;
    }

    public void setPercentualJurosParcelado(Double percentualJurosParcelado) {
        this.percentualJurosParcelado = percentualJurosParcelado;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
