package co.wedevx.digitalbank.automation.api.steps;

import co.wedevx.digitalbank.automation.api.models.api_response.Account;
import co.wedevx.digitalbank.automation.api.models.api_response.AccountType;
import co.wedevx.digitalbank.automation.api.models.datatable.AccountDataTable;
import co.wedevx.digitalbank.automation.api.utils.RestHttpRequest;
import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.api.utils.ObjectMapperUtils.objectMapper;
import static org.junit.jupiter.api.Assertions.*;


public class CreateAccountSteps {

    private Account account;
    private static Response invalidAccountResponse;
    public static int bankingAccountId;

    @When("the user creates a new banking account:")
    public void the_user_creates_a_new_banking_account(List<AccountDataTable> accountDataTableList) throws JsonProcessingException {
        String newAccountJson = objectMapper.writeValueAsString(accountDataTableList.get(0));
        Response postNewUser = RestHttpRequest.sendPostRequestWithPathParams("id",db_userAccountSteps.userId,"user/{id}/account",newAccountJson);
        account = objectMapper.readValue(postNewUser.asString(), Account.class);
        postNewUser.prettyPrint();

        bankingAccountId = postNewUser.jsonPath().getInt("id");
    }

    @Then("the following account is created:")
    public void the_following_account_is_created(List<AccountDataTable> accountDataTableList) {
        assertEquals(accountDataTableList.get(0).getAccountName(),account.getName(), "Account name mismatch after creating account");
        assertEquals(accountDataTableList.get(0).getAccountTypeCode(),account.getAccountType().getCode(), "Account type code mismatch after creating account");
        assertEquals(accountDataTableList.get(0).getOpeningDeposit(),account.getOpeningBalance(), "Account opening deposit mismatch after creating account");
        assertEquals(accountDataTableList.get(0).getOwnerTypeCode(),account.getOwnershipType().getCode(), "Account ownership code mismatch after creating account");
        assertEquals(accountDataTableList.get(0).getAccountStandingName(),account.getAccountStanding().getName(), "Account standing name mismatch after creating account");

        if (accountDataTableList.get(0).getAccountTypeCode().equalsIgnoreCase("ICK")) {
            AccountType accountType = AccountType.createDefaultICKAccountTypeModel();
            assertEquals(accountType.getName(),account.getAccountType().getName(), "ICK Account type Name mismatch after creating account");
            assertEquals(accountType.getId(),account.getAccountType().getId(), "ICK Account type ID mismatch after creating account");
            assertEquals(accountType.getCategory(),account.getAccountType().getCategory(), "ICK Account type Category mismatch after creating account");
            assertEquals(accountType.getInterestRate(),account.getAccountType().getInterestRate(), "ICK Account type Interest Rate mismatch after creating account");
            assertEquals(accountType.getMinDeposit(),account.getAccountType().getMinDeposit(), "ICK Account type Min Deposit mismatch after creating account");
            assertEquals(accountType.getOverdraftLimit(),account.getAccountType().getOverdraftLimit(), "ICK Account type Overdraft Limit mismatch after creating account");
            assertEquals(accountType.getOverdraftFee(),account.getAccountType().getOverdraftFee(), "ICK Account type Overdraft Fee mismatch after creating account");
        }
    }
    @Then("the information is saved in the Db as follows:")
    public void the_information_is_saved_in_the_db_as_follows(List<AccountDataTable> expectedResultList) {
        AccountDataTable expectedResultAccount = expectedResultList.get(0);

        List<Map<String,Object>> dbResultList = DBUtils.runSQLSelectQuery("select * from account where owner_id = " + db_userAccountSteps.userId);
        Map<String,Object> actualResultMap = dbResultList.get(0);

        assertEquals(expectedResultAccount.getAccountName(),actualResultMap.get("name"), "DB Account Name mismatch");
//        assertEquals(expectedResultAccount.getAccountTypeCode(),actualResultMap.get("account_type_id"), "DB Account Type Code mismatch");
        assertEquals(expectedResultAccount.getOpeningDeposit(),String.valueOf(actualResultMap.get("opening_balance")), "DB Account Deposit Amount mismatch");
//        assertEquals(expectedResultAccount.getOwnerTypeCode(),actualResultMap.get("ownership_type_id"), "DB Ownership Code mismatch");
    }

    @When("the user tries to create a new banking account with invalid credentials:")
    public void the_user_tries_to_create_a_new_banking_account_with_invalid_credentials(List<AccountDataTable> accountDataTableList) throws JsonProcessingException {
        String invalidAccount = objectMapper.writeValueAsString(accountDataTableList.get(0));
        invalidAccountResponse = RestHttpRequest.sendPostRequestWithPathParams("id",db_userAccountSteps.userId,"user/{id}/account",invalidAccount);

    }
    @Then("an {string} error message is displayed")
    public void a_error_message_is_displayed(String expectedErrorMessage) throws JsonProcessingException {
        String actualErrorMessage = "";
        boolean containsStatusCode = true;

        // depending on the case, the HTTP response is either a JSON object with an available status code OR an array with a single error message
        try {
            actualErrorMessage = invalidAccountResponse.jsonPath().getString("status") + " " + invalidAccountResponse.jsonPath().getString("error");
        } catch (IllegalArgumentException e) {
            containsStatusCode = false;
        }

        if (containsStatusCode) {
            assertEquals(expectedErrorMessage, actualErrorMessage, "Status code or error message mismatch");
        } else {
            String[] jsonResponseArray = objectMapper.readValue(invalidAccountResponse.asString(), String[].class);
            actualErrorMessage = jsonResponseArray[0];
            assertEquals(expectedErrorMessage,actualErrorMessage, "Error Message mismatch");
        }

    }
    @Then("the account {string} is not saved in the DB")
    public void the_information_is_not_saved_in_the_db(String accountName) {
        assertFalse(DBUtils.existsInTheDB("select * from account where name = '" + accountName + "'"));
    }




}
