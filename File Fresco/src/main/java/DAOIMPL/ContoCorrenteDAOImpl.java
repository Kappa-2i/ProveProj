package DAOIMPL;

import DAO.ContoCorrenteDAO;
import DATABASE.DBConnection;
import ENTITY.ContoCorrente;

import java.sql.*;
import java.util.ArrayList;

public class ContoCorrenteDAOImpl implements ContoCorrenteDAO {
    @Override
    public ArrayList<ContoCorrente> selectBankAccount(String email){

        ArrayList<ContoCorrente> contiCorrenti = new ArrayList<ContoCorrente>();
        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT cc.iban, cc.saldo " +
                "FROM test.contocorrente cc " +
                " WHERE cc.account_email = '" + email + "'";

        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

            // Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null){
                while (resultSet.next()){
                    ContoCorrente conto = new ContoCorrente(resultSet.getString("Iban"), resultSet.getDouble("Saldo"));
                    contiCorrenti.add(conto);
                }
                return contiCorrenti;
            }
    } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Boolean insertBankAccount(String email) {
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            String callFunction = "{call test.crea_contocorrente_con_carta2(?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, email);

            statement.executeQuery();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}