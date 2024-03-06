package DAO;

import ENTITY.Account;

// Classe DAO per la gestione dell'account nel database
public interface AccountDAO {

    /**
     * Metodo che recupera email e password dal db per controllare la validità delle credenziali inserite.
     * @param email riferimento per l'email da controllare.
     * @param password riferimento per la password da controllare.*/
    Account checkCredentials(String email, String password);

    /**
     * Metodo per l'inserimeto di un nuovo accont all'interno del db
     * @param email riferimento per l'email da inserire.
     * @param name riferimento per il nome da inserire.
     * @param password riferimento per la password da inserire.
     * @param surname riferimento per il cognome da inserire.*/
    public void insertAccount(String email, String name, String password, String surname);

}