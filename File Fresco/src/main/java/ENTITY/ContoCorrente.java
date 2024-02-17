package ENTITY;

import java.util.ArrayList;

// Classe del modello che rappresenta il conto corrente

public class ContoCorrente {

    //Attributi
    private String iban;
    private double saldo;
    private Account account;
    private ArrayList<Salvadanaio> salvadanai;

    //Costruttori
    public ContoCorrente(String iban, double saldo, Account account, ArrayList<Salvadanaio> salvadanai) {
        setIban(iban);
        setSaldo(saldo);
        setAccount(account);
        setSalvadanai(salvadanai);
    }

    public ContoCorrente(String iban, double saldo) {
        setIban(iban);
        setSaldo(saldo);
    }


    //Getter e Setter
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

    public ArrayList<Salvadanaio> getSalvadanai() {
        return salvadanai;
    }

    public void setSalvadanai(ArrayList<Salvadanaio> salvadanai) {
        this.salvadanai = salvadanai;
    }

    @Override
    public String toString() {
        return "ContoCorrente{" +
                "iban='" + iban + '\'' +
                ", saldo=" + saldo +
                ", account=" + account +
                ", salvadanai=" + salvadanai +
                '}';
    }
}
