package br.com.AdaCommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "itens_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidade;

    @Column(name = "preco_venda")
    private BigDecimal precoVenda;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public BigDecimal getSubtotal() {
        return precoVenda.multiply(BigDecimal.valueOf(quantidade));
    }
}

