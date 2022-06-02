package br.edu.ifto.pweb.ecommerce.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Esse campo é obrigatório")
    private String descricao;

    @OneToOne
    private Marca marca;

    @Column(nullable = false)
    private String imagem;

    @OneToMany(mappedBy = "produto", fetch = FetchType.EAGER)
    @OrderBy("updatedAt DESC")
    private List<Preco> precos;

    @ManyToMany
    @OrderBy("descricao ASC")
    private List<Categoria> categorias;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Stream<Preco> getPrecosAtivosStream() {
        return getPrecos().stream().filter(Preco::getActive);
    }

    public Preco precoAtual() {
        Optional<Preco> precoOptional = getPrecosAtivosStream().findFirst();
        return precoOptional.orElse(null);
    }

    public Preco precoAnterior() {
        List<Preco> precosAtivos = getPrecosAtivosStream().toList();

        if (precoAtual() == null || !precoAtual().isPromocao() || precosAtivos.size() < 2) {
            return null;
        }

        return precosAtivos.get(1);
    }

    public Double percentualDesconto() {
        if (precoAnterior() == null) {
            return 0.0;
        }

        return 100 - ((precoAtual().getValor() * 100) / precoAnterior().getValor());
    }

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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<Preco> getPrecos() {
        return precos;
    }

    public void setPrecos(List<Preco> precos) {
        this.precos = precos;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
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
