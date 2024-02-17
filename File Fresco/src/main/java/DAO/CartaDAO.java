package DAO;


import ENTITY.Carta;
import ENTITY.ContoCorrente;

public interface CartaDAO {
    /**
     * Metodo per la selzione delle carte per un determinato Conto Corrente dal bd.
     * @param contoCorrente riferimento la carta dal recuperare.
     * @return carta recuperata dal db.*/
    public Carta selectCard(ContoCorrente contoCorrente);

    /**
     * Metodo per eseguire l'upgrade della carta sul db.*/
    public void upgradeCarta(String pan);

    /**
     * Metodo per effettuare il downgrade della carta sul db.*/
    public void downgradeCarta(String pan);

}
