# Sistema de Concessionária

Sistema completo de gerenciamento de concessionária em Java com banco de dados SQLite.

## Funcionalidades

- **Gerenciamento de Clientes**: cadastro, listagem, busca, atualização e exclusão
- **Gerenciamento de Veículos**: suporte para carros, motos e caminhões
- **Gerenciamento de Vendas**: registro de vendas vinculando clientes e veículos
- **Relatórios**: relatórios completos de vendas e histórico de compras por cliente

## Estrutura do Banco de Dados

### Tabelas:
- **clientes**: id, nome, cpf, telefone, email
- **veiculos**: id, tipo, marca, modelo, ano, preco, vendido, portas, cilindradas, capacidade_ton
- **vendas**: id, cliente_id, veiculo_id, data, valor

### Relacionamentos:
- Uma venda vincula um cliente a um veículo
- Quando um veículo é vendido, seu status é atualizado
- As vendas mantêm referências aos IDs de clientes e veículos

## Como Executar

### Opção 1: Usar o script de execução (recomendado)
```bash
chmod +x run.sh
./run.sh
```

### Opção 2: Manualmente
```bash
# Baixar o driver JDBC do SQLite
mkdir -p lib
curl -L -o lib/sqlite-jdbc-3.44.1.0.jar https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.44.1.0/sqlite-jdbc-3.44.1.0.jar

# Compilar
javac -cp ".:lib/sqlite-jdbc-3.44.1.0.jar" *.java

# Executar
java -cp ".:lib/sqlite-jdbc-3.44.1.0.jar" Main
```

## Arquivos do Projeto

- **Main.java**: Aplicação principal com menus interativos
- **DBHelper.java**: Gerenciamento de conexão e migrações do banco
- **Cliente.java**: Modelo de dados de cliente
- **Veiculo.java**: Classe abstrata para veículos
- **Carro.java**: Modelo específico de carro
- **Moto.java**: Modelo específico de moto
- **Caminhao.java**: Modelo específico de caminhão
- **Venda.java**: Modelo de dados de venda
- **ClienteDAO.java**: Operações de banco para clientes
- **VeiculoDAO.java**: Operações de banco para veículos
- **VendaDAO.java**: Operações de banco para vendas

## Recursos

- ✓ Inicialização automática do banco de dados
- ✓ Execução de migrações se necessário
- ✓ Dados de exemplo populados automaticamente
- ✓ Interface interativa em português
- ✓ Validações de integridade referencial
- ✓ Sistema funcional completo
