package DAO;

import ENTITY.ContoCorrente;
import ENTITY.Salvadanaio;

import java.util.ArrayList;

public interface SalvadanaioDAO {
    public ArrayList<Salvadanaio> selectSalvadanaio(ContoCorrente conto);
}
