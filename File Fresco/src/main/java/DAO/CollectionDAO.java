package DAO;

import ENTITY.Collection;
import ENTITY.ContoCorrente;

import java.util.ArrayList;

public interface CollectionDAO {
    public ArrayList<Collection> selectCollectionByIban(ContoCorrente conto);

}
