public class Carro extends Veiculo {
private int portas;


public Carro() { super(); }


public Carro(int id, String marca, String modelo, int ano, double preco, boolean vendido, int portas) {
super(id, marca, modelo, ano, preco, vendido);
this.portas = portas;
}


public int getPortas() { return portas; }
public void setPortas(int portas) { this.portas = portas; }


@Override
public String getTipo() { return "carro"; }


@Override
public String toString() {
return String.format("%s - Carro %d portas", super.toString(), portas);
}
}