package co.wedevx.digitalbank.automation.ui.steps.data_transformers;

import co.wedevx.digitalbank.automation.ui.models.*;
import io.cucumber.java.DataTableType;

import java.util.Map;

public class DataTableTransformer {

    @DataTableType
    public BankingType checkingAccountEntry (Map<String,String> entry) {
        String bankingType = entry.get("bankingType");
        String accountType = entry.get("accountType");
         String ownershipType = entry.get("ownershipType");
         String accountName = entry.get("accountName");
         double initialDepositAmount = Double.parseDouble(entry.get("initialDepositAmount"));

         return new BankingType(bankingType, accountType,ownershipType,accountName,initialDepositAmount);
    }
    
    @DataTableType
    public AccountDetails accountDetailsEntry(Map<String,String> entry) {
         String title = entry.get("title");
         String firstName = entry.get("firstName");
         String lastName = entry.get("lastName");
         String gender = entry.get("gender");
         String dateOfBirth = entry.get("dateOfBirth");
         String ssn = entry.get("ssn");
         String email = entry.get("email");
         String password = entry.get("password");
         String address = entry.get("address");
         String locality = entry.get("locality");
         String region = entry.get("region");
         String postalCode = entry.get("postalCode");
         String country = entry.get("country");
         String homePhone = entry.get("homePhone");

         return new AccountDetails( title, firstName,  lastName,  gender,  dateOfBirth,  ssn,  email,  password,  address,
                 locality,  region,  country,  postalCode,  homePhone);
    }

    @DataTableType
    public AccountUpdate accountDetailsUpdateEntry(Map<String,String> entry) {
        String title = entry.get("title");
        String firstName = entry.get("firstName");
        String lastName = entry.get("lastName");
        String address = entry.get("address");
        String locality = entry.get("locality");
        String region = entry.get("region");
        String postalCode = entry.get("postalCode");
        String country = entry.get("country");
        String homePhone = entry.get("homePhone");

        return new AccountUpdate( title, firstName,  lastName, address,
                locality,  region,  country,  postalCode,  homePhone);
    }

}
