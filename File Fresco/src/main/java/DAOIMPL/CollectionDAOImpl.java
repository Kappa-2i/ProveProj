package DAOIMPL;

import DAO.CollectionDAO;
import DATABASE.DBConnection;
import ENTITY.Collection;
import ENTITY.ContoCorrente;
import ENTITY.Transazione;
import EXCEPTIONS.MyExc;

import java.sql.*;
import java.util.ArrayList;

public class CollectionDAOImpl implements CollectionDAO {

    public ArrayList<Collection> selectCollectionByIban(ContoCorrente conto){

        ArrayList<Collection> collections = new ArrayList<Collection>();

        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT r.nomeraccolta, r.descrizione " +
                "FROM test.raccolta r " +
                "WHERE r.contocorrente_iban = '" +conto.getIban()+ "'";

        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

            // Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet != null){
                while (resultSet.next()){
                    //Creazione degli oggetti Salvadanaio.
                    Collection collection = new Collection(resultSet.getString("nomeraccolta"), resultSet.getString("descrizione"));
                    //Aggiunta della collezione all'ArrayList di collezioni
                    collections.add(collection);
                }
                return collections;
            }
        } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        }
        return null;
    }


    public void addCollection(ContoCorrente conto, String name, String description) throws MyExc{
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {

            //Chiamata della funzione del db.
            String callFunction = "{call test.crea_raccolta(?,?,?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, conto.getIban());


            statement.executeQuery();

        } catch (SQLException e) {
            // "23505" è il codice di stato usato da PostgreSQL per indicare un errore di unique-violation
            if("23505".equals(e.getSQLState())) {
                throw new MyExc("Nome Raccolta già esistente!");
            }
            else {
                e.printStackTrace();
            }
        }
    }

}
