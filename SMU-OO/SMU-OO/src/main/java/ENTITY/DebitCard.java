package ENTITY;

// Classe del modello che rappresenta la carta di debito

public class DebitCard extends Card {

    //Attributi
    private double maxInvio;

    //Costruttori
    public DebitCard(String pan, String pin, String cvv, String tipoCarta, BankAccount bankAccount, double maxInvio){
        super(pan, pin, cvv, tipoCarta, bankAccount);
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
