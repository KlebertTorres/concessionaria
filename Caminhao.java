public class Caminhao extends Veiculo {
private double capacidadeTon;


public Caminhao() { super(); }


public Caminhao(int id, String marca, String modelo, int ano, double preco, boolean vendido, double capacidadeTon) {
super(id, marca, modelo, ano, preco, vendido);
this.capacidadeTon = capacidadeTon;
}


public double getCapacidadeTon() { return capacidadeTon; }
public void setCapacidadeTon(double capacidadeTon) { this.capacidadeTon = capacidadeTon; }


@Override
public String getTipo() { return "caminhao"; }


@Override
public String toString() {
return String.format("%s - Caminhão %.1f t", super.toString(), capacidadeTon);
}
}