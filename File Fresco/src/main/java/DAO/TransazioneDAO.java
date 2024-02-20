package DAO;

import ENTITY.ContoCorrente;
import ENTITY.Transazione;
import java.util.ArrayList;

public interface TransazioneDAO {
    public ArrayList<Transazione> selectTransazioniByIban(ContoCorrente conto);
}
