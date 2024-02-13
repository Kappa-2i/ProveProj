package DAO;

import ENTITY.ContoCorrente;

import java.util.ArrayList;

public interface ContoCorrenteDAO {
    public ArrayList<ContoCorrente> selectBankAccount(String email);
    public Boolean insertBankAccount(String email);
    public void deleteBankAccount(String iban);
}
