package ENTITY;

public class Transazione {
    private double importo;
    private String causale;
    private String dataTransazione;
    private String orarioTransazione;
    private String tipoTransazione;
    private String iban; //iban di chi ci manda soldi, o a chi inviamo soldi
    private String categoriaEntrata;
    private String categoriaUscita;
    private String nameCollection;
    private ContoCorrente contoCorrente;

    public Transazione(double importo, String causale, String dataTransazione, String orarioTransazione, String tipoTransazione, String iban, String categoriaEntrata, String categoriaUscita, String nameCollection, ContoCorrente contoCorrente) {
        setImporto(importo);
        setCausale(causale);
        setDataTransazione(dataTransazione);
        setOrarioTransazione(orarioTransazione);
        setTipoTransazione(tipoTransazione);
        setIban(iban);
        setCategoriaEntrata(categoriaEntrata);
        setCategoriaUscita(categoriaUscita);
        setNameCollection(nameCollection);
        setContoCorrente(contoCorrente);
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public String getCausale() {
        return causale;
    }

    public void setCausale(String causale) {
        this.causale = causale;
    }

    public String getDataTransazione() {
        return dataTransazione;
    }

    public void setDataTransazione(String dataTransazione) {
        this.dataTransazione = dataTransazione;
    }

    public String getOrarioTransazione() {
        return orarioTransazione;
    }

    public void setOrarioTransazione(String orarioTransazione) {
        this.orarioTransazione = orarioTransazione;
    }

    public String getTipoTransazione() {
        return tipoTransazione;
    }

    public void setTipoTransazione(String tipoTransazione) {
        this.tipoTransazione = tipoTransazione;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public ContoCorrente getContoCorrente() {
        return contoCorrente;
    }

    public void setContoCorrente(ContoCorrente contoCorrente) {
        this.contoCorrente = contoCorrente;
    }

    public String getCategoriaEntrata() {
        return categoriaEntrata;
    }

    public void setCategoriaEntrata(String categoriaEntrata) {
        this.categoriaEntrata = categoriaEntrata;
    }

    public String getCategoriaUscita() {
        return categoriaUscita;
    }

    public void setCategoriaUscita(String categoriaUscita) {
        this.categoriaUscita = categoriaUscita;
    }

    public String getNameCollection() {
        return nameCollection;
    }

    public void setNameCollection(String nameCollection) {
        this.nameCollection = nameCollection;
    }

    @Override
    public String toString() {
        return "Transazione{" +
                "importo=" + importo +
                ", causale='" + causale + '\'' +
                ", dataTransazione='" + dataTransazione + '\'' +
                ", orarioTransazione='" + orarioTransazione + '\'' +
                ", tipoTransazione='" + tipoTransazione + '\'' +
                ", iban='" + iban + '\'' +
                '}';
    }
}
