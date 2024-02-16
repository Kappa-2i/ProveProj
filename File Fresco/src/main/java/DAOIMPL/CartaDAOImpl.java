package DAOIMPL;

import CONTROLLER.Controller;
import DAO.CartaDAO;
import DATABASE.DBConnection;
import ENTITY.Carta;
import ENTITY.CartaDiCredito;
import ENTITY.CartaDiDebito;
import ENTITY.ContoCorrente;

import java.sql.*;

public class CartaDAOImpl implements CartaDAO {

    @Override
    public Carta selectCard(ContoCorrente contoCorrente){
        String query = "SELECT c.pan, c.pin, c.cvv, c.tipocarta, c.maxinvio, c.limitefondi, c.contocorrente_iban " +
                "FROM test.carta c " +
                "WHERE c.contocorrente_iban = '" + contoCorrente.getIban() + "'";
        try(Connection conn = DBConnection.getDBConnection().getConnection();
            Statement statement = conn.createStatement()){

            //Esecuzione della query e gestione del ResultSet
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet != null){
                while(resultSet.next()){
                    if (resultSet.getString("tipocarta").equals("CartaDiDebito")) {
                        Carta cartaDiDebito = new CartaDiDebito(resultSet.getString("pan"), resultSet.getString("pin"), resultSet.getString("cvv"), resultSet.getString("tipocarta"),
                                contoCorrente, resultSet.getDouble("limitefondi"));
                        return cartaDiDebito;
                    }
                    else {
                        Carta cartaDiCredito = new CartaDiCredito(resultSet.getString("pan"), resultSet.getString("pin"), resultSet.getString("cvv"), resultSet.getString("tipocarta"),
                                contoCorrente, resultSet.getDouble("maxinvio"));
                        return cartaDiCredito;
                    }

                }
            }
        }
        catch (SQLException e){
            // Gestione delle eccezioni SQL
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void upgradeCarta(String pan){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()){

            String callFunction = "{call test.upgrade_carta(?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, pan);

            statement.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void downgradeCarta(String pan){
        CallableStatement statement = null;
        try (Connection conn = DBConnection.getDBConnection().getConnection()){

            String callFunction = "{call test.downgrade_carta(?)}";

            statement = conn.prepareCall(callFunction);

            statement.setString(1, pan);

            statement.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
