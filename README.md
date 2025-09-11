```markdown
# AdaCommerce - Sistema de E-Commerce

Sistema completo de e-commerce desenvolvido para Ada Tech com Java 21, Spring Boot 3.5.5 e arquitetura moderna.

## 🚀 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.5** - Framework principal
- **MySQL** - Banco de dados relacional
- **Flyway** - Controle de migrations
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **OpenAPI/Swagger** - Documentação da API
- **Docker** - Containerização
- **MapStruct** - Mapeamento de DTOs
- **Caffeine** - Cache em memória

## 📦 Estrutura do Projeto

## 🏗️ Arquitetura

O sistema segue os princípios SOLID e arquitetura em camadas:
- **Controller**: Endpoints REST
- **Service**: Regras de negócio
- **Repository**: Acesso a dados
- **Model**: Entidades de domínio

## 🔧 Configuração e Execução

### Pré-requisitos
- Java 21
- Maven 3.6+
- Docker e Docker Compose
- MySQL 8.0

### Execução com Docker
```bash
# Clone o repositório
git clone https://github.com/seu-usuario/AdaCommerce.git

# Execute com Docker
docker-compose up -d

# Acesse a aplicação
http://localhost:8080

# Acesse o Swagger
http://localhost:8080/swagger-ui.html
```

### Execução Local
```bash
# Configure o banco de dados no application.properties
# Execute a aplicação
mvn spring-boot:run
```

## 📚 Documentação da API

A API está documentada com Swagger/OpenAPI disponível em:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

## 🗄️ Modelo de Dados

### Principais Entidades
- **Cliente**: Dados pessoais e endereço
- **Produto**: Informações de produtos
- **Pedido**: Pedidos dos clientes
- **ItemPedido**: Itens dos pedidos

### Relacionamentos
- Cliente 1:N Pedido
- Pedido 1:N ItemPedido
- ItemPedido N:1 Produto

## 🔐 Autenticação e Segurança

- Spring Security com autenticação JWT
- Roles e permissões
- CORS configurado
- CSRF protection

## 🚀 Endpoints Principais

### Clientes
- `POST /api/clientes` - Criar cliente
- `GET /api/clientes` - Listar clientes
- `GET /api/clientes/{id}` - Buscar por ID
- `PUT /api/clientes/{id}` - Atualizar cliente

### Produtos
- `POST /api/produtos` - Criar produto
- `GET /api/produtos` - Listar produtos
- `GET /api/produtos/search` - Buscar produtos
- `POST /api/produtos/importar/{codigoBarras}` - Importar da API

### Pedidos
- `POST /api/pedidos` - Criar pedido
- `POST /api/pedidos/{id}/itens` - Adicionar item
- `POST /api/pedidos/{id}/finalizar` - Finalizar pedido
- `POST /api/pedidos/{id}/pagar` - Processar pagamento

## 🧪 Testes

```bash
# Executar testes unitários
mvn test

# Executar testes de integração
mvn verify

# Gerar relatório de cobertura
mvn jacoco:report
```

## 📊 Monitoramento

- Health checks em `/actuator/health`
- Metrics em `/actuator/metrics`
- Logs centralizados
- Prometheus metrics

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

## 🆘 Suporte

Para dúvidas e suporte:
- Email: suporte@adacommerce.com
- Issues: GitHub Issues
- Documentação: Wiki do projeto

---

Desenvolvido com ❤️ por Ada Tech
```

## 📋 Exemplos de Uso da API

### 1. Cadastro de Cliente
**POST /api/clientes**
```json
{
  "nome": "Maria Silva",
  "cpf": "123.456.789-00",
  "telefone": "(11) 99999-9999",
  "email": "maria.silva@email.com",
  "endereco": {
    "cep": "01001-000",
    "numero": "150",
    "complemento": "Apto 302",
    "autoCompletar": true
  }
}
```

### 2. Cadastro de Produto Manual
**POST /api/produtos**
```json
{
  "nome": "Arroz Integral",
  "descricao": "Arroz integral tipo 1, pacote 5kg",
  "preco": 24.90,
  "marca": "Tio João",
  "categoria": "Grãos"
}
```

### 3. Importar Produto por Código de Barras
**POST /api/produtos/importar/7891000053508**
```json
{}
```

### 4. Criar Pedido
**POST /api/pedidos**
```json
{
  "clienteId": 1
}
```

### 5. Adicionar Item ao Pedido
**POST /api/pedidos/1/itens?produtoId=1&quantidade=2&precoVenda=24.90**
```json
{}
```

### 6. Finalizar Pedido
**POST /api/pedidos/1/finalizar**
```json
{}
```

### 7. Processar Pagamento
**POST /api/pedidos/1/pagar**
```json
{}
```

### 8. Buscar Produtos por Nome
**GET /api/produtos/search?termo=arroz&page=0&size=10**
```json
{}
```

### 9. Buscar Cliente por CPF
**GET /api/clientes/search?cpf=12345678900**
```json
{}
```

### 10. Consultar CEP
**GET /api/cep/01001000**
```json
{}
```

## 🎯 Fluxo Completo de um Pedido

1. **Cadastrar Cliente** → POST /api/clientes
2. **Cadastrar/Importar Produtos** → POST /api/produtos ou POST /api/produtos/importar/{codigo}
3. **Criar Pedido** → POST /api/pedidos
4. **Adicionar Itens** → POST /api/pedidos/{id}/itens
5. **Finalizar Pedido** → POST /api/pedidos/{id}/finalizar
6. **Processar Pagamento** → POST /api/pedidos/{id}/pagar
7. **Finalizar Entrega** → POST /api/pedidos/{id}/entregar

## 🔍 Exemplos de Respostas

### Resposta de Sucesso:
```json
{
  "success": true,
  "message": "Operação realizada com sucesso",
  "data": {
    "id": 1,
    "nome": "Produto Exemplo",
    "preco": 29.90
  },
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

### Resposta de Erro:
```json
{
  "success": false,
  "message": "Produto não encontrado",
  "data": null,
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

## 📊 Paginação

Todos os endpoints de listagem suportam paginação:
- `page`: Número da página (padrão: 0)
- `size`: Tamanho da página (padrão: 10)
- `sort`: Campo para ordenação

**Exemplo**: `GET /api/produtos?page=0&size=20&sort=nome,asc`

## 🔗 Links Úteis

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health
- **API Docs**: http://localhost:8080/v3/api-docs

