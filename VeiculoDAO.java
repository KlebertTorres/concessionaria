import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    
    public int inserir(Veiculo veiculo) throws SQLException {
        String sql = "INSERT INTO veiculos (tipo, marca, modelo, ano, preco, vendido, portas, cilindradas, capacidade_ton) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, veiculo.getTipo());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());
            ps.setInt(4, veiculo.getAno());
            ps.setDouble(5, veiculo.getPreco());
            ps.setInt(6, veiculo.isVendido() ? 1 : 0);
            
            if (veiculo instanceof Carro) {
                ps.setInt(7, ((Carro) veiculo).getPortas());
                ps.setNull(8, Types.VARCHAR);
                ps.setNull(9, Types.REAL);
            } else if (veiculo instanceof Moto) {
                ps.setNull(7, Types.INTEGER);
                ps.setString(8, ((Moto) veiculo).getCilindradas());
                ps.setNull(9, Types.REAL);
            } else if (veiculo instanceof Caminhao) {
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.VARCHAR);
                ps.setDouble(9, ((Caminhao) veiculo).getCapacidadeTon());
            }
            
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
    
    public Veiculo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extrairVeiculo(rs);
                }
            }
        }
        return null;
    }
    
    public List<Veiculo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM veiculos ORDER BY marca, modelo";
        List<Veiculo> veiculos = new ArrayList<>();
        
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                veiculos.add(extrairVeiculo(rs));
            }
        }
        
        return veiculos;
    }
    
    public List<Veiculo> listarDisponiveis() throws SQLException {
        String sql = "SELECT * FROM veiculos WHERE vendido = 0 ORDER BY marca, modelo";
        List<Veiculo> veiculos = new ArrayList<>();
        
        try (Connection conn = DBHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                veiculos.add(extrairVeiculo(rs));
            }
        }
        
        return veiculos;
    }
    
    public void atualizar(Veiculo veiculo) throws SQLException {
        String sql = "UPDATE veiculos SET tipo = ?, marca = ?, modelo = ?, ano = ?, preco = ?, vendido = ?, "
                   + "portas = ?, cilindradas = ?, capacidade_ton = ? WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, veiculo.getTipo());
            ps.setString(2, veiculo.getMarca());
            ps.setString(3, veiculo.getModelo());
            ps.setInt(4, veiculo.getAno());
            ps.setDouble(5, veiculo.getPreco());
            ps.setInt(6, veiculo.isVendido() ? 1 : 0);
            
            if (veiculo instanceof Carro) {
                ps.setInt(7, ((Carro) veiculo).getPortas());
                ps.setNull(8, Types.VARCHAR);
                ps.setNull(9, Types.REAL);
            } else if (veiculo instanceof Moto) {
                ps.setNull(7, Types.INTEGER);
                ps.setString(8, ((Moto) veiculo).getCilindradas());
                ps.setNull(9, Types.REAL);
            } else if (veiculo instanceof Caminhao) {
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.VARCHAR);
                ps.setDouble(9, ((Caminhao) veiculo).getCapacidadeTon());
            }
            
            ps.setInt(10, veiculo.getId());
            ps.executeUpdate();
        }
    }
    
    public void marcarComoVendido(int id) throws SQLException {
        String sql = "UPDATE veiculos SET vendido = 1 WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM veiculos WHERE id = ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    private Veiculo extrairVeiculo(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        int id = rs.getInt("id");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        int ano = rs.getInt("ano");
        double preco = rs.getDouble("preco");
        boolean vendido = rs.getInt("vendido") == 1;
        
        switch (tipo) {
            case "carro":
                int portas = rs.getInt("portas");
                return new Carro(id, marca, modelo, ano, preco, vendido, portas);
            
            case "moto":
                String cilindradas = rs.getString("cilindradas");
                return new Moto(id, marca, modelo, ano, preco, vendido, cilindradas);
            
            case "caminhao":
                double capacidadeTon = rs.getDouble("capacidade_ton");
                return new Caminhao(id, marca, modelo, ano, preco, vendido, capacidadeTon);
            
            default:
                throw new SQLException("Tipo de veículo desconhecido: " + tipo);
        }
    }
}
