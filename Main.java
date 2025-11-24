import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static VeiculoDAO veiculoDAO = new VeiculoDAO();
    private static VendaDAO vendaDAO = new VendaDAO();
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║       SISTEMA DE CONCESSIONÁRIA        ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        // Verificar conexão e inicializar banco de dados
        if (!DBHelper.verificarConexao()) {
            System.err.println("Não foi possível conectar ao banco de dados. Encerrando...");
            return;
        }
        DBHelper.initializeDatabase();
        System.out.println();
        // Popular banco com dados de exemplo se estiver vazio
        try {
            if (veiculoDAO.listarTodos().isEmpty()) {
                popularDadosExemplo();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar dados: " + e.getMessage());
        }
        
        boolean executando = true;

        while (executando) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();
            System.out.println();
            
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuVeiculos();
                    break;
                case 3:
                    menuVendas();
                    break;
                case 4:
                    menuRelatorios();
                    break;
                case 0:
                    System.out.println("Encerrando sistema... Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("⚠ Opção inválida!");
            }
            
            if (executando) {
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║          MENU PRINCIPAL                  ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ 1 - Gerenciar Clientes                   ║");
        System.out.println("║ 2 - Gerenciar Veículos                   ║");
        System.out.println("║ 3 - Gerenciar Vendas                     ║");
        System.out.println("║ 4 - Relatórios                           ║");
        System.out.println("║ 0 - Sair                                 ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.print("Opção: ");
    }
    
    private static void menuClientes() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        GERENCIAR CLIENTES              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1 - Cadastrar novo cliente             ║");
        System.out.println("║ 2 - Listar todos os clientes           ║");
        System.out.println("║ 3 - Buscar cliente por CPF             ║");
        System.out.println("║ 4 - Atualizar cliente                  ║");
        System.out.println("║ 5 - Deletar cliente                    ║");
        System.out.println("║ 0 - Voltar                             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Opção: ");
        
        int opcao = lerOpcao();
        System.out.println();
        
        try {
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarClientePorCpf();
                    break;
                case 4:
                    atualizarCliente();
                    break;
                case 5:
                    deletarCliente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("⚠ Opção inválida!");
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao executar operação: " + e.getMessage());
        }
    }
    
    private static void menuVeiculos() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        GERENCIAR VEÍCULOS              ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1 - Cadastrar novo veículo             ║");
        System.out.println("║ 2 - Listar todos os veículos           ║");
        System.out.println("║ 3 - Listar veículos disponíveis        ║");
        System.out.println("║ 4 - Buscar veículo por ID              ║");
        System.out.println("║ 5 - Atualizar veículo                  ║");
        System.out.println("║ 6 - Deletar veículo                    ║");
        System.out.println("║ 0 - Voltar                             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Opção: ");
        
        int opcao = lerOpcao();
        System.out.println();
        
        try {
            switch (opcao) {
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    listarVeiculos();
                    break;
                case 3:
                    listarVeiculosDisponiveis();
                    break;
                case 4:
                    buscarVeiculoPorId();
                    break;
                case 5:
                    atualizarVeiculo();
                    break;
                case 6:
                    deletarVeiculo();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("⚠ Opção inválida!");
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao executar operação: " + e.getMessage());
        }
    }
    
    private static void menuVendas() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║         GERENCIAR VENDAS               ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1 - Registrar nova venda               ║");
        System.out.println("║ 2 - Listar todas as vendas             ║");
        System.out.println("║ 3 - Buscar vendas por cliente          ║");
        System.out.println("║ 4 - Cancelar venda                     ║");
        System.out.println("║ 0 - Voltar                             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Opção: ");
        
        int opcao = lerOpcao();
        System.out.println();
        
        try {
            switch (opcao) {
                case 1:
                    registrarVenda();
                    break;
                case 2:
                    listarVendas();
                    break;
                case 3:
                    buscarVendasPorCliente();
                    break;
                case 4:
                    cancelarVenda();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("⚠ Opção inválida!");
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao executar operação: " + e.getMessage());
        }
    }
    
    private static void menuRelatorios() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║           RELATÓRIOS                   ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1 - Relatório completo de vendas       ║");
        System.out.println("║ 2 - Histórico de compras de cliente    ║");
        System.out.println("║ 0 - Voltar                             ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Opção: ");
        
        int opcao = lerOpcao();
        System.out.println();
        
        try {
            switch (opcao) {
                case 1:
                    relatorioVendasCompleto();
                    break;
                case 2:
                    relatorioComprasCliente();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("⚠ Opção inválida!");
            }
        } catch (SQLException e) {
            System.err.println("✗ Erro ao executar operação: " + e.getMessage());
        }
    }
    
    // ===== MÉTODOS DE CLIENTES =====
    
    private static void cadastrarCliente() throws SQLException {
        System.out.println("=== CADASTRAR NOVO CLIENTE ===");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        // Validar e obter CPF válido
        String cpf = null;
        boolean cpfValido = false;
        while (!cpfValido) {
            System.out.print("CPF (apenas números): ");
            cpf = scanner.nextLine();
            
            if (!validarCPF(cpf)) {
                System.out.println("✗ CPF inválido! Use apenas números (ex: 12345678900)");
                continue;
            }
            
            if (clienteDAO.buscarPorCpf(cpf) != null) {
                System.out.println("✗ CPF já cadastrado!");
                continue;
            }
            
            cpfValido = true;
        }
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        // Validar e obter email válido
        String email = null;
        boolean emailValido = false;
        while (!emailValido) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            
            if (email.isEmpty()) {
                email = null;
                break; // Email é opcional
            }
            
            if (!validarEmail(email)) {
                System.out.println("✗ Email inválido! Deve conter @ e .");
                continue;
            }
            
            emailValido = true;
        }
        
        Cliente cliente = new Cliente(0, nome, cpf, telefone.isEmpty() ? null : telefone, email);
        int id = clienteDAO.inserir(cliente);
        
        if (id > 0) {
            System.out.println("✓ Cliente cadastrado com sucesso! ID: " + id);
        } else {
            System.out.println("✗ Erro ao cadastrar cliente!");
        }
    }
    
    private static void listarClientes() throws SQLException {
        System.out.println("=== LISTA DE CLIENTES ===");
        List<Cliente> clientes = clienteDAO.listarTodos();
        
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
            System.out.println("\nTotal: " + clientes.size() + " cliente(s)");
        }
    }
    
    private static void buscarClientePorCpf() throws SQLException {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = clienteDAO.buscarPorCpf(cpf);
        
        if (cliente != null) {
            System.out.println("\n" + cliente);
        } else {
            System.out.println("✗ Cliente não encontrado!");
        }
    }
    
    private static void atualizarCliente() throws SQLException {
        System.out.print("Digite o ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }
        
        System.out.println("Cliente atual: " + cliente);
        System.out.println("\nDeixe em branco para manter o valor atual.");
        
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) cliente.setNome(nome);
        
        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) cliente.setTelefone(telefone);
        
        System.out.print("Novo email: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            // Validar email antes de atualizar
            while (!validarEmail(email)) {
                System.out.println("✗ Email inválido! Deve conter @ e .");
                System.out.print("Novo email: ");
                email = scanner.nextLine();
                if (email.isEmpty()) break;
            }
            if (!email.isEmpty()) cliente.setEmail(email);
        }
        
        clienteDAO.atualizar(cliente);
        System.out.println("✓ Cliente atualizado com sucesso!");
    }
    
    private static void deletarCliente() throws SQLException {
        System.out.print("Digite o ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }
        
        System.out.println("Cliente: " + cliente);
        System.out.print("Confirma exclusão? (S/N): ");
        String confirma = scanner.nextLine();
        
        if (confirma.equalsIgnoreCase("S")) {
            clienteDAO.deletar(id);
            System.out.println("✓ Cliente deletado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    
    // ===== MÉTODOS DE VEÍCULOS =====
    
    private static void cadastrarVeiculo() throws SQLException {
        System.out.println("=== CADASTRAR NOVO VEÍCULO ===");
        System.out.println("Tipo: 1-Carro  2-Moto  3-Caminhão");
        System.out.print("Escolha: ");
        int tipo = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Preço: ");
        double preco = Double.parseDouble(scanner.nextLine());
        
        Veiculo veiculo = null;
        
        switch (tipo) {
            case 1:
                System.out.print("Número de portas: ");
                int portas = Integer.parseInt(scanner.nextLine());
                veiculo = new Carro(0, marca, modelo, ano, preco, false, portas);
                break;
            case 2:
                System.out.print("Cilindradas: ");
                String cilindradas = scanner.nextLine();
                veiculo = new Moto(0, marca, modelo, ano, preco, false, cilindradas);
                break;
            case 3:
                System.out.print("Capacidade (toneladas): ");
                double capacidade = Double.parseDouble(scanner.nextLine());
                veiculo = new Caminhao(0, marca, modelo, ano, preco, false, capacidade);
                break;
            default:
                System.out.println("✗ Tipo inválido!");
                return;
        }
        
        int id = veiculoDAO.inserir(veiculo);
        
        if (id > 0) {
            System.out.println("✓ Veículo cadastrado com sucesso! ID: " + id);
        } else {
            System.out.println("✗ Erro ao cadastrar veículo!");
        }
    }
    
    private static void listarVeiculos() throws SQLException {
        System.out.println("=== LISTA DE VEÍCULOS ===");
        List<Veiculo> veiculos = veiculoDAO.listarTodos();
        
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            for (Veiculo v : veiculos) {
                System.out.println(v);
            }
            System.out.println("\nTotal: " + veiculos.size() + " veículo(s)");
        }
    }
    
    private static void listarVeiculosDisponiveis() throws SQLException {
        System.out.println("=== VEÍCULOS DISPONÍVEIS ===");
        List<Veiculo> veiculos = veiculoDAO.listarDisponiveis();
        
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo disponível.");
        } else {
            for (Veiculo v : veiculos) {
                System.out.println(v);
            }
            System.out.println("\nTotal: " + veiculos.size() + " veículo(s) disponível(is)");
        }
    }
    
    private static void buscarVeiculoPorId() throws SQLException {
        System.out.print("Digite o ID do veículo: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Veiculo veiculo = veiculoDAO.buscarPorId(id);
        
        if (veiculo != null) {
            System.out.println("\n" + veiculo);
        } else {
            System.out.println("✗ Veículo não encontrado!");
        }
    }
    
    private static void atualizarVeiculo() throws SQLException {
        System.out.print("Digite o ID do veículo: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Veiculo veiculo = veiculoDAO.buscarPorId(id);
        if (veiculo == null) {
            System.out.println("✗ Veículo não encontrado!");
            return;
        }
        
        System.out.println("Veículo atual: " + veiculo);
        System.out.println("\nDeixe em branco para manter o valor atual.");
        
        System.out.print("Nova marca: ");
        String marca = scanner.nextLine();
        if (!marca.isEmpty()) veiculo.setMarca(marca);
        
        System.out.print("Novo modelo: ");
        String modelo = scanner.nextLine();
        if (!modelo.isEmpty()) veiculo.setModelo(modelo);
        
        System.out.print("Novo preço: ");
        String precoStr = scanner.nextLine();
        if (!precoStr.isEmpty()) veiculo.setPreco(Double.parseDouble(precoStr));
        
        veiculoDAO.atualizar(veiculo);
        System.out.println("✓ Veículo atualizado com sucesso!");
    }
    
    private static void deletarVeiculo() throws SQLException {
        System.out.print("Digite o ID do veículo: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Veiculo veiculo = veiculoDAO.buscarPorId(id);
        if (veiculo == null) {
            System.out.println("✗ Veículo não encontrado!");
            return;
        }
        
        System.out.println("Veículo: " + veiculo);
        System.out.print("Confirma exclusão? (S/N): ");
        String confirma = scanner.nextLine();
        
        if (confirma.equalsIgnoreCase("S")) {
            veiculoDAO.deletar(id);
            System.out.println("✓ Veículo deletado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    
    // ===== MÉTODOS DE VENDAS =====
    
    private static void registrarVenda() throws SQLException {
        System.out.println("=== REGISTRAR NOVA VENDA ===");
        
        // Listar veículos disponíveis
        List<Veiculo> veiculosDisponiveis = veiculoDAO.listarDisponiveis();
        if (veiculosDisponiveis.isEmpty()) {
            System.out.println("✗ Não há veículos disponíveis para venda!");
            return;
        }
        
        System.out.println("\n--- Veículos Disponíveis ---");
        for (Veiculo v : veiculosDisponiveis) {
            System.out.println(v);
        }
        
        System.out.print("\nID do veículo: ");
        int veiculoId = Integer.parseInt(scanner.nextLine());
        
        Veiculo veiculo = veiculoDAO.buscarPorId(veiculoId);
        if (veiculo == null || veiculo.isVendido()) {
            System.out.println("✗ Veículo não disponível!");
            return;
        }
        
        // Listar clientes
        System.out.println("\n--- Clientes Cadastrados ---");
        List<Cliente> clientes = clienteDAO.listarTodos();
        for (Cliente c : clientes) {
            System.out.println(c);
        }
        
        System.out.print("\nID do cliente: ");
        int clienteId = Integer.parseInt(scanner.nextLine());
        
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }
        
        System.out.print("\nValor da venda (Enter para preço do veículo R$ " + String.format("%.2f", veiculo.getPreco()) + "): ");
        String valorStr = scanner.nextLine();
        double valor = valorStr.isEmpty() ? veiculo.getPreco() : Double.parseDouble(valorStr);
        
        String data = LocalDate.now().toString();
        
        Venda venda = new Venda(0, clienteId, veiculoId, data, valor);
        int id = vendaDAO.inserir(venda);
        
        if (id > 0) {
            veiculoDAO.marcarComoVendido(veiculoId);
            System.out.println("\n✓ Venda registrada com sucesso! ID: " + id);
            System.out.println("Cliente: " + cliente.getNome());
            System.out.println("Veículo: " + veiculo.getMarca() + " " + veiculo.getModelo());
            System.out.println("Valor: R$ " + String.format("%.2f", valor));
        } else {
            System.out.println("✗ Erro ao registrar venda!");
        }
    }
    
    private static void listarVendas() throws SQLException {
        System.out.println("=== LISTA DE VENDAS ===");
        List<Venda> vendas = vendaDAO.listarTodas();
        
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
        } else {
            for (Venda v : vendas) {
                System.out.println(v);
            }
            System.out.println("\nTotal: " + vendas.size() + " venda(s)");
        }
    }
    
    private static void buscarVendasPorCliente() throws SQLException {
        System.out.print("Digite o ID do cliente: ");
        int clienteId = Integer.parseInt(scanner.nextLine());
        
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }
        
        System.out.println("\n=== VENDAS DE " + cliente.getNome().toUpperCase() + " ===");
        List<Venda> vendas = vendaDAO.listarPorCliente(clienteId);
        
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para este cliente.");
        } else {
            for (Venda v : vendas) {
                System.out.println(v);
            }
            System.out.println("\nTotal: " + vendas.size() + " venda(s)");
        }
    }
    
    private static void cancelarVenda() throws SQLException {
        System.out.print("Digite o ID da venda: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Venda venda = vendaDAO.buscarPorId(id);
        if (venda == null) {
            System.out.println("✗ Venda não encontrada!");
            return;
        }
        
        System.out.println("Venda: " + venda);
        System.out.print("Confirma cancelamento? (S/N): ");
        String confirma = scanner.nextLine();
        
        if (confirma.equalsIgnoreCase("S")) {
            // Marcar veículo como disponível novamente
            Veiculo veiculo = veiculoDAO.buscarPorId(venda.getVeiculoId());
            if (veiculo != null) {
                veiculo.setVendido(false);
                veiculoDAO.atualizar(veiculo);
            }
            
            vendaDAO.deletar(id);
            System.out.println("✓ Venda cancelada com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    
    // ===== MÉTODOS DE RELATÓRIOS =====
    
    private static void relatorioVendasCompleto() throws SQLException {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║              RELATÓRIO COMPLETO DE VENDAS                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        
        List<Venda> vendas = vendaDAO.listarTodas();
        
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        
        double totalVendas = 0;
        
        for (Venda venda : vendas) {
            Cliente cliente = clienteDAO.buscarPorId(venda.getClienteId());
            Veiculo veiculo = veiculoDAO.buscarPorId(venda.getVeiculoId());
            
            System.out.println("\n─────────────────────────────────────────────────────────────");
            System.out.println("Venda ID: " + venda.getId());
            System.out.println("Data: " + venda.getData());
            System.out.println("Cliente: " + (cliente != null ? cliente.getNome() + " (CPF: " + cliente.getCpf() + ")" : "N/A"));
            System.out.println("Veículo: " + (veiculo != null ? veiculo.getMarca() + " " + veiculo.getModelo() + " (" + veiculo.getAno() + ")" : "N/A"));
            System.out.println("Valor: R$ " + String.format("%.2f", venda.getValor()));
            
            totalVendas += venda.getValor();
        }
        
        System.out.println("\n═════════════════════════════════════════════════════════════");
        System.out.println("Total de vendas: " + vendas.size());
        System.out.println("Valor total: R$ " + String.format("%.2f", totalVendas));
        System.out.println("═════════════════════════════════════════════════════════════");
    }
    
    private static void relatorioComprasCliente() throws SQLException {
        System.out.print("Digite o ID do cliente: ");
        int clienteId = Integer.parseInt(scanner.nextLine());
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║            HISTÓRICO DE COMPRAS DO CLIENTE                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝");
        System.out.println("\nCliente: " + cliente);
        
        List<Venda> vendas = vendaDAO.listarPorCliente(clienteId);
        
        if (vendas.isEmpty()) {
            System.out.println("\nNenhuma compra registrada para este cliente.");
            return;
        }
        
        double totalGasto = 0;
        
        for (Venda venda : vendas) {
            Veiculo veiculo = veiculoDAO.buscarPorId(venda.getVeiculoId());
            
            System.out.println("\n─────────────────────────────────────────────────────────────");
            System.out.println("Data: " + venda.getData());
            System.out.println("Veículo: " + (veiculo != null ? veiculo : "N/A"));
            System.out.println("Valor pago: R$ " + String.format("%.2f", venda.getValor()));
            
            totalGasto += venda.getValor();
        }
        
        System.out.println("\n═════════════════════════════════════════════════════════════");
        System.out.println("Total de compras: " + vendas.size());
        System.out.println("Valor total gasto: R$ " + String.format("%.2f", totalGasto));
        System.out.println("═════════════════════════════════════════════════════════════");
    }
    
    // ===== MÉTODOS AUXILIARES =====
    
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void popularDadosExemplo() {
        System.out.println("=== Populando banco com dados de exemplo ===");
        
        try {
            // Cadastrar clientes
            clienteDAO.inserir(new Cliente(0, "João Silva", "123.456.789-00", "(11) 98765-4321", "joao@email.com"));
            clienteDAO.inserir(new Cliente(0, "Maria Santos", "987.654.321-00", "(11) 91234-5678", "maria@email.com"));
            clienteDAO.inserir(new Cliente(0, "Pedro Oliveira", "456.789.123-00", "(11) 99876-5432", "pedro@email.com"));
            
            // Cadastrar veículos
            veiculoDAO.inserir(new Carro(0, "Volkswagen", "Gol", 2020, 45000.0, false, 4));
            veiculoDAO.inserir(new Carro(0, "Chevrolet", "Onix", 2021, 55000.0, false, 4));
            veiculoDAO.inserir(new Carro(0, "Fiat", "Uno", 2019, 38000.0, false, 2));
            veiculoDAO.inserir(new Moto(0, "Honda", "CG 160", 2022, 12000.0, false, "160cc"));
            veiculoDAO.inserir(new Moto(0, "Yamaha", "YBR 125", 2021, 10500.0, false, "125cc"));
            veiculoDAO.inserir(new Caminhao(0, "Mercedes-Benz", "Accelo", 2020, 180000.0, false, 5.5));
            veiculoDAO.inserir(new Caminhao(0, "Volkswagen", "Delivery", 2019, 150000.0, false, 4.0));
            
            System.out.println("✓ Dados de exemplo cadastrados com sucesso!");
        } catch (SQLException e) {
            System.err.println("✗ Erro ao popular dados: " + e.getMessage());
        }
    }
    
    // ===== MÉTODOS DE VALIDAÇÃO =====
    
    private static boolean validarCPF(String cpf) {
        // Verificar se é nulo ou vazio
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }
        
        // Remover espaços
        cpf = cpf.trim();
        
        // Verificar se contém apenas números
        if (!cpf.matches("\\d+")) {
            return false;
        }
        
        // CPF deve ter 11 dígitos
        return cpf.length() == 11;
    }
    
    private static boolean validarEmail(String email) {
        // Verificar se é nulo ou vazio
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Verificar se contém @ e ponto
        return email.contains("@") && email.contains(".");
    }
}
