package CONTROLLER;

import DAO.AccountDao;
import DAOIMPL.AccountDAOImpl;
import ENTITY.*;
import GUI.LoginViewGUI;
import GUI.SignInViewGUI;

import javax.swing.*;
import java.sql.SQLException;

// Classe del controller
public class Controller {

    //Dichiarazioni delle Gui
    private LoginViewGUI frameLogin;
    private SignInViewGUI frameSignIn;

    //Dichiarazioni delle Dao
    private AccountDao accountDao;

    //Dichiarazione di un account null
    public Account account = null;

    public Controller() {

        frameLogin = new LoginViewGUI(this); // Assumi che LoginView accetti ControllerLogin come parametro
        frameLogin.setVisible(true);

        frameSignIn = new SignInViewGUI(this);



        //DAO
        this.accountDao = new AccountDAOImpl(); // Assumi che tu abbia un costruttore predefinito

    }

//    // Metodo per avviare la GUI di login
//    public void startLogin() {
//        final Controller controller = this;
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new LoginViewGUI(controller);
//            }
//        });
//    }

    public void checkCredentials(String email, String password) throws SQLException {
        if((!email.isEmpty()) && (!password.isEmpty())){
            account = accountDao.checkCredentials(email.toLowerCase(), password);
            if (account != null){
//                frameSignIn = new SignInViewGUI(new ControllerSignIn());
//                frameLogin.setVisible(false);
//                frameSignIn.setVisible(true);
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
        else {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci delle credenziali valide!",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertAccount(String email, String nomeUtente, String password, String codiceFiscale){
        if (!email.isEmpty() && !nomeUtente.isEmpty() && !password.isEmpty() && !codiceFiscale.isEmpty()) {
            try {
                Account account = new Account(email, nomeUtente, password, codiceFiscale);
            }
            catch (IllegalArgumentException exception){
                JOptionPane.showMessageDialog(
                        frameSignIn,
                        "Inserisci un'email valida!",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(
                    frameSignIn,
                    "Inserisci delle credenziali valide!",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }

    }


    public void frameLogin(Boolean isVisibile){
        frameLogin.setVisible(isVisibile);
    }

    public void frameSignIn(Boolean isVisibile){
        frameSignIn.setVisible(isVisibile);
    }

}