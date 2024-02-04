package DAO;

import ENTITY.Account;

import java.sql.SQLException;

// Classe DAO per la gestione dell'account nel database
public interface AccountDao {

//    // Simulazione di un database con un account di esempio
//    private static final Account exampleAccount = new Account("example@email.com", "password123");
//
//    // Metodo per verificare l'esistenza di un account nel database
//    public static boolean accountExists(Account account) {
//        return exampleAccount.getEmail().equals(account.getEmail()) &&
//                exampleAccount.getPassword().equals(account.getPassword());
//    }

    Account checkCredentials(String email, String password) throws SQLException;
}