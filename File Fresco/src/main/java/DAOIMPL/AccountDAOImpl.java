package DAOIMPL;

import DAO.AccountDao;
import DATABASE.DBConnection;
import ENTITY.Account;
import EXCEPTIONS.MyExc;

import java.lang.invoke.StringConcatFactory;
import java.sql.*;

public class AccountDAOImpl implements AccountDao {


    @Override
    public void insertAccount(String email, String nomeUtente, String password, String codiceFiscale){
        String insert = "INSERT INTO test.account(email, nomeutente, password, persona_codicefiscale) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             PreparedStatement statement = conn.prepareStatement(insert)) {  // Creazione di un PreparedStatement

            statement.setString(1, email);
            statement.setString(2, nomeUtente);
            statement.setString(3, password);
            statement.setString(4, codiceFiscale);

            // Esecuzione dell'insert
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        }
    }



    @Override
    public Account checkCredentials(String email, String password){
        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT a.email, a.password, a.nomeutente " +
                     "FROM test.account a " +
                    " WHERE a.email = '" + email + "' AND a.password = '" + password + "'";
        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

            //statement.setString(1, email);  // Impostazione del primo parametro (email)
            //statement.setString(2, password);  // Impostazione del secondo parametro (password)

            // Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);

            // Verifico se ho una tupla da analizzare
            if (resultSet != null){
                while (resultSet.next()) {
                    // Ritorno dei dati dell'utente sotto forma di stringhe
                    Account account = new Account(resultSet.getString("Email"), resultSet.getString("Password"), resultSet.getString("NomeUtente"));
                    return account;
                }
            }
        } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        } catch (MyExc e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
