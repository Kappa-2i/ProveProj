package ENTITY;

public class CartaDiDebito extends Carta{
    private double limiteFondi;

    public CartaDiDebito(String pan, String pin, String cvv, String tipoCarta, ContoCorrente contoCorrente, double limiteFondi){
        super(pan, pin, cvv, tipoCarta, contoCorrente);
        setLimiteFondi(limiteFondi);
    }

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
