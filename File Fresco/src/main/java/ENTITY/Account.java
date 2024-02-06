package ENTITY;


import EXCEPTIONS.MyExc;

// Classe del modello che rappresenta l'account
public class Account {
    private String email;
    private String password;
    private String nomeutente;
    private String codiceFiscale;

    public Account(String email, String password, String nomeutente) throws MyExc {
        setEmail(email);
        setPassword(password);
        setNomeutente(nomeutente);
    }

    public Account(String email, String password, String nomeutente, String codiceFiscale) throws MyExc {
        setEmail(email);
        setPassword(password);
        setNomeutente(nomeutente);
        setCodiceFiscale(codiceFiscale);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws MyExc {
        // Verifica che la stringa "email" contenga esattamente una "@" e nessuna altra occorrenza
        if (countOccurrences(email, '@') != 1)
            throw new MyExc("L'email deve contenere esattamente una carattere '@'.");
        else
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

    // Metodo per contare le occorrenze di un carattere in una stringa
    private int countOccurrences(String str, char character) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == character) {
                count++;
            }
        }
        return count;
    }
}
