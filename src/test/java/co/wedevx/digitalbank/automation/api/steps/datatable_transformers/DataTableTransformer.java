package co.wedevx.digitalbank.automation.api.steps.datatable_transformers;

import co.wedevx.digitalbank.automation.api.models.datatable.AccountDataTable;
import co.wedevx.digitalbank.automation.api.models.datatable.UserFromDataTable;
import io.cucumber.java.DataTableType;

import java.util.Map;

public class DataTableTransformer {

    @DataTableType
    public UserFromDataTable userEntry(Map<String,String> entry) {
        String title = entry.get("title");
        String firstName = entry.get("firstName");
        String lastName = entry.get("lastName");
        String gender = entry.get("gender");
        String dateOfBirth = entry.get("dob");
        String ssn = entry.get("ssn");
        String emailAddress = entry.get("emailAddress");
        String password = entry.get("password");
        String address = entry.get("address");
        String locality = entry.get("locality");
        String region = entry.get("region");
        String postalCode = entry.get("postalCode");
        String country = entry.get("country");
        String homePhone = entry.get("homePhone");

        return new UserFromDataTable(title, firstName,  lastName,  gender,  dateOfBirth,  ssn,  emailAddress,  password,  address,
                locality,  region,  country,  postalCode,  homePhone);
    }

    @DataTableType
    public AccountDataTable accountEntry(Map<String,String> entry) {
        String accountName = entry.get("accountName");
        String accountTypeCode = entry.get("accountTypeCode");
        String openingDeposit = entry.get("openingDeposit");
        String ownerTypeCode = entry.get("ownerTypeCode");
        String accountStandingName = entry.get("accountStandingName");
        return new AccountDataTable(accountName,accountTypeCode,openingDeposit,ownerTypeCode, accountStandingName);

    }

}
