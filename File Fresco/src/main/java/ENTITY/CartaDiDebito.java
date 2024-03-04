package ENTITY;

// Classe del modello che rappresenta la carta di debito

public class CartaDiDebito extends Carta{

    //Attributi
    private double maxInvio;

    //Costruttori
    public CartaDiDebito(String pan, String pin, String cvv, String tipoCarta, ContoCorrente contoCorrente, double maxInvio){
        super(pan, pin, cvv, tipoCarta, contoCorrente);
        setMaxInvio(maxInvio);
    }

    //Getter e Setter
    public double getMaxInvio() {
        return maxInvio;
    }

    public void setMaxInvio(double maxInvio) {
        this.maxInvio = maxInvio;
    }

}
