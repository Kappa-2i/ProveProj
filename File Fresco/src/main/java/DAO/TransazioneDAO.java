package DAO;

import ENTITY.Collection;
import ENTITY.ContoCorrente;
import ENTITY.Transazione;
import EXCEPTIONS.MyExc;

import java.util.ArrayList;

public interface TransazioneDAO {
    public ArrayList<Transazione> selectTransazioniByIban(ContoCorrente conto);
    public Double[] viewReport(ContoCorrente conto, String mese);
    public double totaleInviatoMensile(ContoCorrente conto, String mese);
    public double totaleRicevutoMensile(ContoCorrente conto, String mese);
    public String selectNameAndSurnameByIban(String iban);
    public void sendBankTransfer(ContoCorrente conto, String receiver, String amount, String reason, String cat, String nameCollection);
    public void sendIstantBankTransfer(ContoCorrente conto, String receiver, String amount, String reason, String cat, String nameCollection);
    public boolean checkIban(String receiver, String name, String surname) throws MyExc;
    public ArrayList<Transazione> selectTransactionsByCollection(Collection collection, ContoCorrente conto);
    public double selectSumOfCollections(ContoCorrente conto, String name);
}
