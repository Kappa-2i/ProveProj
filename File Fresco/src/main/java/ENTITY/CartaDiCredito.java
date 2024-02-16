package ENTITY;

public class CartaDiCredito extends Carta{
    private double maxInvio;

    public CartaDiCredito(String pan, String pin, String cvv, String tipoCarta,  ContoCorrente contoCorrente, double maxInvio){
        super(pan, pin, cvv, tipoCarta, contoCorrente);
        setMaxInvio(maxInvio);
    }

    public double getMaxInvio() {
        return maxInvio;
    }

    public void setMaxInvio(double maxInvio) {
        this.maxInvio = maxInvio;
    }

    @Override
    public String toString() {
        return super.toString() +
                "CartaDiCredito{" +
                "maxInvio=" + maxInvio +
                '}';
    }
}
