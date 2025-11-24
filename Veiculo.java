public abstract class Veiculo {
protected int id;
protected String marca;
protected String modelo;
protected int ano;
protected double preco;
protected boolean vendido;


public Veiculo() {}


public Veiculo(int id, String marca, String modelo, int ano, double preco, boolean vendido) {
this.id = id;
this.marca = marca;
this.modelo = modelo;
this.ano = ano;
this.preco = preco;
this.vendido = vendido;
}


public abstract String getTipo();


// getters and setters
public int getId() { return id; }
public void setId(int id) { this.id = id; }
public String getMarca() { return marca; }
public void setMarca(String marca) { this.marca = marca; }
public String getModelo() { return modelo; }
public void setModelo(String modelo) { this.modelo = modelo; }
public int getAno() { return ano; }
public void setAno(int ano) { this.ano = ano; }
public double getPreco() { return preco; }
public void setPreco(double preco) { this.preco = preco; }
public boolean isVendido() { return vendido; }
public void setVendido(boolean vendido) { this.vendido = vendido; }


@Override
public String toString() {
return String.format("[ID=%d] %s %s (%d) - R$ %.2f - %s", id, marca, modelo, ano, preco, (vendido?"VENDIDO":"DISPONÍVEL"));
}
}