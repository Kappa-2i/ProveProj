package ENTITY;

public class CartaDiCredito extends Carta{
    private double maxInvio;

    public CartaDiCredito(String pan, String pin, String cvv, ContoCorrente contoCorrente, double maxInvio){
        super(pan, pin, cvv, contoCorrente);
        setMaxInvio(maxInvio);
    }

    public double getMaxInvio() {
        return maxInvio;
    }

    public void setMaxInvio(double maxInvio) {
        this.maxInvio = maxInvio;
    }
}
