import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    
    public int inserir(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (cliente_id, veiculo_id, data, valor) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, venda.getClienteId());
            ps.setInt(2, venda.getVeiculoId());
            ps.setString(3, venda.getData());
            ps.setDouble(4, venda.getValor());
            
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
    
    public Venda buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM vendas WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extrairVenda(rs);
                }
            }
        }
        return null;
    }
    
    public List<Venda> listarTodas() throws SQLException {
        String sql = "SELECT * FROM vendas ORDER BY data DESC";
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                vendas.add(extrairVenda(rs));
            }
        }
        
        return vendas;
    }
    
    public List<Venda> listarPorCliente(int clienteId) throws SQLException {
        String sql = "SELECT * FROM vendas WHERE cliente_id = ? ORDER BY data DESC";
        List<Venda> vendas = new ArrayList<>();
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, clienteId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vendas.add(extrairVenda(rs));
                }
            }
        }
        
        return vendas;
    }
    
    public Venda buscarPorVeiculo(int veiculoId) throws SQLException {
        String sql = "SELECT * FROM vendas WHERE veiculo_id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, veiculoId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extrairVenda(rs);
                }
            }
        }
        return null;
    }
    
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM vendas WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    private Venda extrairVenda(ResultSet rs) throws SQLException {
        return new Venda(
            rs.getInt("id"),
            rs.getInt("cliente_id"),
            rs.getInt("veiculo_id"),
            rs.getString("data"),
            rs.getDouble("valor")
        );
    }
}
