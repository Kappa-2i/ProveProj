package DAOIMPL;

import DAO.BankAccountDAO;
import DATABASE.DBConnection;
import ENTITY.Account;
import ENTITY.BankAccount;

import java.sql.*;
import java.util.ArrayList;

public class BankAccountDAOImpl implements BankAccountDAO {
    @Override
    public ArrayList<BankAccount> selectBankAccountByAccount(Account account){

        ArrayList<BankAccount> contiCorrenti = new ArrayList<BankAccount>();
        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT cc.iban, cc.saldo " +
                "FROM test.contocorrente cc " +
                " WHERE cc.account_email = '" + account.getEmail() + "'";

        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

            // Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null){
                while (resultSet.next()){
                    BankAccount conto = new BankAccount(resultSet.getString("Iban"), resultSet.getDouble("Saldo"), account, null, null);
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
    public BankAccount updateBankAccount(BankAccount bankAccount){


        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT cc.iban, cc.saldo " +
                "FROM test.contocorrente cc " +
                " WHERE cc.iban = '" + bankAccount.getIban() + "'";

        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

            // Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null){
                while (resultSet.next()){
                    BankAccount refreshBankAccount = new BankAccount(resultSet.getString("iban"), resultSet.getDouble("saldo"));
                    return refreshBankAccount;
                }
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

            //Chiamata della funzione del db.
            String callFunction = "{call test.crea_contocorrente_con_carta(?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, email);

            statement.executeQuery();
            return true;

        } catch (SQLException e) {
            //Gestione delle eccezioni SQL
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteBankAccount(String iban){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()){

            //Chiamata della funzione del db.
            String callFunction = "{call test.rimuovi_contocorrente_con_carta(?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, iban);
            statement.executeQuery();
        }
        catch (SQLException e){
            //Gestione delle eccezioni SQL
            e.printStackTrace();
        }
    }

}
