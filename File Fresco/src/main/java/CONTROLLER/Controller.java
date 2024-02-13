package CONTROLLER;

import DAO.AccountDao;
import DAO.ContoCorrenteDAO;
import DAO.PersonaDAO;
import DAOIMPL.AccountDAOImpl;
import DAOIMPL.ContoCorrenteDAOImpl;
import DAOIMPL.PersonaDAOImpl;
import ENTITY.*;
import EXCEPTIONS.MyExc;
import GUI.BankAccountPickViewGUI;
import GUI.HomePageGUI;
import GUI.LoginViewGUI;
import GUI.SignInViewGUI;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

// Classe del controller
public class Controller {

    //Dichiarazioni delle Gui
    private LoginViewGUI frameLogin;
    private SignInViewGUI frameSignIn;
    private BankAccountPickViewGUI framePick;
    private HomePageGUI frameHome;

    //Dichiarazioni delle Dao
    private AccountDao accountDao;
    private PersonaDAO personaDao;
    private ContoCorrenteDAO contoCorrenteDAO;

    //Dichiarazione di un account null
    public Account account = null;
    public ArrayList<ContoCorrente> conti = null;
    public ContoCorrente contoScelto = null;

    public Controller() {
        frameLogin = new LoginViewGUI(this); // Assumi che LoginView accetti ControllerLogin come parametro
        frameLogin(true);

        frameSignIn = new SignInViewGUI(this);


        //DAO
        this.accountDao = new AccountDAOImpl(); // Assumi che tu abbia un costruttore predefinito
        this.personaDao = new PersonaDAOImpl();
        this.contoCorrenteDAO = new ContoCorrenteDAOImpl();
    }

    public void checkCredentials(String email, String password) throws SQLException {
        if((!email.isEmpty()) && (!password.isEmpty())){
            account = accountDao.checkCredentials(email.toLowerCase(), password);
            if (account != null){
                frameLogin(false);
                framePick = new BankAccountPickViewGUI(this);
                framePick(true);
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
               Account accountInserito = new Account(email, password, username, codiceFiscale);

               if (personaDao.insertUser(nome, cognome, telefono, dataNascita, citta, via, nCivico, cap, codiceFiscale)){
                   JOptionPane.showMessageDialog(
                           frameSignIn,
                           "Dati della persona inseriti!",
                           "Benvenuta/o",
                           JOptionPane.INFORMATION_MESSAGE);
                   insertAccount(email, password, username, codiceFiscale);
               }
               else {
                   JOptionPane.showMessageDialog(
                           frameSignIn,
                           "Credenziali già esistenti!",
                           "Errore",
                           JOptionPane.ERROR_MESSAGE);
               }

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
                accountDao.insertAccount(email, nomeUtente, password, codiceFiscale);
                JOptionPane.showMessageDialog(
                        frameSignIn,
                        "Dati dell'account inseriti!",
                        "Benvenuta/o",
                        JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(
                    frameSignIn,
                    "Inserisci delle credenziali valide!",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean confirmedPassword(String password, String confirmedPassword){
        if (password.equals(confirmedPassword))
            return true;
        else
            return false;
    }


    public ArrayList<ContoCorrente> selectBankAccount(String email){
        conti = new ArrayList<ContoCorrente>();
        conti = contoCorrenteDAO.selectBankAccount(email);

        account.setConti(conti);
        return conti;
    }

    public Boolean insertBankAccount(String email){
        if (contoCorrenteDAO.insertBankAccount(email)){
            return true;
        }
        else
            return false;
    }

    public void deleteBankAccount(String iban){
        contoCorrenteDAO.deleteBankAccount(iban);
        frameHome(false);
        try {
            checkCredentials(account.getEmail(), account.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JOptionPane.showMessageDialog(
                framePick,
                "Conto Corrente con Iban: " +iban+ ", eliminato con successo!",
                "Conto Corrente eliminato",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    public void showHomePage(ContoCorrente conto){
        contoScelto = conto;
        framePick(false);
        frameHome = new HomePageGUI(this);
        frameHome(true);
    }

    public void backLoginPage(){
        account = null;
        if (contoScelto!= null)
            contoScelto = null;

        framePick(false);
        frameLogin(true);
    }

    public void backFramePick(){
        contoScelto = null;
        frameHome(false);
        framePick(true);
    }

    public void frameLogin(Boolean isVisibile){
        frameLogin.setVisible(isVisibile);
    }

    public void frameSignIn(Boolean isVisibile){
        frameSignIn.setVisible(isVisibile);
    }

    public void framePick(Boolean isVisibile){
        framePick.setVisible(isVisibile);
    }

    public void frameHome(Boolean isVisible){
        frameHome.setVisible(isVisible);
    }

}