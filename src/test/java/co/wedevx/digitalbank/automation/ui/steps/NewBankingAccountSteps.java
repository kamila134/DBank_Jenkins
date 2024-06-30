package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.pages.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewBankingAccountSteps{

    private String accountName = BaseSteps.getAccountName();
    private final String bankingType = BaseSteps.getBankingType();
    DepositPage depositPage = new DepositPage(getDriver());
    ViewCheckingPage viewCheckingPage = new ViewCheckingPage(getDriver());
    ViewSavingsPage viewSavingsPage = new ViewSavingsPage(getDriver());
    LocalDate today = LocalDate.now();




    @When("the user makes a deposit of {double}")
    public void the_user_makes_a_deposit_of(Double depositAmount) {
        depositPage.makeADeposit(String.valueOf(depositAmount));
    }

    @Then("the user's account balance is {string}")
    public void the_user_s_new_account_balance_is(String expectedAccountBalance) {
        String actualBalance = "";
        if (bankingType.equalsIgnoreCase("checking")) {
            actualBalance = viewCheckingPage.getChqAccountCardBalance(accountName);
        } else if (bankingType.equalsIgnoreCase("savings")) {
            actualBalance = viewSavingsPage.getSvgAccountCardBalance(accountName);
        }
        assertEquals(expectedAccountBalance, actualBalance, "Card balance mismatch");


        Map<String,String> tableMap = new HashMap<>();
        if (bankingType.equalsIgnoreCase("checking")) {
            tableMap = viewCheckingPage.getChqTableLatestTransactionDetails(accountName);
        } else if (bankingType.equalsIgnoreCase("savings")) {
            tableMap = viewSavingsPage.getSvgTableLatestTransactionDetails(accountName);
        }

        String transactionDate = tableMap.get("date");
        String transactionDescription = tableMap.get("description");
        String tableAccountBalance = tableMap.get("balance");

        assertEquals(String.valueOf(today),transactionDate, "Transaction table date mismatch");
        assertEquals(expectedAccountBalance,tableAccountBalance, "Transaction account balance mismatch");

//        if (transactionType.equalsIgnoreCase("deposit")) {
//            assertEquals("Online Deposit",transactionDescription, "Transaction table description mismatch");
//        } else if (transactionType.equalsIgnoreCase("withdrawal")) {
//            assertEquals("Online Withdrawl",transactionDescription, "Transaction table description mismatch");
//        }
    }
    
    //Scenario # - deposit Reset button

    @When("the user makes a ${double} deposit and clicks the {string} button")
    public void the_user_makes_a_deposit_and_clicks_the_button(double depositAmount, String string) {
        depositPage.makeADepositAndReset(depositAmount);
    }
    @Then("the deposit fields are cleared and a deposit is not made")
    public void the_deposit_fields_are_cleared() {
        String accountNameDropdown = depositPage.getAccountNameDropdownValue();
        assertTrue(accountNameDropdown.contains("Select Account"), "Account name dropdown wasn't reset");

        String depositAmountTxtBox = depositPage.getTransactionAmountTxtBoxValue();
        assertTrue(depositAmountTxtBox.isEmpty(), "Deposit amount Txt box wasn't reset");
    }

    //

    @When("a user makes a deposit to new checking account but enters an invalid {string} deposit amount")
    public void deposit_with_invalid_amount(String invalidAmount) {
        depositPage.makeADeposit(accountName, invalidAmount);
    }

    @Then("a red {string} error message is displayed")
    public void red_error_message(String expectedErrorMessage) {
        String actualErrorMessage = depositPage.getRedErrorMessage();
        assertEquals(expectedErrorMessage,actualErrorMessage,"Red error messages mismatch");
    }

    //Negative deposits

    @When("a user makes a deposit with the following data:")
    public void a_user_makes_a_deposit_with_the_following_data(List<Map<String,String>> depositDetailsList) {
        Map<String,String> depositDetailsMap = depositDetailsList.get(0);
        depositPage.makeADeposit(depositDetailsMap.get("accountName"), depositDetailsMap.get("depositAmount"));
    }


    // Withdrawal -----

    WithdrawPage withdrawPage = new WithdrawPage(getDriver());

    @When("the user makes a withdrawal of ${string}")
    public void the_user_makes_a_withdrawal_of_$(String withdrawalAmount) {
        withdrawPage.makeAWithdrawal("",withdrawalAmount);
    }
    
}
