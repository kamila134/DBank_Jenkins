package co.wedevx.digitalbank.automation.api.models.datatable;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.text.DecimalFormat;

public class AccountDataTable {
    private String accountName;
    private String accountTypeCode;
    private String openingDeposit;
    private String ownerTypeCode;
    private String accountStandingName;

    public AccountDataTable(String accountName, String accountTypeCode, String openingDeposit, String ownerTypeCode, String accountStandingName) {
        this.accountName = accountName;
        this.accountTypeCode = accountTypeCode;
        this.openingDeposit = openingDeposit;
        this.ownerTypeCode = ownerTypeCode;
        this.accountStandingName = accountStandingName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountName='" + accountName + '\'' +
                ", accountTypeCode='" + accountTypeCode + '\'' +
                ", openingDeposit=" + openingDeposit +
                ", ownerTypeCode='" + ownerTypeCode + '\'' +
                '}';
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getOpeningDeposit() {
//        double num = Double.parseDouble(openingDeposit);
//        DecimalFormat df = new DecimalFormat("#0.00");
//        return df.format(num);
        return openingDeposit;
    }

    public void setOpeningDeposit(String openingDeposit) {
        this.openingDeposit = openingDeposit;
    }

    public String getOwnerTypeCode() {
        return ownerTypeCode;
    }

    public void setOwnerTypeCode(String ownerTypeCode) {
        this.ownerTypeCode = ownerTypeCode;
    }

    public String getAccountStandingName() {
        return accountStandingName;
    }

    public void setAccountStandingName(String accountStandingName) {
        this.accountStandingName = accountStandingName;
    }
}
