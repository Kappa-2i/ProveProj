package ENTITY;

// Classe del modello che rappresenta il salvadanaio

public class PiggyBank {

    //Attributi
    private String namePiggyBank;
    private String description;
    private double target;
    private double savingBalance;
    private double remainingBalance;
    private String dateCreation;

    //Costruttori
    public PiggyBank(String namePiggyBank, String description, double target, double savingBalance, double remainingBalance, String dataApertura) {
        setNamePiggyBank(namePiggyBank);
        setDescription(description);
        setTarget(target);
        setSavingBalance(savingBalance);
        setRemainingBalance(remainingBalance);
        setDateCreation(dataApertura);
    }

    //Getter e Setter
    public String getNamePiggyBank() {
        return namePiggyBank;
    }

    public void setNamePiggyBank(String namePiggyBank) {
        this.namePiggyBank = namePiggyBank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getSavingBalance() {
        return savingBalance;
    }

    public void setSavingBalance(double savingBalance) {
        this.savingBalance = savingBalance;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

}
