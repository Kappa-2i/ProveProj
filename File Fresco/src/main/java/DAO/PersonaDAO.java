package DAO;

import ENTITY.Persona;


public interface PersonaDAO {

    /***/
    public Boolean insertUser(String nome, String cognome, String telefono, String dataNascita, String citta, String via, String nCivico, String cap, String codiceFiscale);
    public Persona selectPersonaFromEmail(String email);
}
