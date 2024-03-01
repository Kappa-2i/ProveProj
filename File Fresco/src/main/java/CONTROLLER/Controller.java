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
    private TransazioniGUI frameTransazioni;
    private BankTransferPageGUI frameBankTransfer;

    //Icone
    ImageIcon iconAlert = new ImageIcon(HomePageGUI.class.getResource("/IMG/alert.png"));
    ImageIcon iconCancel = new ImageIcon(HomePageGUI.class.getResource("/IMG/cancel.png"));
    ImageIcon iconChecked = new ImageIcon(HomePageGUI.class.getResource("/IMG/checked.png"));
    ImageIcon iconDelete = new ImageIcon(HomePageGUI.class.getResource("/IMG/cancel.png"));

    //Dichiarazioni delle Dao
    private AccountDAO accountDao;
    private ContoCorrenteDAO contoCorrenteDAO;
    private CartaDAO cartaDAO;
    private SalvadanaioDAO salvadanaioDAO;
    private TransazioneDAO transazioneDAO;

    //Dichiarazione delle variabili
    private Account account = null;
    private ArrayList<ContoCorrente> conti = null;
    private ContoCorrente contoScelto = null;
    private Carta carta = null;
    private ArrayList<Salvadanaio> salvadanai = null;
    private ArrayList<Transazione> transazioni = null;
    private Double[] report = null;
    private String credenzialiIbanMittDest = null;

    public Controller() {
        frameLogin = new LoginViewGUI(this); //LoginView accetta ControllerLogin come parametro
        frameLogin(true);

        //DAO
        this.accountDao = new AccountDAOImpl();
        this.contoCorrenteDAO = new ContoCorrenteDAOImpl();
        this.cartaDAO = new CartaDAOImpl();
        this.salvadanaioDAO = new SalvadanaioDAOImpl();
        this.transazioneDAO = new TransazionaDAOImpl();
    }

    /**
     * Metodo che controlla la validità dei campi email e password inseriti al momento del login.
     * <br> In caso di successo viene visulalizzata la pagina Home, bisogna riporovare altrimenti.
     * @param email per controllare che l'email sia corretta.
     * @param password per controllare che la password sia corretta.
     * */
    public void checkCredentials(String email, String password) throws SQLException {
        if((!email.isEmpty()) && (!password.isEmpty())){
            account = accountDao.checkCredentials(email.toLowerCase(), password);
            if (account != null){
                frameLogin(false);
                showPickFrame();
            }
            else{
                //Se uno dei due campi è sbagliato viene visualizzato un messaggio di errore.
                JOptionPane.showMessageDialog(
                        null,
                        "Email o Password Errati",
                        "Errore di Login",
                        JOptionPane.PLAIN_MESSAGE,
                        iconCancel
                );
            }
        }
        else {
            //Se i campi non vengono compilati viene visualizzato un messaggio di errore.
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci delle credenziali valide!",
                    "Errore di Login",
                    JOptionPane.PLAIN_MESSAGE,
                    iconAlert);
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



    public void insertAccount(String email, String password, String name, String surname){
        try{
            account = new Account(email, password, name, surname);
            if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !surname.isEmpty()) {
                accountDao.insertAccount(email, password, name, surname);
                JOptionPane.showMessageDialog(
                        frameSignIn,
                        "Dati dell'account inseriti!",
                        "Benvenuta/o",
                        JOptionPane.PLAIN_MESSAGE,
                        iconChecked);
            }
            else{
                JOptionPane.showMessageDialog(
                        frameSignIn,
                        "Inserisci delle credenziali valide!",
                        "Errore",
                        JOptionPane.PLAIN_MESSAGE,
                        iconAlert);
            }
        }
        catch (MyExc exc){
            JOptionPane.showMessageDialog(
                    frameSignIn,
                    "L'email deve contenere una '@'!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE,
                    iconAlert
                    );
        }


    }

    public boolean confirmedPassword(String password, String confirmedPassword){
        if (password.equals(confirmedPassword))
            return true;
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
                JOptionPane.PLAIN_MESSAGE,
                iconDelete
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


        if(framePick != null)
            framePick(false);
        if(frameHome != null)
            frameHome(false);
        if(frameSignIn != null)
            frameSignIn(false);

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
        if (frameCard==null) {
            frameCard = new CardPageGUI(this);
            frameCard(true);
        }
    }




    /**
     * Metodo che permette di effettuare l'upgrade della carta da Debito (default) a Credito.
     * @param pan riferimento per la carta da aggiornare.*/
    public void upgradeCarta(String pan){
        cartaDAO.upgradeCarta(pan);
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

    public void addPiggyBank(String nome, double obiettivo, String descrizione) throws MyExc{
        try {
            if(!nome.isEmpty() && !descrizione.isEmpty())
                salvadanaioDAO.addPiggyBank(contoScelto, nome, obiettivo, descrizione);
            else{
                JOptionPane.showMessageDialog(
                        null,
                        "Riempi tutti i campi!",
                        "Errore inserimento",
                        JOptionPane.ERROR_MESSAGE,
                        iconAlert
                );
            }
        } catch (MyExc e) {
            JOptionPane.showMessageDialog(
                    frameSalvadanaio,
                    e.getMessage(),
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE,
                    iconCancel
            );
        }

    }

    public void deletePiggyBank(String nome){
        salvadanaioDAO.deletePiggyBank(contoScelto, nome);
    }

    public void fillPiggyBank(String nome, String soldiDaInviare){
        try{
            if(!soldiDaInviare.isEmpty()) {
                if(Math.round((Double.parseDouble(soldiDaInviare)*100.00)/100.00) > 0) {
                    if (contoScelto.getSaldo() >= Math.round((Double.parseDouble(soldiDaInviare) * 100.00) / 100.00)) {
                        salvadanaioDAO.fillPiggyBank(contoScelto, nome, Math.round((Double.parseDouble(soldiDaInviare) * 100.00) / 100.00));
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Saldo conto corrente insufficiente!",
                                "Errore",
                                JOptionPane.PLAIN_MESSAGE,
                                iconCancel

                        );
                    }
                }
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Inserisci una cifra valida!",
                            "Errore inserimento",
                            JOptionPane.PLAIN_MESSAGE,
                            iconAlert
                    );
                }
            }
            else {
                JOptionPane.showMessageDialog(
                        null,
                        "Riempi tutti i campi!",
                        "Errore inserimento",
                        JOptionPane.PLAIN_MESSAGE,
                        iconAlert
                );
            }
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci una cifra valida!",
                    "Errore inserimento",
                    JOptionPane.ERROR_MESSAGE,
                    iconAlert
            );
        }
    }

    public void getMoneyByPiggyBank(String saldoSalvadanaio, String nome, String soldiDaPrelevare){
        if(!soldiDaPrelevare.isEmpty()) {
            if (Double.parseDouble(saldoSalvadanaio) >= Math.round((Double.parseDouble(soldiDaPrelevare)*100.00)/100.00)) {
                salvadanaioDAO.getMoneyByPiggyBank(contoScelto, nome, Math.round((Double.parseDouble(soldiDaPrelevare)*100.00)/100.00));
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Saldo salvadanaio insufficiente!",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        else {
            JOptionPane.showMessageDialog(
                    null,
                    "Inserisci una cifra valida!",
                    "Errore inserimento",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public void showTransazioniPage(){
        if (frameTransazioni != null)
            frameTransazioni(false);
        //Vengono recuperati le transazioni associati al conto scelto.
        transazioni = transazioneDAO.selectTransazioniByIban(contoScelto);
        contoScelto.setTransazioni(transazioni);

        frameTransazioni = new TransazioniGUI(this);
        frameHome(false);
        frameTransazioni(true);
    }

    public void showBankTransferPage(){
        frameBankTransfer = new BankTransferPageGUI(this);
        frameBankTransfer(true);
    }

    public void sendBankTransfer(String ibanReceiver, String amount, String name, String surname, String reason, String cat, String typeBankTransfer){
        try{
            if(typeBankTransfer.equals("Bonifico")){
                if(!amount.isEmpty()) {
                    if (!contoScelto.getIban().equals(ibanReceiver)) {
                        if (contoScelto.getSaldo() >= Math.round((Double.parseDouble(amount) * 100.00) / 100.00)) {
                            if (!ibanReceiver.isEmpty() && !name.isEmpty() && !surname.isEmpty() && !reason.isEmpty()) {
                                if (transazioneDAO.checkIban(ibanReceiver, name, surname)) {
                                    transazioneDAO.sendBankTransfer(contoScelto, ibanReceiver, amount, reason, cat);
                                    JOptionPane.showMessageDialog(
                                            frameBankTransfer,
                                            "Bonifico inviato con successo!",
                                            "",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                }
                            } else {
                                JOptionPane.showMessageDialog(
                                        frameBankTransfer,
                                        "Riempi tutti i campi.",
                                        "Errore",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    frameBankTransfer,
                                    "Saldo conto corrente insufficiente",
                                    "Errore",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                frameBankTransfer,
                                "Non puoi inserire il tuo stesso Iban.",
                                "Errore",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
                else {
                    JOptionPane.showMessageDialog(
                            frameBankTransfer,
                            "Riempi tutti i campi.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            else {
                if(!amount.isEmpty()) {
                    if (!contoScelto.getIban().equals(ibanReceiver)) {
                        if (contoScelto.getSaldo() >= Math.round((Double.parseDouble(amount) * 100.00) / 100.00)) {
                            if (!ibanReceiver.isEmpty() && !name.isEmpty() && !surname.isEmpty() && !reason.isEmpty()) {
                                if (transazioneDAO.checkIban(ibanReceiver, name, surname)) {
                                    transazioneDAO.sendIstantBankTransfer(contoScelto, ibanReceiver, amount, reason, cat);
                                    JOptionPane.showMessageDialog(
                                            frameBankTransfer,
                                            "Bonifico inviato con successo!",
                                            "",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                }
                            } else {
                                JOptionPane.showMessageDialog(
                                        frameBankTransfer,
                                        "Riempi tutti i campi.",
                                        "Errore",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    frameBankTransfer,
                                    "Saldo conto corrente insufficiente",
                                    "Errore",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                frameBankTransfer,
                                "Non puoi inserire il tuo stesso Iban.",
                                "Errore",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
                else {
                    JOptionPane.showMessageDialog(
                            frameBankTransfer,
                            "Riempi tutti i campi.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
        catch (MyExc e){
            JOptionPane.showMessageDialog(
                    frameBankTransfer,
                    e.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(
                    frameBankTransfer,
                    "Inserisci una cifra valida",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void selectNameAndSurnameByIban(String iban){
        credenzialiIbanMittDest = transazioneDAO.selectNameAndSurnameByIban(iban);
    }

    public void viewReport(ContoCorrente conto, String mese){
        report = transazioneDAO.viewReport(contoScelto, mese);

    }

    public double totaleInviatoMensile(ContoCorrente conto, String mese){
        return transazioneDAO.totaleInviatoMensile(conto, mese);
    }

    public double totaleRicevutoMensile(ContoCorrente conto, String mese){
        return transazioneDAO.totaleRicevutoMensile(conto, mese);
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
     * Metodo che gestisce la visibilità della pagina per visualizzare la carta.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void frameBankTransfer(Boolean isVisible){
        frameBankTransfer.setVisible(isVisible);
    }

    /**
     * Metodo che gestisce la visibilità della pagina per visualizzare i salvadanai.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void frameSalvadanaio(Boolean isVisible){
        frameSalvadanaio.setVisible(isVisible);
    }

    public void frameTransazioni(Boolean isVisibile){
        frameTransazioni.setVisible(isVisibile);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<ContoCorrente> getConti() {
        return conti;
    }

    public void setConti(ArrayList<ContoCorrente> conti) {
        this.conti = conti;
    }

    public ContoCorrente getContoScelto() {
        return contoScelto;
    }

    public void setContoScelto(ContoCorrente contoScelto) {
        this.contoScelto = contoScelto;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public ArrayList<Salvadanaio> getSalvadanai() {
        return salvadanai;
    }

    public void setSalvadanai(ArrayList<Salvadanaio> salvadanai) {
        this.salvadanai = salvadanai;
    }

    public ArrayList<Transazione> getTransazioni() {
        return transazioni;
    }

    public void setTransazioni(ArrayList<Transazione> transazioni) {
        this.transazioni = transazioni;
    }

    public Double[] getReport() {
        return report;
    }

    public void setReport(Double[] report) {
        this.report = report;
    }

    public String getCredenzialiIbanMittDest() {
        return credenzialiIbanMittDest;
    }

    public void setCredenzialiIbanMittDest(String credenzialiIbanMittDest) {
        this.credenzialiIbanMittDest = credenzialiIbanMittDest;
    }
}