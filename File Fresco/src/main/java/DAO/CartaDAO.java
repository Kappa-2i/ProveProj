package DAO;


import ENTITY.Carta;
import ENTITY.ContoCorrente;

public interface CartaDAO {
    public Carta selectCard(ContoCorrente contoCorrente);


}
