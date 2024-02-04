package DAOIMPL;

import DAO.AccountDao;
import DATABASE.DBConnection;
import ENTITY.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDao {
    public Account checkCredentials(String email, String password) {
        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT a.email, a.password " +
                     "FROM test.account a " +
                     "WHERE a.email = ? AND a.password = ?";

        // Utilizzo di un blocco try-with-resources per la gestione automatica delle risorse
        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             PreparedStatement statement = conn.prepareStatement(query)) {  // Creazione di un PreparedStatement

            statement.setString(1, email);  // Impostazione del primo parametro (email)
            statement.setString(2, password);  // Impostazione del secondo parametro (password)

            // Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery();

            // Verifico se ho una tupla da analizzare
            if (resultSet != null){
                while (resultSet.next()) {
                    // Ritorno dei dati dell'utente sotto forma di stringhe
                    Account account = new Account(resultSet.getString("Email"), resultSet.getString("Password"));
                    return account;
                }
            }
        } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        }
        // Ritorno di null in caso di errore o se le credenziali non sono valide
        return null;
    }
}
