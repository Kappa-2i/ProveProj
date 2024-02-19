package DAOIMPL;

import DAO.SalvadanaioDAO;
import DATABASE.DBConnection;
import ENTITY.ContoCorrente;
import ENTITY.Salvadanaio;

import java.sql.*;
import java.util.ArrayList;

public class SalvadanaioDAOImpl implements SalvadanaioDAO {
    public ArrayList<Salvadanaio> selectSalvadanaio(ContoCorrente conto){

        ArrayList<Salvadanaio> salvadanai = new ArrayList<Salvadanaio>();

        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT * " +
                " FROM test.salvadanaio s " +
                " WHERE s.contocorrente_iban = '" + conto.getIban() + "'";

        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

            // Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null){
                while (resultSet.next()){
                    //Creazione degli oggetti Salvadanaio.
                    Salvadanaio salvadanaio = new Salvadanaio(resultSet.getString("nomesalvadanaio"), resultSet.getString("descrizione"),
                            resultSet.getDouble("obiettivo"), resultSet.getDouble("saldorisparmio"),
                            resultSet.getDouble("saldorimanente"), resultSet.getString("datacreazione")
                    );
                    //Agginta del salvadaio all'ArrayList di salvadanai
                    salvadanai.add(salvadanaio);
                }
                return salvadanai;
            }
        } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        }
        return null;
    }

    public void addPiggyBank(ContoCorrente contoscelto, String nome, double obiettivo, String descrizione){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            //Chiamata della funzione del db.
            String callFunction = "{call test.crea_salvadanaio(?,?,?,?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, contoscelto.getIban());
            statement.setString(2, nome);
            statement.setDouble(3, obiettivo);
            statement.setString(4, descrizione);


            statement.executeQuery();

        } catch (SQLException e) {
            //Gestione delle eccezioni SQL
            e.printStackTrace();
        }
    }

    public void deletePiggyBank(ContoCorrente contoscelto, String nome){
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

    public void fillPiggyBank(ContoCorrente contoscelto, String nome, double soldi){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            //Chiamata della funzione del db.
            String callFunction = "{call test.invia_soldi_al_salvadanaio(?,?,?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, contoscelto.getIban());
            statement.setString(2, nome);
            statement.setDouble(3, soldi);

            statement.executeQuery();

        } catch (SQLException e) {
            //Gestione delle eccezioni SQL
            e.printStackTrace();
        }
    }

    public void getMoneyByPiggyBank(ContoCorrente contoscelto, String nome, double soldi){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            //Chiamata della funzione del db.
            String callFunction = "{call test.prendi_soldi_dal_salvadanaio(?,?,?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, contoscelto.getIban());
            statement.setString(2, nome);
            statement.setDouble(3, soldi);

            statement.executeQuery();

        } catch (SQLException e) {
            //Gestione delle eccezioni SQL
            e.printStackTrace();
        }
    }
}
