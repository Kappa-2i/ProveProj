package DAOIMPL;

import DAO.SalvadanaioDAO;
import DATABASE.DBConnection;
import ENTITY.ContoCorrente;
import ENTITY.Salvadanaio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalvadanaioDAOImpl implements SalvadanaioDAO {
    public ArrayList<Salvadanaio> selectSalvadanaio(ContoCorrente conto){

        ArrayList<Salvadanaio> salvadanai = new ArrayList<Salvadanaio>();

        // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT * " +
                "FROM test.salvadanaio s" +
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
}
