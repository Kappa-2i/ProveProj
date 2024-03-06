package DAO;

import ENTITY.BankAccount;
import ENTITY.PiggyBank;
import EXCEPTIONS.MyExc;

import java.util.ArrayList;

public interface PiggyBankDAO {

    /**
     * Metodo per recuperare i salvadanai dal bd.
     * @param conto riferimento per recuperare i conti.
     * @return ArrayList di salvadanai.*/
    public ArrayList<PiggyBank> selectSalvadanaio(BankAccount conto);

    public void addPiggyBank(BankAccount contoscelto, String nome, double obiettivo, String descrizione) throws MyExc;
    public void deletePiggyBank(BankAccount contoscelto, String nome);
    public void fillPiggyBank(BankAccount contoscelto, String nome, double soldi);
    public void getMoneyByPiggyBank(BankAccount contoscelto, String nome, double soldi);
}
