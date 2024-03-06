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
    private SignUpViewGUI frameSignUp;
    private BankAccountPickViewGUI framePickBankAccount;
    private HomeViewGUI frameHome;
    private CardViewGUI frameCard;
    private PiggyBankViewGUI framePiggyBank;
    private TransactionViewGUI frameTransaction;
    private BankTransferViewGUI frameBankTransfer;
    private CollectionPickViewGUI framePickCollection;
    private CollectionViewGUI frameCollection;

    //Icone
    ImageIcon iconAlert = new ImageIcon(Controller.class.getResource("/IMG/alert.png"));
    ImageIcon iconCancel = new ImageIcon(Controller.class.getResource("/IMG/cancel.png"));
    ImageIcon iconChecked = new ImageIcon(Controller.class.getResource("/IMG/checked.png"));
    ImageIcon iconDelete = new ImageIcon(Controller.class.getResource("/IMG/delete.png"));

    //Dichiarazioni delle Dao
    private AccountDAO accountDao;
    private BankAccountDAO bankAccountDAO;
    private CardDAO cardDAO;
    private PiggyBankDAO piggyBankDAO;
    private TransactionDAO transactionDAO;
    private CollectionDAO collectionDAO;

    //Dichiarazione delle variabili
    private Account account = null;
    private ArrayList<BankAccount> bankAccounts = null;
    private BankAccount selectedBankAccount = null;
    private Card card = null;
    private ArrayList<PiggyBank> piggyBanks = null;
    private ArrayList<Transaction> transactions = null;
    private Double[] report = null;
    private String credentialIban = null;
    private ArrayList<Collection> collections = null;
    private Collection selectedCollection = null;
    private ArrayList<Transaction> transactionsCollection = null;

    public Controller() {
        frameLogin = new LoginViewGUI(this); //LoginView accetta ControllerLogin come parametro
        frameLogin(true);

        //DAO
        this.accountDao = new AccountDAOImpl();
        this.bankAccountDAO = new BankAccountDAOImpl();
        this.cardDAO = new CardDAOImpl();
        this.piggyBankDAO = new PiggyBankDAOImpl();
        this.transactionDAO = new TransactionDAOImpl();
        this.collectionDAO = new CollectionDAOImpl();
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
        frameSignUp = new SignUpViewGUI(this);
        frameSignIn(true);
    }

    public void showPickFrame(){
        framePickBankAccount = new BankAccountPickViewGUI(this);
        framePick(true);
    }



    public void insertAccount(String email, String password, String name, String surname){
        try{
            account = new Account(email, password, name, surname);
            if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !surname.isEmpty()) {
                accountDao.insertAccount(email, password, name, surname);
                JOptionPane.showMessageDialog(
                        frameSignUp,
                        "Dati dell'account inseriti!",
                        "Benvenuta/o",
                        JOptionPane.PLAIN_MESSAGE,
                        iconChecked);
            }
            else{
                JOptionPane.showMessageDialog(
                        frameSignUp,
                        "Inserisci delle credenziali valide!",
                        "Errore",
                        JOptionPane.PLAIN_MESSAGE,
                        iconAlert);
            }
        }
        catch (MyExc exc){
            JOptionPane.showMessageDialog(
                    frameSignUp,
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
    public ArrayList<BankAccount> selectBankAccountByAccount(Account account){
        bankAccounts = new ArrayList<BankAccount>();
        bankAccounts = bankAccountDAO.selectBankAccountByAccount(account);
        account.setBankAccounts(bankAccounts);
        return bankAccounts;
    }

    public void updateBankAccount(BankAccount conto){
        selectedBankAccount = bankAccountDAO.updateBankAccount(conto);
    }

    /**
     * Metodo che aggiunge un nuovo Conto Corrente. <br>Ritorna true in caso venga completato correttanente, false altrimenti.
     * @param email riferimeto per il conto da aggiungere.*/
    public Boolean insertBankAccount(String email){
        if (bankAccountDAO.insertBankAccount(email)){
            return true;
        }
        else
            return false;
    }

    /**
     * Metodo che elimina un determinato Conto Corrente.
     *@param iban riferimento per l'eliminazione del conto.*/
    public void deleteBankAccount(String iban){
        bankAccountDAO.deleteBankAccount(iban);
        frameHome(false);

        //Viene aggiornata la pagina con i conti corretti.
        try {
            checkCredentials(account.getEmail(), account.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(
                framePickBankAccount,
                "Conto Corrente con Iban: " +iban+ ", eliminato con successo!",
                "Conto Corrente eliminato",
                JOptionPane.PLAIN_MESSAGE,
                iconDelete
        );
    }

    /**
     * Metodo per gesitre la visualizzaione della pagina di Home page.
     * @param bankAccount riferimento per le informazioni da visualizzare in Home Page.*/
    public void showHomePage(BankAccount bankAccount){

        //Viene selezionato il conto dopo averlo scelto dalla pagina di selzione.
        selectedBankAccount = bankAccount;
        //Viene recuperata la carta associata al conto scelto.
        card = cardDAO.selectCard(selectedBankAccount);

        framePick(false);
        if(framePiggyBank != null)
            frameSalvadanaio(false);
        if(framePickCollection != null)
            framePickCollection(false);
        if(frameHome != null)
            frameHome(false);
        frameHome = new HomeViewGUI(this);
        frameHome(true);
    }

    /**
     * Metodo che permette di tornare alla pagina di Login.
     */
    public void backLoginPage(){
        //Quando si torna alla pagina di Login l'account viene settato a null.
        account = null;
        //Quando si torna alla pagina di Login il conto scelto viene settato a null.
        if (selectedBankAccount != null)
            selectedBankAccount = null;

        if(frameBankTransfer!=null)
            frameBankTransfer(false);
        if(frameCard!=null)
            frameCard(false);
        if(framePickBankAccount != null)
            framePick(false);
        if(frameHome != null)
            frameHome(false);
        if(frameSignUp != null)
            frameSignIn(false);

        frameLogin(true);
    }


    /**
     * Metodo che permette gestire la visualizzazione della pagina della carta.*/
    public void showCardPage(){
        if (frameCard != null) {
            frameCard(false);
            frameCard = new CardViewGUI(this);
            frameCard(true);
        }
        else {
            frameCard = new CardViewGUI(this);
            frameCard(true);
        }

    }




    /**
     * Metodo che permette di effettuare l'upgrade della carta da Debito (default) a Credito.
     * @param pan riferimento per la carta da aggiornare.*/
    public void upgradeCarta(String pan){
        if(selectedBankAccount.getBalance() >= 5) {
            cardDAO.upgradeCarta(pan);
            JOptionPane.showMessageDialog(
                    null,
                    "La tua carta è stata aggiornata a carta di credito!",
                    "Aggiornamento effettuato",
                    JOptionPane.PLAIN_MESSAGE,
                    iconChecked
            );
            frameHome(false);
            showHomePage(bankAccountDAO.updateBankAccount(selectedBankAccount));
        }
        else {
            JOptionPane.showMessageDialog(
                    frameHome,
                    "Saldo insufficiente per effettuare l'upgrade!",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE,
                    iconAlert
            );
        }
    }

    /**
     * Metodo che permette di effetuare il downgrade della carta Da credito a Debito.
     * @param pan riferimento per la carta da aggiornare.*/
    public void downgradeCarta(String pan){
        cardDAO.downgradeCarta(pan);
        JOptionPane.showMessageDialog(
                null,
                "La tua carta è stata aggiornata a carta di debito!",
                "Aggiornamento effettuato",
                JOptionPane.PLAIN_MESSAGE,
                iconChecked
        );
        frameHome(false);
        showHomePage(selectedBankAccount);
    }

    /**
     * Metodo che permette di gestire la visualizzazione della pagina dei salvadanai. */
    public void showSalvadanaioPage(){
        if (framePiggyBank != null)
            frameSalvadanaio(false);
        //Vengono recuperati i salvadanai associati al conto scelto.
        piggyBanks = piggyBankDAO.selectSalvadanaio(selectedBankAccount);
        selectedBankAccount.setPiggyBanks(piggyBanks);
        if(frameBankTransfer!=null){
            frameBankTransfer(false);
        }
        if(frameCard!=null){
            frameCard(false);
        }
        framePiggyBank = new PiggyBankViewGUI(this);
        frameHome(false);
        frameSalvadanaio(true);
    }

    public void addPiggyBank(String name, double target, String description) throws MyExc{
        try {
            if(!name.isEmpty() && !description.isEmpty())
                piggyBankDAO.addPiggyBank(selectedBankAccount, name, target, description);
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
                    framePiggyBank,
                    e.getMessage(),
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE,
                    iconCancel
            );
        }

    }

    public void deletePiggyBank(String name){
        piggyBankDAO.deletePiggyBank(selectedBankAccount, name);
    }

    public void fillPiggyBank(String name, String moneyToSend){
        try{
            if(!moneyToSend.isEmpty()) {
                if(Math.round((Double.parseDouble(moneyToSend)*100.00)/100.00) > 0) {
                    if (selectedBankAccount.getBalance() >= Math.round((Double.parseDouble(moneyToSend) * 100.00) / 100.00)) {
                        piggyBankDAO.fillPiggyBank(selectedBankAccount, name, Math.round((Double.parseDouble(moneyToSend) * 100.00) / 100.00));
                    } else {
                        JOptionPane.showMessageDialog(
                                framePiggyBank,
                                "Saldo conto corrente insufficiente!",
                                "Errore",
                                JOptionPane.PLAIN_MESSAGE,
                                iconCancel

                        );
                    }
                }
                else {
                    JOptionPane.showMessageDialog(
                            framePiggyBank,
                            "Inserisci una cifra valida!",
                            "Errore inserimento",
                            JOptionPane.PLAIN_MESSAGE,
                            iconAlert
                    );
                }
            }
            else {
                JOptionPane.showMessageDialog(
                        framePiggyBank,
                        "Riempi tutti i campi!",
                        "Errore inserimento",
                        JOptionPane.PLAIN_MESSAGE,
                        iconAlert
                );
            }
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(
                    framePiggyBank,
                    "Inserisci una cifra valida!",
                    "Errore inserimento",
                    JOptionPane.ERROR_MESSAGE,
                    iconAlert
            );
        }
    }

    public void getMoneyByPiggyBank(String balancePiggyBank, String name, String moneyToGet){
        try{
            if(!moneyToGet.isEmpty()) {
                if (Double.parseDouble(balancePiggyBank) >= Math.round((Double.parseDouble(moneyToGet)*100.00)/100.00)) {
                    piggyBankDAO.getMoneyByPiggyBank(selectedBankAccount, name, Math.round((Double.parseDouble(moneyToGet)*100.00)/100.00));
                } else {
                    JOptionPane.showMessageDialog(
                            framePiggyBank,
                            "Saldo salvadanaio insufficiente!",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            else {
                JOptionPane.showMessageDialog(
                        framePiggyBank,
                        "Inserisci una cifra valida!",
                        "Errore inserimento",
                        JOptionPane.PLAIN_MESSAGE,
                        iconAlert
                );
            }
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(
                    framePiggyBank,
                    "Inserisci una cifra valida",
                    "Errore inserimento",
                    JOptionPane.PLAIN_MESSAGE,
                    iconAlert
            );
        }
    }


    public void showTransazioniPage(){
        if (frameTransaction != null)
            frameTransazioni(false);
        //Vengono recuperati le transazioni associati al conto scelto.
        transactions = transactionDAO.selectTransazioniByIban(selectedBankAccount);
        selectedBankAccount.setTransactions(transactions);
        if(frameBankTransfer!=null){
            frameBankTransfer(false);
        }
        if(frameCard!=null){
            frameCard(false);
        }
        frameTransaction = new TransactionViewGUI(this);
        frameHome(false);
        frameTransazioni(true);
    }

    public void showBankTransferPage(){
        if(frameBankTransfer != null){
            frameBankTransfer(false);
        }
        frameBankTransfer = new BankTransferViewGUI(this);
        frameBankTransfer(true);
    }

    public void sendBankTransfer(String ibanReceiver, String amount, String name, String surname, String causal, String category, String typeBankTransfer, String nameCollection){
        try{
            if(typeBankTransfer.equals("Bonifico")){
                if(!amount.isEmpty()) {
                    if((getCarta().getTypeCard().equals("CartaDiDebito") && Double.parseDouble(amount)<=3000) || (getCarta().getTypeCard().equals("CartaDiCredito"))) {
                        if (!selectedBankAccount.getIban().equals(ibanReceiver)) {
                            if (selectedBankAccount.getBalance() >= Math.round((Double.parseDouble(amount) * 100.00) / 100.00)) {
                                if (!ibanReceiver.isEmpty() && !name.isEmpty() && !surname.isEmpty() && !causal.isEmpty()) {
                                    if (transactionDAO.checkIban(ibanReceiver, name, surname)) {
                                        if (nameCollection == null)
                                            nameCollection = "ALTRO";
                                        transactionDAO.sendBankTransfer(selectedBankAccount, ibanReceiver, amount, causal, category, nameCollection);
                                        JOptionPane.showMessageDialog(
                                                frameBankTransfer,
                                                "Bonifico inviato con successo!",
                                                "",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                        frameBankTransfer(false);
                                        showHomePage(bankAccountDAO.updateBankAccount(selectedBankAccount));

                                    }
                                } else {
                                    JOptionPane.showMessageDialog(
                                            frameBankTransfer,
                                            "Riempi tutti i campi.",
                                            "Errore",
                                            JOptionPane.PLAIN_MESSAGE,
                                            iconAlert
                                    );
                                }
                            } else {
                                JOptionPane.showMessageDialog(
                                        frameBankTransfer,
                                        "Saldo conto corrente insufficiente",
                                        "Errore",
                                        JOptionPane.PLAIN_MESSAGE,
                                        iconAlert
                                );
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    frameBankTransfer,
                                    "Non puoi inserire il tuo stesso Iban.",
                                    "Errore",
                                    JOptionPane.PLAIN_MESSAGE,
                                    iconAlert
                            );
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                frameBankTransfer,
                                "<html>L'importo supera il limite di soldi che è possibile inviare con una sola transazione.<br>Se desideri puoi effettuare l'upgrade della carta!</html>",
                                "Attenzione",
                                JOptionPane.PLAIN_MESSAGE,
                                iconAlert
                        );
                    }
                }
                else {
                    JOptionPane.showMessageDialog(
                            frameBankTransfer,
                            "Riempi tutti i campi.",
                            "Errore",
                            JOptionPane.PLAIN_MESSAGE,
                            iconAlert
                    );
                }

            }
            else {
                if(!amount.isEmpty()) {
                    if((getCarta().getTypeCard().equals("CartaDiDebito") && Double.parseDouble(amount)<=3000) || (getCarta().getTypeCard().equals("CartaDiCredito"))) {
                        if (!selectedBankAccount.getIban().equals(ibanReceiver)) {
                            if (selectedBankAccount.getBalance() >= Math.round((Double.parseDouble(amount) * 100.00) / 100.00)) {
                                if (!ibanReceiver.isEmpty() && !name.isEmpty() && !surname.isEmpty() && !causal.isEmpty()) {
                                    if (transactionDAO.checkIban(ibanReceiver, name, surname)) {
                                        if (nameCollection == null)
                                            nameCollection = "ALTRO";
                                        transactionDAO.sendIstantBankTransfer(selectedBankAccount, ibanReceiver, amount, causal, category, nameCollection);
                                        JOptionPane.showMessageDialog(
                                                frameBankTransfer,
                                                "Bonifico inviato con successo!",
                                                "",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                        frameBankTransfer(false);
                                        showHomePage(bankAccountDAO.updateBankAccount(selectedBankAccount));
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(
                                            frameBankTransfer,
                                            "Riempi tutti i campi.",
                                            "Errore",
                                            JOptionPane.PLAIN_MESSAGE,
                                            iconAlert
                                    );
                                }
                            } else {
                                JOptionPane.showMessageDialog(
                                        frameBankTransfer,
                                        "Saldo conto corrente insufficiente",
                                        "Errore",
                                        JOptionPane.PLAIN_MESSAGE,
                                        iconAlert
                                );
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    frameBankTransfer,
                                    "Non puoi inserire il tuo stesso Iban.",
                                    "Errore",
                                    JOptionPane.PLAIN_MESSAGE,
                                    iconAlert
                            );
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                frameBankTransfer,
                                "<html>L'importo supera il limite di soldi che è possibile inviare con una sola transazione.<br>Se desideri puoi effettuare l'upgrade della carta!</html>",
                                "Attenzione",
                                JOptionPane.PLAIN_MESSAGE,
                                iconAlert
                        );
                    }
                }
                else {
                    JOptionPane.showMessageDialog(
                            frameBankTransfer,
                            "Riempi tutti i campi.",
                            "Errore",
                            JOptionPane.PLAIN_MESSAGE,
                            iconAlert
                    );
                }
            }
        }
        catch (MyExc e){
            JOptionPane.showMessageDialog(
                    frameBankTransfer,
                    e.getMessage(),
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE,
                    iconAlert
            );
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(
                    frameBankTransfer,
                    "Inserisci una cifra valida",
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE,
                    iconAlert
            );
        }
    }

    public void selectNameAndSurnameByIban(String iban){
        credentialIban = transactionDAO.selectNameAndSurnameByIban(iban);
    }

    public void showCollectionPickView(){
        collections = collectionDAO.selectCollectionByIban(selectedBankAccount);

        if(framePickCollection!=null){
            framePickCollection(false);
        }

        framePickCollection = new CollectionPickViewGUI(this);

        frameHome(false);
        if(frameBankTransfer!=null){
            frameBankTransfer(false);
        }
        if(frameCard!=null){
            frameCard(false);
        }

        framePickCollection(true);
    }

    public void pickCollectionByIban(){
        collections = collectionDAO.selectCollectionByIban(selectedBankAccount);
    }

    public double selectSumOfCollections(String name){
        return transactionDAO.selectSumOfCollections(selectedBankAccount, name);
    }

    public void showCollectionPage(Collection collection){
        selectedCollection = collection;
        transactionsCollection = transactionDAO.selectTransactionsByCollection(selectedCollection, selectedBankAccount);


        selectedCollection.setTransactions(transactionsCollection);


        framePickCollection(false);
        frameCollection = new CollectionViewGUI(this);
        frameCollection(true);

    }

    public void addCollection(BankAccount bankAccount, String name, String description) throws MyExc{
        try {
            if(!name.isEmpty() && !description.isEmpty())
                collectionDAO.addCollection(selectedBankAccount, name, description);
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
                    framePiggyBank,
                    e.getMessage(),
                    "Errore",
                    JOptionPane.PLAIN_MESSAGE,
                    iconCancel
            );
        }
    }

    public void deleteCollection(String name){
        collectionDAO.deleteCollection(selectedBankAccount, name);
        if(framePickCollection!=null)
            framePickCollection(false);
        showCollectionPickView();
    }

    public void viewReport(BankAccount bankAccount, String month){
        report = transactionDAO.viewReport(selectedBankAccount, month);

    }

    public double totaleInviatoMensile(BankAccount bankAccount, String month){
        return transactionDAO.totaleInviatoMensile(bankAccount, month);
    }

    public double totaleRicevutoMensile(BankAccount bankAccount, String month){
        return transactionDAO.totaleRicevutoMensile(bankAccount, month);
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
        frameSignUp.setVisible(isVisibile);
    }

    /**
     * Metodo che gestisce la visibilità della pagina di scelata del conto.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void framePick(Boolean isVisibile){
        framePickBankAccount.setVisible(isVisibile);
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
        framePiggyBank.setVisible(isVisible);
    }

    public void frameTransazioni(Boolean isVisibile){
        frameTransaction.setVisible(isVisibile);
    }

    /**
     * Metodo che gestisce la visibilità della pagina per visualizzare le collezioni.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void framePickCollection(Boolean isVisible){
        framePickCollection.setVisible(isVisible);
    }

    /**
     * Metodo che gestisce la visibilità della pagina per visualizzare le collezioni.
     * @param isVisibile setta la visibilità della pagina
     * */
    public void frameCollection(Boolean isVisible){
        frameCollection.setVisible(isVisible);
    }









    // Getter & Setter

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(ArrayList<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public BankAccount getSelectedBankAccount() {
         return selectedBankAccount;
    }

    public void setSelectedBankAccount(BankAccount selectedBankAccount) {
        this.selectedBankAccount = selectedBankAccount;
    }

    public Card getCarta() {
        return card;
    }

    public void setCarta(Card card) {
        this.card = card;
    }

    public ArrayList<PiggyBank> getPiggyBanks() {
        return piggyBanks;
    }

    public void setPiggyBanks(ArrayList<PiggyBank> piggyBanks) {
        this.piggyBanks = piggyBanks;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Double[] getReport() {
        return report;
    }

    public void setReport(Double[] report) {
        this.report = report;
    }

    public String getCredentialIban() {
        return credentialIban;
    }

    public void setCredentialIban(String credentialIban) {
        this.credentialIban = credentialIban;
    }

    public ArrayList<Collection> getCollections() {
        return collections;
    }

    public void setCollections(ArrayList<Collection> collections) {
        this.collections = collections;
    }

    public ArrayList<Transaction> getTransactionsCollection() {
        return transactionsCollection;
    }

    public void setTransactionsCollection(ArrayList<Transaction> transactionsCollection) {
        this.transactionsCollection = transactionsCollection;
    }

    public Collection getSelectedCollection() {
        return selectedCollection;
    }

    public void setSelectedCollection(Collection selectedCollection) {
        this.selectedCollection = selectedCollection;
    }
}