package CONTROLLER;

import DAO.AccountDao;
import DAO.PersonaDAO;
import DAOIMPL.AccountDAOImpl;
import DAOIMPL.PersonaDAOImpl;
import ENTITY.*;
import EXCEPTIONS.MyExc;
import GUI.LoginViewGUI;
import GUI.SignInViewGUI;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

// Classe del controller
public class Controller {

    //Dichiarazioni delle Gui
    private LoginViewGUI frameLogin;
    private SignInViewGUI frameSignIn;

    //Dichiarazioni delle Dao
    private AccountDao accountDao;
    private PersonaDAO personaDao;

    //Dichiarazione di un account null
    public Account account = null;

    public Controller() {

        frameLogin = new LoginViewGUI(this); // Assumi che LoginView accetti ControllerLogin come parametro
        frameLogin.setVisible(true);

        frameSignIn = new SignInViewGUI(this);



        //DAO
        this.accountDao = new AccountDAOImpl(); // Assumi che tu abbia un costruttore predefinito
        this.personaDao = new PersonaDAOImpl();

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
                frameLogin.setVisible(false);
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

    public void insertUser(String nome, String cognome, String telefono, String dataNascita, String citta, String via, String nCivico, String cap, String codiceFiscale, String email, String password, String username) throws MyExc {
        if (!nome.isEmpty() && !cognome.isEmpty() && !telefono.isEmpty() && !citta.isEmpty() && !via.isEmpty() && !nCivico.isEmpty() && !cap.isEmpty() && !codiceFiscale.isEmpty()){
           try {
               Persona persona = new Persona(nome, cognome, telefono, dataNascita, citta, via, nCivico, cap, codiceFiscale);
               personaDao.insertUser(nome, cognome, telefono, dataNascita, citta, via, nCivico, cap, codiceFiscale);
               JOptionPane.showMessageDialog(
                       frameSignIn,
                       "Dati della persona inseriti!",
                       "Benvenuta/o",
                       JOptionPane.ERROR_MESSAGE);
               insertAccount(email, password, username, codiceFiscale);
           } catch (MyExc e){
               JOptionPane.showMessageDialog(
                       frameSignIn,
                       e.getMessage(),
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

    public void insertAccount(String email, String nomeUtente, String password, String codiceFiscale){
        if (!email.isEmpty() && !nomeUtente.isEmpty() && !password.isEmpty() && !codiceFiscale.isEmpty()) {
            try {
                Account account = new Account(email, nomeUtente, password, codiceFiscale);
                accountDao.insertAccount(email, nomeUtente, password, codiceFiscale);
                JOptionPane.showMessageDialog(
                        frameSignIn,
                        "Dati dell'account inseriti!",
                        "Benvenuta/o",
                        JOptionPane.ERROR_MESSAGE);
            }
            catch (MyExc e){
                JOptionPane.showMessageDialog(
                        frameSignIn,
                        e.getMessage(),
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