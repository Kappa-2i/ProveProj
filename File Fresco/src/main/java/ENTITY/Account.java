package ENTITY;


// Classe del modello che rappresenta l'account
public class Account {
    private String email;
    private String password;
    private String nomeutente;
    private String codiceFiscale;

    public Account(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public Account(String email, String password, String nomeutente){
        setEmail(email);
        setPassword(password);
        setNomeutente(nomeutente);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNomeutente() {
        return nomeutente;
    }

    public void setNomeutente(String nomeutente) {
        this.nomeutente = nomeutente;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
}
