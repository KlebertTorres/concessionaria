import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

// Arquivo: Main_comentado.java
// Versão comentada de Main.java — comentários explicam o objetivo das linhas/trechos.
public class Main {
    // Scanner para ler entradas do usuário no console
    private static Scanner scanner = new Scanner(System.in);
    // Instâncias dos DAOs usados pela aplicação (acesso ao banco)
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static VeiculoDAO veiculoDAO = new VeiculoDAO();
    private static VendaDAO vendaDAO = new VendaDAO();
    
    public static void main(String[] args) {
        // Exibir cabeçalho do sistema
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║       SISTEMA DE CONCESSIONÁRIA        ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

        // Verifica se consegue conectar ao banco de dados
        if (!DBHelper.verificarConexao()) {
            // Se a conexão falhar, imprime mensagem de erro e encerra a aplicação
            System.err.println("Não foi possível conectar ao banco de dados. Encerrando...");
            return; // finaliza main
        }

        // Inicializa as tabelas do banco caso não existam
        DBHelper.initializeDatabase();
        System.out.println();

        // Popula o banco com dados de exemplo caso esteja vazio
        try {
            if (veiculoDAO.listarTodos().isEmpty()) {
                // Se não há veículos, insere dados iniciais para facilitar testes
                popularDadosExemplo();
            }
        } catch (SQLException e) {
            // Se houver erro ao verificar/consultar, imprime mensagem com a exceção
            System.err.println("Erro ao verificar dados: " + e.getMessage());
        }
        
        // Variável de controle para o loop do menu principal
        boolean executando = true;

        // Loop principal da aplicação — apresenta o menu e executa ações
        while (executando) {
            exibirMenuPrincipal(); // mostra opções para o usuário
            int opcao = lerOpcao(); // lê a opção escolhida
            System.out.println();

            switch (opcao) {
                case 1:
                    menuClientes(); // abrir submenu de clientes
                    break;
                case 2:
                    menuVeiculos(); // abrir submenu de veículos
                    break;
                case 3:
                    menuVendas(); // abrir submenu de vendas
                    break;
                case 4:
                    menuRelatorios(); // abrir submenu de relatórios
                    break;
                case 0:
                    // opção para encerrar o sistema
                    System.out.println("Encerrando sistema... Até logo!");
                    executando = false; // sai do loop
                    break;
                default:
                    // entrada inválida (não numérica ou fora das opções)
                    System.out.println("⚠ Opção inválida!");
            }

            if (executando) {
                // Pausa até o usuário pressionar ENTER — cria uma pequena quebra entre ações
                System.out.println("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }

        // Fechar scanner para liberar recursos antes de sair
        scanner.close();
    }

    // Exibe o menu principal com as opções principais do sistema
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
        // Solicita a opção do usuário
        System.out.print("Opção: ");
    }
    
    // ----- MENU CLIENTES -----
    private static void menuClientes() {
        // Imprime o cabeçalho do submenu de clientes
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
        
        int opcao = lerOpcao(); // lê a opção escolhida pelo usuário
        System.out.println();
        
        try {
            switch (opcao) {
                case 1:
                    cadastrarCliente(); // chama rotina de cadastro
                    break;
                case 2:
                    listarClientes(); // lista todos os clientes
                    break;
                case 3:
                    buscarClientePorCpf(); // busca por CPF
                    break;
                case 4:
                    atualizarCliente(); // atualiza dados do cliente
                    break;
                case 5:
                    deletarCliente(); // remove cliente
                    break;
                case 0:
                    // volta ao menu principal
                    break;
                default:
                    System.out.println("⚠ Opção inválida!");
            }
        } catch (SQLException e) {
            // Tratamento genérico de erros de banco nesse menu
            System.err.println("✗ Erro ao executar operação: " + e.getMessage());
        }
    }
    
    // ----- MENU VEÍCULOS -----
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
    
    // ----- MENU VENDAS -----
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
    
    // ----- MENU RELATÓRIOS -----
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
    
    // Método para cadastrar um novo cliente
    private static void cadastrarCliente() throws SQLException {
        System.out.println("=== CADASTRAR NOVO CLIENTE ===");
        
        // Lê o nome do usuário
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        // Validação de CPF (loop até fornecer um CPF válido e não cadastrado)
        String cpf = null;
        boolean cpfValido = false;
        while (!cpfValido) {
            System.out.print("CPF (apenas números): ");
            cpf = scanner.nextLine();
            
            // Chama função auxiliar validarCPF — verifica apenas dígitos e tamanho 11
            if (!validarCPF(cpf)) {
                System.out.println("✗ CPF inválido! Use apenas números (ex: 12345678900)");
                continue; // volta ao começo do loop
            }
            
            // Verifica duplicidade no banco
            if (clienteDAO.buscarPorCpf(cpf) != null) {
                System.out.println("✗ CPF já cadastrado!");
                continue;
            }
            
            cpfValido = true; // CPF aceito
        }
        
        // Lê telefone (opcional)
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        // Validação de email: pede até que seja vazio (opcional) ou válido
        String email = null;
        boolean emailValido = false;
        while (!emailValido) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            
            if (email.isEmpty()) {
                // Email é opcional — aceita vazio
                email = null;
                break;
            }
            
            // Chama validarEmail — verifica presença de @ e ponto
            if (!validarEmail(email)) {
                System.out.println("✗ Email inválido! Deve conter @ e .");
                continue; // continua pedindo
            }
            
            emailValido = true; // email aceito
        }
        
        // Cria objeto Cliente com os dados coletados (telefone/email nulos se vazios)
        Cliente cliente = new Cliente(0, nome, cpf, telefone.isEmpty() ? null : telefone, email);
        // Insere no banco via DAO e recebe o id gerado
        int id = clienteDAO.inserir(cliente);
        
        if (id > 0) {
            System.out.println("✓ Cliente cadastrado com sucesso! ID: " + id);
        } else {
            System.out.println("✗ Erro ao cadastrar cliente!");
        }
    }
    
    // Lista todos os clientes existentes
    private static void listarClientes() throws SQLException {
        System.out.println("=== LISTA DE CLIENTES ===");
        List<Cliente> clientes = clienteDAO.listarTodos(); // obtém lista via DAO
        
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                // Imprime representação string do cliente (toString em Cliente.java)
                System.out.println(c);
            }
            System.out.println("\nTotal: " + clientes.size() + " cliente(s)");
        }
    }

    // Busca e exibe cliente por CPF
    private static void buscarClientePorCpf() throws SQLException {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = clienteDAO.buscarPorCpf(cpf); // procura no banco
        
        if (cliente != null) {
            System.out.println("\n" + cliente);
        } else {
            System.out.println("✗ Cliente não encontrado!");
        }
    }

    // Atualiza dados de um cliente existente
    private static void atualizarCliente() throws SQLException {
        System.out.print("Digite o ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine()); // lê e converte para int
        
        Cliente cliente = clienteDAO.buscarPorId(id); // busca o cliente pelo ID
        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return; // encerra o método
        }
        
        // Mostra o cliente atual e permite deixar em branco para manter
        System.out.println("Cliente atual: " + cliente);
        System.out.println("\nDeixe em branco para manter o valor atual.");
        
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) cliente.setNome(nome); // atualiza se informado
        
        System.out.print("Novo telefone: ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) cliente.setTelefone(telefone);
        
        System.out.print("Novo email: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            // Valida o email; se inválido, pede novamente até ser válido ou vazio
            while (!validarEmail(email)) {
                System.out.println("✗ Email inválido! Deve conter @ e .");
                System.out.print("Novo email: ");
                email = scanner.nextLine();
                if (email.isEmpty()) break; // permite cancelar alteração
            }
            if (!email.isEmpty()) cliente.setEmail(email);
        }
        
        // Persiste as alterações via DAO
        clienteDAO.atualizar(cliente);
        System.out.println("✓ Cliente atualizado com sucesso!");
    }

    // Exclui um cliente por ID
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
            clienteDAO.deletar(id); // deleta via DAO
            System.out.println("✓ Cliente deletado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ===== MÉTODOS DE VEÍCULOS =====
    // (comentários resumidos — estrutura similar a clientes: CRUD + pesquisas)
    private static void cadastrarVeiculo() throws SQLException {
        System.out.println("=== CADASTRAR NOVO VEÍCULO ===");
        System.out.println("Tipo: 1-Carro  2-Moto  3-Caminhão");
        System.out.print("Escolha: ");
        int tipo = Integer.parseInt(scanner.nextLine()); // tipo escolhido
        
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        
        System.out.print("Ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Preço: ");
        double preco = Double.parseDouble(scanner.nextLine());
        
        Veiculo veiculo = null; // variável para receber a instância específica
        
        switch (tipo) {
            case 1:
                // Se for carro, pede número de portas e cria Carro
                System.out.print("Número de portas: ");
                int portas = Integer.parseInt(scanner.nextLine());
                veiculo = new Carro(0, marca, modelo, ano, preco, false, portas);
                break;
            case 2:
                // Se for moto, pede cilindradas
                System.out.print("Cilindradas: ");
                String cilindradas = scanner.nextLine();
                veiculo = new Moto(0, marca, modelo, ano, preco, false, cilindradas);
                break;
            case 3:
                // Se for caminhão, pede capacidade em toneladas
                System.out.print("Capacidade (toneladas): ");
                double capacidade = Double.parseDouble(scanner.nextLine());
                veiculo = new Caminhao(0, marca, modelo, ano, preco, false, capacidade);
                break;
            default:
                System.out.println("✗ Tipo inválido!");
                return; // encerra cadastro em caso de tipo inválido
        }
        
        // Insere veículo via DAO e mostra resultado
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
                System.out.println(v); // imprime toString de cada veículo
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
            for (Veiculo v : veiculos) System.out.println(v);
            System.out.println("\nTotal: " + veiculos.size() + " veículo(s) disponível(is)");
        }
    }

    private static void buscarVeiculoPorId() throws SQLException {
        System.out.print("Digite o ID do veículo: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Veiculo veiculo = veiculoDAO.buscarPorId(id);
        
        if (veiculo != null) System.out.println("\n" + veiculo);
        else System.out.println("✗ Veículo não encontrado!");
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
        
        // Obtém veículos disponíveis para venda
        List<Veiculo> veiculosDisponiveis = veiculoDAO.listarDisponiveis();
        if (veiculosDisponiveis.isEmpty()) {
            System.out.println("✗ Não há veículos disponíveis para venda!");
            return;
        }
        
        // Mostra lista e pede o ID do veículo
        System.out.println("\n--- Veículos Disponíveis ---");
        for (Veiculo v : veiculosDisponiveis) System.out.println(v);
        
        System.out.print("\nID do veículo: ");
        int veiculoId = Integer.parseInt(scanner.nextLine());
        
        Veiculo veiculo = veiculoDAO.buscarPorId(veiculoId);
        if (veiculo == null || veiculo.isVendido()) {
            System.out.println("✗ Veículo não disponível!");
            return;
        }
        
        // Lista clientes para seleção
        System.out.println("\n--- Clientes Cadastrados ---");
        List<Cliente> clientes = clienteDAO.listarTodos();
        for (Cliente c : clientes) System.out.println(c);
        
        System.out.print("\nID do cliente: ");
        int clienteId = Integer.parseInt(scanner.nextLine());
        
        Cliente cliente = clienteDAO.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("✗ Cliente não encontrado!");
            return;
        }
        
        // Valor da venda: se o usuário apertar Enter, usa o preço do veículo
        System.out.print("\nValor da venda (Enter para preço do veículo R$ " + String.format("%.2f", veiculo.getPreco()) + "): ");
        String valorStr = scanner.nextLine();
        double valor = valorStr.isEmpty() ? veiculo.getPreco() : Double.parseDouble(valorStr);
        
        // Data atual
        String data = LocalDate.now().toString();
        
        // Cria objeto Venda e insere via DAO
        Venda venda = new Venda(0, clienteId, veiculoId, data, valor);
        int id = vendaDAO.inserir(venda);
        
        if (id > 0) {
            // Se inseriu com sucesso, marca veículo como vendido
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
            for (Venda v : vendas) System.out.println(v);
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
            for (Venda v : vendas) System.out.println(v);
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
            // Reverte o status do veículo para disponível
            Veiculo veiculo = veiculoDAO.buscarPorId(venda.getVeiculoId());
            if (veiculo != null) {
                veiculo.setVendido(false);
                veiculoDAO.atualizar(veiculo);
            }
            
            // Remove a venda do banco
            vendaDAO.deletar(id);
            System.out.println("✓ Venda cancelada com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ===== MÉTODOS DE RELATÓRIOS =====
    private static void relatorioVendasCompleto() throws SQLException {
        // Exibe relatório detalhado de todas as vendas com cliente e veículo
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
    // Lê opção do usuário e trata entrada inválida
    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // retorna valor inválido para indicar erro
        }
    }

    // Popula o banco com dados de exemplo para facilitar testes
    private static void popularDadosExemplo() {
        System.out.println("=== Populando banco com dados de exemplo ===");
        
        try {
            // Insere alguns clientes de exemplo
            clienteDAO.inserir(new Cliente(0, "João Silva", "123.456.789-00", "(11) 98765-4321", "joao@email.com"));
            clienteDAO.inserir(new Cliente(0, "Maria Santos", "987.654.321-00", "(11) 91234-5678", "maria@email.com"));
            clienteDAO.inserir(new Cliente(0, "Pedro Oliveira", "456.789.123-00", "(11) 99876-5432", "pedro@email.com"));
            
            // Insere veículos de exemplo (Carro/Moto/Caminhao)
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
    // Verifica se CPF é composto apenas por dígitos e tem 11 caracteres
    private static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) return false;
        cpf = cpf.trim();
        if (!cpf.matches("\\d+")) return false; // permite apenas dígitos
        return cpf.length() == 11; // CPF padrão tem 11 dígitos
    }
    
    // Validação simples de email: presença de '@' e '.'
    private static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return email.contains("@") && email.contains(".");
    }
}
