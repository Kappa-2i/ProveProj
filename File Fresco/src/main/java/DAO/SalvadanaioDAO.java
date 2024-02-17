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
}
