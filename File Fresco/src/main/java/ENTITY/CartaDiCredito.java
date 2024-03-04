package ENTITY;

// Classe del modello che rappresenta la carta di credito

public class CartaDiCredito extends Carta{

    //Attributi
    private double priceUpgrade;

    //Costruttori
    public CartaDiCredito(String pan, String pin, String cvv, String tipoCarta,  ContoCorrente contoCorrente, double priceUpgrade){
        super(pan, pin, cvv, tipoCarta, contoCorrente);
        setPriceUpgrade(priceUpgrade);
    }

    //Getter e Setter
    public double getPriceUpgrade() {
        return priceUpgrade;
    }

    public void setPriceUpgrade(double priceUpgrade) {
        this.priceUpgrade = priceUpgrade;
    }

}
