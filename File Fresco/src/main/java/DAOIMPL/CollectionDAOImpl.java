package DAOIMPL;

import DAO.CollectionDAO;
import DATABASE.DBConnection;
import ENTITY.Collection;
import ENTITY.ContoCorrente;
import ENTITY.Transazione;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


}
