package ENTITY;

// Classe del modello che rappresenta la carta

public class Carta {

    //Attributi
    private String pan;
    private String pin;
    private String cvv;
    private String tipoCarta;
    private ContoCorrente contoCorrente;

    //Costruttori
    public Carta(String pan, String pin, String cvv, String tipoCarta, ContoCorrente contoCorrente) {
        setCvv(cvv);
        setPan(pan);
        setPin(pin);
        setTipoCarta(tipoCarta);
        setContoCorrente(contoCorrente);
    }

    //Getter e Setter
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

    public String getTipoCarta() {
        return tipoCarta;
    }

    public void setTipoCarta(String tipoCarta) {
        this.tipoCarta = tipoCarta;
    }

    public ContoCorrente getContoCorrente() {
        return contoCorrente;
    }

    public void setContoCorrente(ContoCorrente contoCorrente) {
        this.contoCorrente = contoCorrente;
    }


}
