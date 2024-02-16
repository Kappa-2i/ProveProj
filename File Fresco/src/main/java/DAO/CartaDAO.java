package DAO;


import ENTITY.Carta;
import ENTITY.ContoCorrente;

public interface CartaDAO {
    public Carta selectCard(ContoCorrente contoCorrente);
    public void upgradeCarta(String pan);
    public void downgradeCarta(String pan);

}
