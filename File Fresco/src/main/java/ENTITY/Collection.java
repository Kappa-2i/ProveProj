package ENTITY;

import java.util.ArrayList;

public class Collection {
    private String nameCollection;
    private String description;
    private ArrayList<Transazione> transactions;
    private ContoCorrente bankAccount;

    public Collection(String nameCollection, String description) {
        setNameCollection(nameCollection);
        setDescription(description);
    }

    public String getNameCollection() {
        return nameCollection;
    }

    public void setNameCollection(String nameCollection) {
        this.nameCollection = nameCollection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Transazione> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transazione> transactions) {
        this.transactions = transactions;
    }

    public ContoCorrente getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(ContoCorrente bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "nameCollection='" + nameCollection + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
