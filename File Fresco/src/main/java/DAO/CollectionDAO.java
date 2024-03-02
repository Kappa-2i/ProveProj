package DAO;

import ENTITY.Collection;
import ENTITY.ContoCorrente;
import EXCEPTIONS.MyExc;

import java.util.ArrayList;

public interface CollectionDAO {
    public ArrayList<Collection> selectCollectionByIban(ContoCorrente conto);
    public void addCollection(ContoCorrente conto, String name, String description) throws MyExc;
}
