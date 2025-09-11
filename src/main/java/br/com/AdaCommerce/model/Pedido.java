package br.com.AdaCommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private List<ItemPedido> itens = new ArrayList<>();

    private BigDecimal total;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void prePersist() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        status = StatusPedido.ABERTO;
        statusPagamento = StatusPagamento.NAO_PAGO;
        total = BigDecimal.ZERO;
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
        calcularTotal();
    }

    public void calcularTotal() {
        this.total = itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        calcularTotal();
    }

    public void removerItem(ItemPedido item) {
        itens.remove(item);
        calcularTotal();
    }
}

