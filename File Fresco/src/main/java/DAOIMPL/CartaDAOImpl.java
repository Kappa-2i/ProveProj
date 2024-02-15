package DAOIMPL;

import CONTROLLER.Controller;
import DAO.CartaDAO;
import DATABASE.DBConnection;
import ENTITY.Carta;
import ENTITY.CartaDiDebito;
import ENTITY.ContoCorrente;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CartaDAOImpl implements CartaDAO {

    @Override
    public Carta selectCard(ContoCorrente contoCorrente){
        String query = "SELECT c.pan, c.pin, c.cvv, c.limitefondi, c.contocorrente_iban " +
                "FROM test.carta c " +
                "WHERE c.contocorrente_iban = '" + contoCorrente.getIban() + "'";
        try(Connection conn = DBConnection.getDBConnection().getConnection();
            Statement statement = conn.createStatement()){

            //Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet != null){
                while(resultSet.next()){
                    Carta carta = new CartaDiDebito(resultSet.getString("pan"), resultSet.getString("pin"), resultSet.getString("cvv"),
                            contoCorrente, resultSet.getDouble("limitefondi"));
                    return carta;
                }
            }

        }
        catch (SQLException e){
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        }
        return null;
    }
}
