package DAO;

import ENTITY.Account;

// Classe DAO per la gestione dell'account nel database
public interface AccountDAO {

    Account checkCredentials(String email, String password);
    public void insertAccount(String email, String nomeUtente, String password, String codiceFiscale);

}