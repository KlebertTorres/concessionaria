public class Moto extends Veiculo {
private String cilindradas;


public Moto() { super(); }


public Moto(int id, String marca, String modelo, int ano, double preco, boolean vendido, String cilindradas) {
super(id, marca, modelo, ano, preco, vendido);
this.cilindradas = cilindradas;
}


public String getCilindradas() { return cilindradas; }
public void setCilindradas(String cilindradas) { this.cilindradas = cilindradas; }


@Override
public String getTipo() { return "moto"; }


@Override
public String toString() {
return String.format("%s - Moto %s", super.toString(), cilindradas);
}
}