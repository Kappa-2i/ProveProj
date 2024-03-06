package DAO;


import ENTITY.Card;
import ENTITY.BankAccount;

public interface CardDAO {
    /**
     * Metodo per la selezione delle carte per un determinato Conto Corrente dal bd.
     * @param bankAccount riferimento del conto corrente.
     * @return carta recuperata dal db.*/
    public Card selectCard(BankAccount bankAccount);

    /**
     * Metodo per eseguire l'upgrade della carta sul db.*/
    public void upgradeCarta(String pan);

    /**
     * Metodo per effettuare il downgrade della carta sul db.*/
    public void downgradeCarta(String pan);

}
