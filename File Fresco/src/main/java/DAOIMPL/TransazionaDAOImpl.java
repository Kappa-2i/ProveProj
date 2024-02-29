package DAOIMPL;

import DAO.TransazioneDAO;
import DATABASE.DBConnection;
import ENTITY.ContoCorrente;
import ENTITY.Salvadanaio;
import ENTITY.Transazione;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TransazionaDAOImpl implements TransazioneDAO {
    public ArrayList<Transazione> selectTransazioniByIban(ContoCorrente conto){

            ArrayList<Transazione> transazioni = new ArrayList<Transazione>();

            // Query SQL per ottenere i dettagli dell'utente
        String query = "SELECT t.importo, t.causale, t.datatransazione, t.orariotransazione, t.tipotransazione, t.iban1 " +
                "FROM test.transazione t " +
                "WHERE (t.iban2 = '" + conto.getIban() + "' AND t.tipotransazione = 'Invia a') " +
                "OR (t.iban2 = '" + conto.getIban() + "' AND t.tipotransazione = 'Riceve da') "+
                "ORDER BY t.datatransazione DESC, t.orariotransazione DESC";

            try (Connection conn = DBConnection.getDBConnection().getConnection();  // Ottenimento della connessione al database
                 Statement statement = conn.createStatement()) {  // Creazione di un PreparedStatement

                // Esecuzione della query e gestione del ResultSet
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet != null){
                    while (resultSet.next()){
                        //Creazione degli oggetti Salvadanaio.
                        Transazione transazione = new Transazione(resultSet.getDouble("importo"), resultSet.getString("causale"),
                                resultSet.getString("datatransazione"), resultSet.getString("orariotransazione").substring(0,5), resultSet.getString("tipotransazione"),
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

    public Double[] viewReport(ContoCorrente conto, String mese){
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {
            // Prepara la query sostituendo i valori di iban e mese
            String query = "SELECT "
                    + " CAST(MAX(CASE WHEN t.tipotransazione = 'Invia a' THEN t.importo END) AS double precision) AS uscita_massima,"
                    + " CAST(MIN(CASE WHEN t.tipotransazione = 'Invia a' THEN t.importo END) AS double precision) AS uscita_minima,"
                    + " CAST(AVG(CASE WHEN t.tipotransazione = 'Invia a' THEN t.importo END) AS double precision) AS uscita_media,"
                    + " CAST(MAX(CASE WHEN t.tipotransazione = 'Riceve da' THEN t.importo END) AS double precision) AS entrata_massima,"
                    + " CAST(MIN(CASE WHEN t.tipotransazione = 'Riceve da' THEN t.importo END) AS double precision) AS entrata_minima,"
                    + " CAST(AVG(CASE WHEN t.tipotransazione = 'Riceve da' THEN t.importo END) AS double precision) AS entrata_media"
                    + " FROM test.transazione t"
                    + " WHERE (t.iban2 = ?)"
                    + " AND t.datatransazione BETWEEN TO_DATE(? || '-01', 'YYYY-MM-DD') AND (TO_DATE(? || '-01', 'YYYY-MM-DD') + INTERVAL '1 MONTH' - INTERVAL '1 day')::DATE";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Imposta i parametri della query
                pstmt.setString(1, conto.getIban());
                pstmt.setString(2, mese);
                pstmt.setString(3, mese);

                // Esegue la query
                ResultSet rs = pstmt.executeQuery();

                // Processa i risultati
                if (rs.next()) {
                    Double[] report = {rs.getDouble("entrata_massima"),
                            rs.getDouble("entrata_minima"),
                            rs.getDouble("entrata_media"),
                            rs.getDouble("uscita_massima"),
                            rs.getDouble("uscita_minima"),
                            rs.getDouble("uscita_media")};
                    return report;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double totaleInviatoMensile(ContoCorrente conto, String mese) {
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {
            // Prepara la query sostituendo i valori di iban e mese
            String query = "SELECT SUM(t.importo) AS totale_inviato " +
                    "FROM test.transazione t " +
                    "WHERE t.iban2 = ? " +
                    "AND t.tipotransazione = 'Invia a' " +
                    " AND t.datatransazione BETWEEN TO_DATE(? || '-01', 'YYYY-MM-DD') AND (TO_DATE(? || '-01', 'YYYY-MM-DD') + INTERVAL '1 MONTH' - INTERVAL '1 day')::DATE";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Imposta i parametri della query
                pstmt.setString(1, conto.getIban());
                pstmt.setString(2, mese);
                pstmt.setString(3, mese);

                // Esegue la query
                ResultSet rs = pstmt.executeQuery();

                // Processa i risultati
                if (rs.next()) {
                    Double totaleInviato = rs.getDouble("totale_inviato");
                    return totaleInviato;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double totaleRicevutoMensile(ContoCorrente conto, String mese) {
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {
            // Prepara la query sostituendo i valori di iban e mese
            String query = "SELECT SUM(t.importo) AS totale_ricevuto " +
                    "FROM test.transazione t " +
                    "WHERE t.iban2 = ? " +
                    "AND t.tipotransazione = 'Riceve da' " +
                    " AND t.datatransazione BETWEEN TO_DATE(? || '-01', 'YYYY-MM-DD') AND (TO_DATE(? || '-01', 'YYYY-MM-DD') + INTERVAL '1 MONTH' - INTERVAL '1 day')::DATE";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Imposta i parametri della query
                pstmt.setString(1, conto.getIban());
                pstmt.setString(2, mese);
                pstmt.setString(3, mese);

                // Esegue la query
                ResultSet rs = pstmt.executeQuery();

                // Processa i risultati
                if (rs.next()) {
                    Double totaleRicevuto = rs.getDouble("totale_ricevuto");
                    return totaleRicevuto;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String selectNameAndSurnameByIban(String iban) {
        try (Connection conn = DBConnection.getDBConnection().getConnection()) {
            // Prepara la query sostituendo i valori di iban e mese
            String query = "SELECT test.account.nome, test.account.cognome " +
                    "FROM test.transazione " +
                    "JOIN test.contocorrente ON test.transazione.iban1 = test.contocorrente.iban " +
                    "JOIN test.account ON test.contocorrente.Account_Email = test.account.email " +
                    "WHERE test.transazione.iban1 = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Imposta i parametri della query
                pstmt.setString(1, iban);


                // Esegue la query
                ResultSet rs = pstmt.executeQuery();

                // Processa i risultati
                if (rs.next()) {
                    String nomeCognomeIban = rs.getString("nome")+" "+rs.getString("cognome");
                    return nomeCognomeIban;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

