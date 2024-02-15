package ENTITY;

public class Carta {
    private String pan;
    private String pin;
    private String cvv;
    private ContoCorrente contoCorrente;

    public Carta(String pan, String pin, String cvv, ContoCorrente contoCorrente) {
        setCvv(cvv);
        setPan(pan);
        setPin(pin);
        setContoCorrente(contoCorrente);
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public ContoCorrente getContoCorrente() {
        return contoCorrente;
    }

    public void setContoCorrente(ContoCorrente contoCorrente) {
        this.contoCorrente = contoCorrente;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "pan='" + pan + '\'' +
                ", pin='" + pin + '\'' +
                ", cvv='" + cvv + '\'' +
                ", contoCorrente=" + contoCorrente +
                '}';
    }
}
