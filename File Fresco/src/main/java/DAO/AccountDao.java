package DAO;

import ENTITY.Account;

import java.sql.SQLException;

// Classe DAO per la gestione dell'account nel database
public interface AccountDao {

    Account checkCredentials(String email, String password);
    public void insertAccount(String email, String nomeUtente, String password, String codiceFiscale);

}