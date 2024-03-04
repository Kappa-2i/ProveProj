package ENTITY;

// Classe del modello che rappresenta la carta di debito

public class CartaDiDebito extends Carta{

    //Costruttori
    public CartaDiDebito(String pan, String pin, String cvv, String tipoCarta, ContoCorrente contoCorrente){
        super(pan, pin, cvv, tipoCarta, contoCorrente);
    }

}
