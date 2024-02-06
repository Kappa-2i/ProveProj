package ENTITY;

public class ContoCorrente {
    private String iban;
    private String dataApertura;
    private double saldo;
    private Account account;

    public ContoCorrente(String iban, String dataApertura, double saldo, Account account) {
        setIban(iban);
        setDataApertura(dataApertura);
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

    public String getDataApertura() {
        return dataApertura;
    }

    public void setDataApertura(String dataApertura) {
        this.dataApertura = dataApertura;
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
                ", dataApertura='" + dataApertura + '\'' +
                ", saldo=" + saldo +
                ", account=" + account +
                '}';
    }
}
