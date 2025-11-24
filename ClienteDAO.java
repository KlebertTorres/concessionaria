import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    public int inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());
            
            ps.executeUpdate();
            
            // SQLite specific way to get last inserted ID
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
    
    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extrairCliente(rs);
                }
            }
        }
        return null;
    }
    
    public Cliente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE cpf = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cpf);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extrairCliente(rs);
                }
            }
        }
        return null;
    }
    
    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM clientes ORDER BY nome";
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(extrairCliente(rs));
            }
        }
        
        return clientes;
    }
    
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEmail());
            ps.setInt(5, cliente.getId());
            
            ps.executeUpdate();
        }
    }
    
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    private Cliente extrairCliente(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getString("telefone"),
            rs.getString("email")
        );
    }
}
