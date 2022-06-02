package br.edu.ifto.pweb.ecommerce.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class ItemVenda implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Produto produto;

    @ManyToOne
    private Venda venda;

    @Column(nullable = false, columnDefinition = "integer default 1")
    @Min(value = 1, message = "O valor mínimo para essa campo é {value}")
    @NotNull(message = "Esse campo é obrigatório")
    private Integer quantidade = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double total() {
        return quantidade * produto.precoAtual().getValor();
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "id=" + id +
                ", produto=" + produto +
                ", venda=" + venda +
                ", quantidade=" + quantidade +
                '}';
    }
}