package ENTITY;

// Classe del modello che rappresenta la carta di debito

public class CartaDiDebito extends Carta{

    //Attributi
    private double limiteFondi;


    //Costruttori
    public CartaDiDebito(String pan, String pin, String cvv, String tipoCarta, ContoCorrente contoCorrente, double limiteFondi){
        super(pan, pin, cvv, tipoCarta, contoCorrente);
        setLimiteFondi(limiteFondi);
    }

    //Getter e Setter
    public double getLimiteFondi() {
        return limiteFondi;
    }

    public void setLimiteFondi(double limiteFondi) {
        this.limiteFondi = limiteFondi;
    }

    @Override
    public String toString() {
        return super.toString() +
         "CartaDiDebito{" +
                "limiteFondi=" + limiteFondi +
                '}';
    }
}
