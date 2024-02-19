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

    //Dichiarazione delle variabili
    public Account account = null;
    public Persona persona = null;
    public ArrayList<ContoCorrente> conti = null;
    public ContoCorrente contoScelto = null;
    public Carta carta = null;
    public ArrayList<Salvadanaio> salvadanai = null;

    public Controller() {
        frameLogin = new LoginViewGUI(this); //LoginView accetta ControllerLogin come parametro
        frameLogin(true);

        //DAO
        this.accountDao = new AccountDAOImpl();
        this.personaDao = new PersonaDAOImpl();
        this.contoCorrenteDAO = new ContoCorrenteDAOImpl();
        this.cartaDAO = new CartaDAOImpl();
        this.salvadanaioDAO = new SalvadanaioDAOImpl();
    }

    /**
     * Metodo che controlla la validità dei campi email e password inseriti al momento del login. <br> In caso di successo viene visulalizzata la pagina Home, bisogna riporovare altrimenti.
     * @param email per controllare che l'email sia corretta.
     * @param password per controllare che la password sia corretta.
     * */
    public void checkCredentials(String email, String password) throws SQLException {
        if((!email.isEmpty()) && (!password.isEmpty())){
            account = accountDao.checkCredentials(email.toLowerCase(), password);
            if (account != null){
                persona = personaDao.selectPersonaFromEmail(email.toLowerCase());
                account.setPersona(persona);
                frameLogin(false);
                showPickFrame();
            }
            else{
                //Se uno dei due campi è sbagliato viene visualizzato un messaggio di errore.
                JOptionPane.showMessageDialog(
                        null,
                        "Email o Password Errati",
                        "Errore di Login",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        else {
            //Se i campi non vengono compilati viene visualizzato un messaggio di errore.
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci delle credenziali valide!",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *Metodo che permette di gestire la viusalizzazione della pagina di SignIn.*/
    public void showFrameSignIn(){
        frameLogin(false);
        frameSignIn = new SignInViewGUI(this);
        frameSignIn(true);
    }

    public void showPickFrame(){
        framePick = new BankAccountPickViewGUI(this);
        framePick(true);
    }

    /**
     * Metodo che permette di gestire la creazione di un una nuovo Utente, aggiornando il database.*/
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

    /**
     * Metodo che seleziona tutti i conti relativi all'account che gli viene passato
     * @param account riferimento per i conti da selzionare
     * */
    public ArrayList<ContoCorrente> selectBankAccountByAccount(Account account){
        conti = new ArrayList<ContoCorrente>();
        conti = contoCorrenteDAO.selectBankAccountByAccount(account);
        account.setConti(conti);
        return conti;
    }

    public void updateBankAccount(ContoCorrente conto){
        contoScelto = contoCorrenteDAO.updateBankAccount(conto);
        contoScelto.setAccount(account);
        contoScelto.setSalvadanai(salvadanai);
    }

    /**
     * Metodo che aggiunge un nuovo Conto Corrente. <br>Ritorna true in caso venga completato correttanente, false altrimenti.
     * @param email riferimeto per il conto da aggiungere.*/
    public Boolean insertBankAccount(String email){
        if (contoCorrenteDAO.insertBankAccount(email)){
            return true;
        }
        else
            return false;
    }

    /**
     * Metodo che elimina un determinato Conto Corrente.
     *@param iban riferimento per l'eliminazione del conto.*/
    public void deleteBankAccount(String iban){
        contoCorrenteDAO.deleteBankAccount(iban);
        frameHome(false);

        //Viene aggiornata la pagina con i conti corretti.
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

    /**
     * Metodo per gesitre la visualizzaione della pagina di Home page.
     * @param conto riferimento per le informazioni da visualizzare in Home Page.*/
    public void showHomePage(ContoCorrente conto){

        //Viene selezionato il conto dopo averlo scelto dalla pagina di selzione.
        contoScelto = conto;
        //Viene recuperata la carta associata al conto scelto.
        carta = cartaDAO.selectCard(contoScelto);

        //System.out.println(contoScelto.toString());
        framePick(false);
        if(frameSalvadanaio != null)
            frameSalvadanaio(false);
        frameHome = new HomePageGUI(this);
        frameHome(true);
    }

    /**
     * Metodo che permette di tornare alla pagina di Login.
     */
    public void backLoginPage(){
        //Quando si torna alla pagina di Login l'account viene settato a null.
        account = null;
        //Quando si torna alla pagina di Login il conto scelto viene settato a null.
        if (contoScelto!= null)
            contoScelto = null;

        framePick(false);
        frameHome(false);
        frameLogin(true);
    }
    /**
     * Metodo che permette di tornare alla pagina di selezione del Conto Corrente.*/
    public void backFramePick(){
        contoScelto = null;
        frameHome(false);
        framePick(true);
    }

    /**
     * Metodo che permette gestire la visualizzazione della pagina della carta.*/
    public void showCardPage(){
        frameCard = new CardPageGUI(this);
        frameCard(true);
    }




    /**
     * Metodo che permette di effettuare l'upgrade della carta da Debito (default) a Credito.
     * @param pan riferimento per la carta da aggiornare.*/
    public void upgradeCarta(String pan){
        cartaDAO.upgradeCarta(pan);
        ImageIcon iconChecked = new ImageIcon(Controller.class.getResource("/IMG/checked.png")); //Inserisce l'immagine sul JOptionPane.
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

    /**
     * Metodo che permette di effetuare il downgrade della carta Da credito a Debito.
     * @param pan riferimento per la carta da aggiornare.*/
    public void downgradeCarta(String pan){
        cartaDAO.downgradeCarta(pan);
        ImageIcon iconChecked = new ImageIcon(Controller.class.getResource("/IMG/checked.png")); //Inserisce l'immagine sul JOptionPane.
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

    /**
     * Metodo che permette di gestire la visualizzazione della pagina dei salvadanai. */
    public void showSalvadanaioPage(){
        if (frameSalvadanaio != null)
            frameSalvadanaio(false);
        //Vengono recuperati i salvadanai associati al conto scelto.
        salvadanai = salvadanaioDAO.selectSalvadanaio(contoScelto);
        contoScelto.setSalvadanai(salvadanai);
        frameSalvadanaio = new SalvadanaioGUI(this);
        frameHome(false);
        frameSalvadanaio(true);
    }

    public void addPiggyBank(String nome, double obiettivo, String descrizione) {
        salvadanaioDAO.addPiggyBank(contoScelto, nome, obiettivo, descrizione);
    }

    public void deletePiggyBank(String nome){
        salvadanaioDAO.deletePiggyBank(contoScelto, nome);
    }

    public void fillPiggyBank(String nome, double soldi){

        salvadanaioDAO.fillPiggyBank(contoScelto, nome, soldi);
    }

    public void getMoneyByPiggyBank(String nome, double soldi){
        salvadanaioDAO.getMoneyByPiggyBank(contoScelto, nome, soldi);
    }

    /**
     * Metodo che gestisce la visibilità della pagina di Login.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void frameLogin(Boolean isVisibile){
        frameLogin.setVisible(isVisibile);
    }

    /**
     * Metodo che gestisce la visibilità della pagina di SignIn.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void frameSignIn(Boolean isVisibile){
        frameSignIn.setVisible(isVisibile);
    }

    /**
     * Metodo che gestisce la visibilità della pagina di scelata del conto.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void framePick(Boolean isVisibile){
        framePick.setVisible(isVisibile);
    }

    /**
     * Metodo che gestisce la visibilità della pagina Home.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void frameHome(Boolean isVisible){
        frameHome.setVisible(isVisible);
    }

    /**
     * Metodo che gestisce la visibilità della pagina per visualizzare la carta.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void frameCard(Boolean isVisible){
        frameCard.setVisible(isVisible);
    }

    /**
     * Metodo che gestisce la visibilità della pagina per visualizzare i salvadanai.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void frameSalvadanaio(Boolean isVisible){
        frameSalvadanaio.setVisible(isVisible);
    }

}