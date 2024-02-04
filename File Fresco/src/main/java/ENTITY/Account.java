package ENTITY;


// Classe del modello che rappresenta l'account
public class Account {
    private String email;
    private String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter per l'email
    public String getEmail() {
        return email;
    }

    // Getter per la password
    public String getPassword() {
        return password;
    }
}
