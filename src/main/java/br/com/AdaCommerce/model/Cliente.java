package br.com.AdaCommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    @Column(unique = true)
    private String cpf;

    private String telefone;

    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

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

