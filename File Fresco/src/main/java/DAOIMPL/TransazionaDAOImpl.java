package DAOIMPL;

import DAO.TransazioneDAO;
import DATABASE.DBConnection;
import ENTITY.ContoCorrente;
import ENTITY.Salvadanaio;
import ENTITY.Transazione;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TransazionaDAOImpl implements TransazioneDAO {
    public ArrayList<Transazione> selectTransazioniByIban(ContoCorrente conto){

            ArrayList<Transazione> transazioni = new ArrayList<Transazione>();

            // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT t.importo, t.causale, t.datatransazione, t.orariotransazione, t.tipotransazione, t.iban1 " +
                "FROM test.transazione t " +
                "WHERE (t.iban2 = '" + conto.getIban() + "' AND t.tipotransazione = 'Invia a') " +
                "OR (t.iban2 = '" + conto.getIban() + "' AND t.tipotransazione = 'Riceve da')";

            try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
                 Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

                // Esecuzione della query e gestione del ResultSet
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet != null){
                    while (resultSet.next()){
                        //Creazione degli oggetti Salvadanaio.
                        Transazione transazione = new Transazione(resultSet.getDouble("importo"), resultSet.getString("causale"),
                                resultSet.getString("datatransazione"), resultSet.getString("orariotransazione"), resultSet.getString("tipotransazione"),
                                resultSet.getString("iban1"), conto);
                        //Agginta del salvadaio all'ArrayList di salvadanai
                        transazioni.add(transazione);
                    }
                    return transazioni;
                }
            } catch (SQLException e) {
                // Gestione delle eccezioni SQL
                e.printStackTrace();
            }
            return null;
    }
}

