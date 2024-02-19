package DAO;

import ENTITY.ContoCorrente;
import ENTITY.Salvadanaio;

import java.util.ArrayList;

public interface SalvadanaioDAO {

    /**
     * Metodo per recuperare i salvadanai dal bd.
     * @param conto riferimento per recuperare i conti.
     * @return ArrayList di salvadanai.*/
    public ArrayList<Salvadanaio> selectSalvadanaio(ContoCorrente conto);

    public void addPiggyBank(ContoCorrente contoscelto, String nome, double obiettivo, String descrizione);
    public void deletePiggyBank(ContoCorrente contoscelto, String nome);
    public void fillPiggyBank(ContoCorrente contoscelto, String nome, double soldi);
    public void getMoneyByPiggyBank(ContoCorrente contoscelto, String nome, double soldi);
}
