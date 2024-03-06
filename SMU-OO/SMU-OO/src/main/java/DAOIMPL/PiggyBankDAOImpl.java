package DAOIMPL;

import DAO.PiggyBankDAO;
import DATABASE.DBConnection;
import ENTITY.BankAccount;
import ENTITY.PiggyBank;
import EXCEPTIONS.MyExc;

import java.sql.*;
import java.util.ArrayList;

public class PiggyBankDAOImpl implements PiggyBankDAO {
    public ArrayList<PiggyBank> selectSalvadanaio(BankAccount bankAccount){

        ArrayList<PiggyBank> piggyBanks = new ArrayList<PiggyBank>();

        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT * " +
                " FROM test.salvadanaio s " +
                " WHERE s.contocorrente_iban = '" + bankAccount.getIban() + "'";

        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

            // Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null){
                while (resultSet.next()){
                    //Creazione degli oggetti Salvadanaio.
                    PiggyBank piggyBank = new PiggyBank(resultSet.getString("nomesalvadanaio"), resultSet.getString("descrizione"),
                            resultSet.getDouble("obiettivo"), resultSet.getDouble("saldorisparmio"),
                            resultSet.getDouble("saldorimanente"), resultSet.getString("datacreazione")
                    );
                    //Agginta del salvadaio all'ArrayList di salvadanai
                    piggyBanks.add(piggyBank);
                }
                return piggyBanks;
            }
        } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        }
        return null;
    }

    public void addPiggyBank(BankAccount bankAccount, String name, double target, String description) throws MyExc {
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            //Chiamata della funzione del db.
            String callFunction = "{call test.crea_salvadanaio(?,?,?,?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, bankAccount.getIban());
            statement.setString(2, name);
            statement.setDouble(3, target);
            statement.setString(4, description);


            statement.executeQuery();

        } catch (SQLException e) {
            // "23505" è il codice di stato usato da PostgreSQL per indicare un errore di unique-violation
            if("23505".equals(e.getSQLState())) {
                throw new MyExc("Nome salvadanaio già esistente!");
            } else if ("23514".equals(e.getSQLState())) {
                throw new MyExc("Inserisci una cifra valida");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void deletePiggyBank(BankAccount contoscelto, String nome){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            //Chiamata della funzione del db.
            String callFunction = "{call test.rimuovi_salvadanaio(?,?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, contoscelto.getIban());
            statement.setString(2, nome);


            statement.executeQuery();

        } catch (SQLException e) {
            //Gestione delle eccezioni SQL
            e.printStackTrace();
        }
    }

    public void fillPiggyBank(BankAccount bankAccount, String name, double money){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            //Chiamata della funzione del db.
            String callFunction = "{call test.invia_soldi_al_salvadanaio(?,?,?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, bankAccount.getIban());
            statement.setString(2, name);
            statement.setDouble(3, money);

            statement.executeQuery();

        } catch (SQLException e) {
            //Gestione delle eccezioni SQL
            e.printStackTrace();
        }
    }

    public void getMoneyByPiggyBank(BankAccount bankAccount, String name, double money){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            //Chiamata della funzione del db.
            String callFunction = "{call test.prendi_soldi_dal_salvadanaio(?,?,?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, bankAccount.getIban());
            statement.setString(2, name);
            statement.setDouble(3, money);

            statement.executeQuery();

        } catch (SQLException e) {
            //Gestione delle eccezioni SQL
            e.printStackTrace();
        }
    }
}
