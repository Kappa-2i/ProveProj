package ENTITY;


// Classe del modello che rappresenta l'account
public class Account {
    private String email;
    private String password;

    public Account(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    // Getter per l'email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter per la password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
