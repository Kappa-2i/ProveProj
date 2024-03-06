package ENTITY;

public class Transaction {
    private double amount;
    private String causal;
    private String dateTransaction;
    private String timeTransaction;
    private String typeTransaction;
    private String iban; //iban di chi ci manda soldi, o a chi inviamo soldi
    private String entryCategory;
    private String exitCategory;
    private String nameCollection;
    private BankAccount bankAccount;

    public Transaction(double amount, String causal, String dateTransaction, String timeTransaction, String typeTransaction, String iban, String entryCategory, String exitCategory, String nameCollection, BankAccount bankAccount) {
        setAmount(amount);
        setCausal(causal);
        setDateTransaction(dateTransaction);
        setTimeTransaction(timeTransaction);
        setTypeTransaction(typeTransaction);
        setIban(iban);
        setEntryCategory(entryCategory);
        setExitCategory(exitCategory);
        setNameCollection(nameCollection);
        setContoCorrente(bankAccount);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCausal() {
        return causal;
    }

    public void setCausal(String causal) {
        this.causal = causal;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getTimeTransaction() {
        return timeTransaction;
    }

    public void setTimeTransaction(String timeTransaction) {
        this.timeTransaction = timeTransaction;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BankAccount getContoCorrente() {
        return bankAccount;
    }

    public void setContoCorrente(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getEntryCategory() {
        return entryCategory;
    }

    public void setEntryCategory(String entryCategory) {
        this.entryCategory = entryCategory;
    }

    public String getExitCategory() {
        return exitCategory;
    }

    public void setExitCategory(String exitCategory) {
        this.exitCategory = exitCategory;
    }

    public String getNameCollection() {
        return nameCollection;
    }

    public void setNameCollection(String nameCollection) {
        this.nameCollection = nameCollection;
    }
}
