package DAO;

import ENTITY.ContoCorrente;
import ENTITY.Transazione;
import java.util.ArrayList;

public interface TransazioneDAO {
    public ArrayList<Transazione> selectTransazioniByIban(ContoCorrente conto);
    public Double[] viewReport(ContoCorrente conto, String mese);
    public double totaleInviatoMensile(ContoCorrente conto, String mese);
    public double totaleRicevutoMensile(ContoCorrente conto, String mese);
}
