package co.wedevx.digitalbank.automation.api.steps;

import co.wedevx.digitalbank.automation.api.utils.RestHttpRequest;
import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeleteAccountSteps {


    @When("the user deletes the account")
    public void the_user_deletes_banking_account(){
        Response accountDeletedResponse = RestHttpRequest.sendDeleteRequestWithPathParams("id",CreateAccountSteps.bankingAccountId,"account/{id}");
        accountDeletedResponse.then().statusCode(204);
    }

    @Then("the {string} banking account is no longer saved in the DB")
    public void the_acc_is_deleted_in_the_DB(String account)  {
        assertFalse(DBUtils.existsInTheDB("select * from account WHERE name = '" + account + "'"), "Account " + account + " hasn't been deleted in the DB");
    }
}
