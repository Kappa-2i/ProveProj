package DAOIMPL;

import DAO.PersonaDAO;
import DATABASE.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PersonaDAOImpl implements PersonaDAO {

    @Override
    public void insertUser(String nome, String cognome, String telefono, String dataNascita, String citta, String via, String nCivico, String cap, String codiceFiscale){
        String insert = "INSERT INTO db.persona(codiceFiscale, nome, cognome, dataNascita, numerotelefono, città, via, n_civico, cap) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
             PreparedStatement statement = conn.prepareStatement(insert)) {  // Creazione di un PreparedStatement

            statement.setString(1, codiceFiscale);
            statement.setString(2, nome);
            statement.setString(3, cognome);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(dataNascita);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            statement.setDate(4, sqlDate);

            statement.setString(5, telefono);
            statement.setString(6, citta);
            statement.setString(7, via);
            statement.setString(8, nCivico);
            statement.setString(9, cap);


            // Esecuzione dell'insert
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
