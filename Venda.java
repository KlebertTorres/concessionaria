public class Venda {
private int id;
private int clienteId;
private int veiculoId;
private String data; // formato simples YYYY-MM-DD
private double valor;


public Venda() {}


public Venda(int id, int clienteId, int veiculoId, String data, double valor) {
this.id = id;
this.clienteId = clienteId;
this.veiculoId = veiculoId;
this.data = data;
this.valor = valor;
}


// getters/setters
public int getId() { return id; }
public void setId(int id) { this.id = id; }
public int getClienteId() { return clienteId; }
public void setClienteId(int clienteId) { this.clienteId = clienteId; }
public int getVeiculoId() { return veiculoId; }
public void setVeiculoId(int veiculoId) { this.veiculoId = veiculoId; }
public String getData() { return data; }
public void setData(String data) { this.data = data; }
public double getValor() { return valor; }
public void setValor(double valor) { this.valor = valor; }


@Override
public String toString() {
return String.format("[ID=%d] ClienteID=%d VeiculoID=%d Data=%s Valor=R$ %.2f", id, clienteId, veiculoId, data, valor);
}
}