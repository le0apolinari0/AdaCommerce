CREATE TABLE itens_pedido (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    preco_venda DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) GENERATED ALWAYS AS (quantidade * preco_venda) STORED,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos(id) ON DELETE RESTRICT,
    INDEX idx_pedido_id (pedido_id),
    INDEX idx_produto_id (produto_id),
    UNIQUE INDEX uk_pedido_produto (pedido_id, produto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;