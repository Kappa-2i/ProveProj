package DAO;

import java.sql.Date;

public interface PersonaDAO {
    public Boolean insertUser(String nome, String cognome, String telefono, String dataNascita, String citta, String via, String nCivico, String cap, String codiceFiscale);
}
