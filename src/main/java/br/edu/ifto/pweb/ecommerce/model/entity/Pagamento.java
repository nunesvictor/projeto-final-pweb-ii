package br.edu.ifto.pweb.ecommerce.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Pagamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FormaPagamento formaPagamento;

    @OneToOne
    private Venda venda;

    @Column(nullable = false, columnDefinition = "decimal(10,2) default 0")
    @DecimalMin(value = "0.00", message = "O valor mínimo é {value}")
    private Double descontoTotal = 0.00;

    @Column(nullable = false, columnDefinition = "decimal(10,2) default 0")
    @DecimalMin(value = "0.00", message = "O valor mínimo é {value}")
    private Double jurosTotal;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", message = "O valor mínimo é {value}")
    private Double valorParcela;

    @Column(nullable = false, columnDefinition = "integer default 1")
    @Min(value = 1, message = "O valor mínimo para essa campo é {value}")
    private Integer numeroParcelas;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", message = "O valor mínimo é {value}")
    private Double valorTotal;

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

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Double getDescontoTotal() {
        return descontoTotal;
    }

    public void setDescontoTotal(Double descontoTotal) {
        this.descontoTotal = descontoTotal;
    }

    public Double getJurosTotal() {
        return jurosTotal;
    }

    public void setJurosTotal(Double jurosTotal) {
        this.jurosTotal = jurosTotal;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
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
