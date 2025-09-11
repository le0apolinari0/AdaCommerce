package br.com.AdaCommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    private String descricao;

    @NotNull
    private BigDecimal preco;

    @Column(name = "codigo_barras")
    private String codigoBarras;

    private String marca;

    private String categoria;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}

