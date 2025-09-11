CREATE TABLE produtos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    codigo_barras VARCHAR(50),
    marca VARCHAR(100),
    categoria VARCHAR(100),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_nome (nome),
    INDEX idx_categoria (categoria),
    INDEX idx_marca (marca),
    INDEX idx_codigo_barras (codigo_barras)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;