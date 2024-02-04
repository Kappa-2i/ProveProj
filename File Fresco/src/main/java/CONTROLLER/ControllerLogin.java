package CONTROLLER;

import DAO.AccountDao;
import DAOIMPL.AccountDAOImpl;
import ENTITY.*;
import GUI.LoginView;
import GUI.SignInView;

import javax.swing.*;
import java.sql.SQLException;

// Classe del controller
public class ControllerLogin {

    //Dichiarazioni delle Gui
    private LoginView frameLogin;
    private SignInView frameSignIn;

    //Dichiarazioni delle Dao
    private AccountDao accountDao;

    //Dichiarazione di un account null
    public Account account = null;

    public ControllerLogin() {
        this.accountDao = new AccountDAOImpl(); // Assumi che tu abbia un costruttore predefinito
    }

    // Metodo per avviare la GUI di login
    public void startLogin() {
        final ControllerLogin self = this;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView(self);
            }
        });
    }



    public void checkCredentials(String email, String password) throws SQLException {
        if((!email.isEmpty()) && (!password.isEmpty())){
            account = accountDao.checkCredentials(email.toLowerCase(), password);
            if (account != null){
                frameSignIn = new SignInView();
                frameLogin.setVisible(false);
                frameSignIn.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(
                        null,
                        "Email o Password Errati",
                        "Errore di Login",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}

