package CONTROLLER;

import DAO.*;
import DAOIMPL.*;
import ENTITY.*;
import EXCEPTIONS.MyExc;
import GUI.*;

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
    private CardPageGUI frameCard;
    private SalvadanaioGUI frameSalvadanaio;

    //Dichiarazioni delle Dao
    private AccountDAO accountDao;
    private PersonaDAO personaDao;
    private ContoCorrenteDAO contoCorrenteDAO;
    private CartaDAO cartaDAO;
    private SalvadanaioDAO salvadanaioDAO;

    //Dichiarazione di un account null
    public Account account = null;
    public Persona persona = null;
    public ArrayList<ContoCorrente> conti = null;
    public ContoCorrente contoScelto = null;
    public Carta carta = null;
    public ArrayList<Salvadanaio> salvadanai = null;

    public Controller() {
        frameLogin = new LoginViewGUI(this); // Assumi che LoginView accetti ControllerLogin come parametro
        frameLogin(true);

        //DAO
        this.accountDao = new AccountDAOImpl();
        this.personaDao = new PersonaDAOImpl();
        this.contoCorrenteDAO = new ContoCorrenteDAOImpl();
        this.cartaDAO = new CartaDAOImpl();
        this.salvadanaioDAO = new SalvadanaioDAOImpl();
    }

    public void checkCredentials(String email, String password) throws SQLException {
        if((!email.isEmpty()) && (!password.isEmpty())){
            account = accountDao.checkCredentials(email.toLowerCase(), password);
            if (account != null){
                persona = personaDao.selectPersonaFromEmail(email.toLowerCase());
                account.setPersona(persona);
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

    public void showFrameSignIn(){
        frameLogin(false);
        frameSignIn = new SignInViewGUI(this);
        frameSignIn(true);
    }

    public void insertUser(String nome, String cognome, String telefono, String dataNascita, String citta, String via, String nCivico, String cap, String codiceFiscale, String email, String password, String username) throws MyExc {
        if (!nome.isEmpty() && !cognome.isEmpty() && !telefono.isEmpty() && !citta.isEmpty() && !via.isEmpty() && !nCivico.isEmpty() && !cap.isEmpty() && !codiceFiscale.isEmpty()){
           try {
               Persona personaInserita = new Persona(nome, cognome, telefono, dataNascita, citta, via, nCivico, cap, codiceFiscale);
               Account accountInserito = new Account(email, password, username);

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


    public ArrayList<ContoCorrente> selectBankAccount(Account account){
        conti = new ArrayList<ContoCorrente>();
        conti = contoCorrenteDAO.selectBankAccount(account);
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
        carta = cartaDAO.selectCard(contoScelto);
        salvadanai = salvadanaioDAO.selectSalvadanaio(contoScelto);
        contoScelto.setSalvadanai(salvadanai);
        System.out.println(contoScelto.toString());
        framePick(false);
        frameHome = new HomePageGUI(this);
        frameHome(true);
    }

    public void backLoginPage(){
        account = null;
        if (contoScelto!= null)
            contoScelto = null;

        framePick(false);
        frameHome(false);
        frameLogin(true);
    }

    public void backFramePick(){
        contoScelto = null;
        frameHome(false);
        framePick(true);
    }

    public void showCardPage(){
        frameCard = new CardPageGUI(this);
        frameCard(true);
    }

    public void showSalvadanaioPage(){
        frameSalvadanaio = new SalvadanaioGUI(this);
        frameHome(false);
        frameSalvadanaio(true);
    }

    public void upgradeCarta(String pan){
        cartaDAO.upgradeCarta(pan);
        ImageIcon iconChecked = new ImageIcon(Controller.class.getResource("/IMG/checked.png"));
        JOptionPane.showMessageDialog(
                null,
                "La tua carta è stata aggiornata a carta di credito!",
                "Aggiornamento effettuato",
                JOptionPane.PLAIN_MESSAGE,
                iconChecked
        );
        frameHome(false);
        showHomePage(contoScelto);
    }

    public void downgradeCarta(String pan){
        cartaDAO.downgradeCarta(pan);
        ImageIcon iconChecked = new ImageIcon(Controller.class.getResource("/IMG/checked.png"));
        JOptionPane.showMessageDialog(
                null,
                "La tua carta è stata aggiornata a carta di debito!",
                "Aggiornamento effettuato",
                JOptionPane.PLAIN_MESSAGE,
                iconChecked
        );
        frameHome(false);
        showHomePage(contoScelto);
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

    public void frameCard(Boolean isVisible){
        frameCard.setVisible(isVisible);
    }

    public void frameSalvadanaio(Boolean isVisible){
        frameSalvadanaio.setVisible(isVisible);
    }

}