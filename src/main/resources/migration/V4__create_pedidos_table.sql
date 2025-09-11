CREATE TABLE pedidos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('ABERTO', 'AGUARDANDO_PAGAMENTO', 'PAGO', 'FINALIZADO') DEFAULT 'ABERTO',
    status_pagamento ENUM('NAO_PAGO', 'AGUARDANDO_PAGAMENTO', 'PAGO') DEFAULT 'NAO_PAGO',
    total DECIMAL(10, 2) DEFAULT 0.00,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE RESTRICT,
    INDEX idx_cliente_id (cliente_id),
    INDEX idx_status (status),
    INDEX idx_data_criacao (data_criacao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;