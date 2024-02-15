package DAO;

import ENTITY.Account;
import ENTITY.ContoCorrente;

import java.util.ArrayList;

public interface ContoCorrenteDAO {
    public ArrayList<ContoCorrente> selectBankAccount(Account account);
    public Boolean insertBankAccount(String email);
    public void deleteBankAccount(String iban);
}
