package DAO;

import ENTITY.Collection;
import ENTITY.BankAccount;
import EXCEPTIONS.MyExc;

import java.util.ArrayList;

public interface CollectionDAO {
    /**
     * */
    public ArrayList<Collection> selectCollectionByIban(BankAccount conto);
    public void addCollection(BankAccount conto, String name, String description) throws MyExc;
    public void deleteCollection(BankAccount conto, String name);
}
