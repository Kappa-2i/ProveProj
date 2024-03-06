package DAO;

import ENTITY.Collection;
import ENTITY.BankAccount;
import ENTITY.Transaction;
import EXCEPTIONS.MyExc;

import java.util.ArrayList;

public interface TransactionDAO {
    public ArrayList<Transaction> selectTransazioniByIban(BankAccount conto);
    public Double[] viewReport(BankAccount conto, String mese);
    public double totaleInviatoMensile(BankAccount conto, String mese);
    public double totaleRicevutoMensile(BankAccount conto, String mese);
    public String selectNameAndSurnameByIban(String iban);
    public void sendBankTransfer(BankAccount conto, String receiver, String amount, String reason, String cat, String nameCollection);
    public void sendIstantBankTransfer(BankAccount conto, String receiver, String amount, String reason, String cat, String nameCollection);
    public boolean checkIban(String receiver, String name, String surname) throws MyExc;
    public ArrayList<Transaction> selectTransactionsByCollection(Collection collection, BankAccount conto);
    public double selectSumOfCollections(BankAccount conto, String name);
}
