import java.sql.*;

// Classe responsável pela conexão e inicialização do banco de dados
public class DBHelper {
    private static final String URL = "jdbc:sqlite:concessionaria.db";
    
    // Bloco estático executado uma vez ao carregar a classe
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Driver SQLite JDBC não encontrado!");
            e.printStackTrace();
        }
    }
    
    // Retorna uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
    // Cria as tabelas veiculos, clientes e vendas se não existirem
    public static void initializeDatabase() {
        System.out.println("=== Inicializando banco de dados ===");
        
        String sqlVeiculos = "CREATE TABLE IF NOT EXISTS veiculos ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "tipo TEXT NOT NULL,"
            + "marca TEXT NOT NULL,"
            + "modelo TEXT NOT NULL,"
            + "ano INTEGER NOT NULL,"
            + "preco REAL NOT NULL,"
            + "vendido INTEGER NOT NULL DEFAULT 0,"
            + "portas INTEGER,"
            + "cilindradas TEXT,"
            + "capacidade_ton REAL"
            + ");";
        
        String sqlClientes = "CREATE TABLE IF NOT EXISTS clientes ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "nome TEXT NOT NULL,"
            + "cpf TEXT UNIQUE NOT NULL,"
            + "telefone TEXT,"
            + "email TEXT"
            + ");";
        
        String sqlVendas = "CREATE TABLE IF NOT EXISTS vendas ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "cliente_id INTEGER NOT NULL,"
            + "veiculo_id INTEGER NOT NULL,"
            + "data TEXT NOT NULL,"
            + "valor REAL NOT NULL,"
            + "FOREIGN KEY(cliente_id) REFERENCES clientes(id),"
            + "FOREIGN KEY(veiculo_id) REFERENCES veiculos(id)"
            + ");";
        
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute(sqlVeiculos);
            st.execute(sqlClientes);
            st.execute(sqlVendas);
            System.out.println("✓ Tabelas criadas/verificadas com sucesso!");
        } catch (SQLException e) {
            System.err.println("✗ Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }
    
    // Verifica se consegue conectar ao banco
    public static boolean verificarConexao() {
        try (Connection conn = getConnection()) {
            System.out.println("✓ Conexão com o banco de dados estabelecida!");
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Erro ao conectar com o banco de dados: " + e.getMessage());
            return false;
        }
    }
}
