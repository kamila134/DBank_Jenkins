package co.wedevx.digitalbank.automation.ui.models;

public class BankingType {

    private String bankingType;
    private String accountType;
    private String ownershipType;
    private String accountName;
    private double initialDepositAmount;

    public BankingType(String bankingType, String accountType, String ownershipType, String accountName, double initialDepositAmount) {
        this.bankingType = bankingType;
        this.accountType = accountType;
        this.ownershipType = ownershipType;
        this.accountName = accountName;
        this.initialDepositAmount = initialDepositAmount;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getOwnershipType() {
        return ownershipType;
    }


    public String getAccountName() {
        return accountName;
    }
    public String getBankingType() {
        return bankingType;
    }

    public double getInitialDepositAmount() {
        return initialDepositAmount;
    }
}
