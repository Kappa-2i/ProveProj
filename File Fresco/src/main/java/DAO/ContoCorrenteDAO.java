package DAO;

import ENTITY.ContoCorrente;

import java.util.ArrayList;

public interface ContoCorrenteDAO {
    public ArrayList<ContoCorrente> selectBankAccount(String email);
}
