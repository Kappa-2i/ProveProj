package ENTITY;

public class ContoCorrente {
    private String iban;
    private double saldo;
    private Account account;

    public ContoCorrente(String iban, double saldo, Account account) {
        setIban(iban);
        setSaldo(saldo);
        setAccount(account);
    }

    public ContoCorrente(String iban, double saldo) {
        setIban(iban);
        setSaldo(saldo);
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "ContoCorrente{" +
                "iban='" + iban + '\'' +
                ", saldo=" + saldo +
                ", account=" + account +
                '}';
    }
}
