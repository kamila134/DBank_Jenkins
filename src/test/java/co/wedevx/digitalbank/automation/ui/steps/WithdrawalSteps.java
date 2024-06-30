package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.models.BankingType;
import co.wedevx.digitalbank.automation.ui.pages.BaseViewAccountsPage;
import co.wedevx.digitalbank.automation.ui.pages.WithdrawPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawalSteps {

    WithdrawPage withdrawPage = new WithdrawPage(getDriver());

//    @When("the user makes a withdrawal of ${double}")
//    public void the_user_makes_a_withdrawal_of_$(Double withdrawalAmount) {
//        withdrawPage.makeValidWithdrawal(withdrawalAmount);
//    }


    @Given("the overdraft fee is ${int}")
    public void the_overdraft_fee_is_$(Integer int1) {

    }
    @When("the user makes a withdrawal with an amount of ${string}")
    public void the_user_makes_a_withdrawal_with_an_amount_of_$(String invalidDepositAmount) {
        withdrawPage.makeAWithdrawal("", invalidDepositAmount);
    }
    @Then("the user is displayed with a red {string} message")
    public void the_user_is_displayed_with_a_red_error_message(String expectedErrorMessage) {
        String actualErrorMessage = withdrawPage.getRedErrorMessage();
        assertEquals(expectedErrorMessage,actualErrorMessage, "Red error message mismatch");
    }


    // Negative - blank fields

    @When("the user makes an invalid withdrawal with an empty field")
    public void the_user_makes_a_withdrawal_but_leaves_the_field_empty(List<Map<String,String>> accountsList) {
        Map<String,String> firstRowAccountMap = accountsList.get(0);
        withdrawPage.makeAWithdrawal(firstRowAccountMap.get("accountName"),firstRowAccountMap.get("withdrawalAmount"));
    }

    @Then("the {string} field displays a {string} error message")
    public void an_error_message_is_displayed(String emptyFieldName, String expectedErrorMessage) {
        if (emptyFieldName.equalsIgnoreCase("accountName")) {
            assertEquals(expectedErrorMessage,WithdrawPage.getAccountNameErrorMessage(), "Field error messages mismatch");
        } else if (emptyFieldName.contains("Amount")){
            assertEquals(expectedErrorMessage,WithdrawPage.getTransactionAmountErrorMessage(), "Field error messages mismatch");
        }

    }


}
