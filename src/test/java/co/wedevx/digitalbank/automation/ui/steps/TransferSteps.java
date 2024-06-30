package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.models.BankingType;
import co.wedevx.digitalbank.automation.ui.pages.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferSteps {

    NewCheckingPage newCheckingPage = new NewCheckingPage(getDriver());
    NewSavingsPage newSavingsPage = new NewSavingsPage(getDriver());
    TransferPage transferPage = new TransferPage(getDriver());
    ViewCheckingPage viewCheckingPage = new ViewCheckingPage(getDriver());
    ViewSavingsPage viewSavingsPage = new ViewSavingsPage(getDriver());
    LocalDate today = LocalDate.now();

    private String nonExistentAccountException;


    @When("the user makes the following transfer:")
    public void the_user_makes_the_following_transfer(List<Map<String,String>> transferDetailsListOfMaps) {
        Map<String,String> firstRowTransfer = transferDetailsListOfMaps.get(0);
        transferPage.transferBetweenAccounts(firstRowTransfer.get("fromAccount"), firstRowTransfer.get("toAccount"), firstRowTransfer.get("transferAmount"));
    }

    @Then("the accounts' details are as follows:")
    public void the_history_table_details_are_as_follows_with_today_s_date(List<Map<String,String>> historyTableList) {
        Map<String,String> firstTransactionMap = historyTableList.get(0);

        //asserting balance on the account card - transferor
        switch (firstTransactionMap.get("transferorType").toLowerCase()) {
            case "checking":
                String transferorCheckingCardBalance = viewCheckingPage.getChqAccountCardBalance(firstTransactionMap.get("transferorAccountName"));
                assertEquals(firstTransactionMap.get("transferorBalance"), transferorCheckingCardBalance, "Transferor checking card balance mismatch");

                Map<String,String> checkingHistoryTable = viewCheckingPage.getChqTableLatestTransactionDetails(firstTransactionMap.get("transferorAccountName"));
                assertEquals(String.valueOf(today), checkingHistoryTable.get("date"), "Transferor table Date mismatch");
                assertTrue(checkingHistoryTable.get("description").contains("Transfer to"), "Transferor table Description mismatch");
                assertEquals(firstTransactionMap.get("transferorBalance"), checkingHistoryTable.get("balance"), "Transferor table Balance mismatch");
                break;
            case "savings":
                Map<String,String> transferorSavingsCard = viewSavingsPage.getSvgAccountCardDetails(firstTransactionMap.get("transferorAccountName"));
                assertEquals(firstTransactionMap.get("transferorBalance"), transferorSavingsCard.get("balance"), "Transferor savings card balance mismatch");

                Map<String,String> savingsHistoryTable = viewSavingsPage.getSvgTableLatestTransactionDetails(firstTransactionMap.get("transferorAccountName"));
                assertEquals(String.valueOf(today), savingsHistoryTable.get("date"), "Transferor table Date mismatch");
                assertTrue(savingsHistoryTable.get("description").contains("Transfer to"), "Transferor table Description mismatch");
                assertEquals(firstTransactionMap.get("transferorBalance"), savingsHistoryTable.get("balance"), "Transferor table Balance mismatch");
                break;
            default:
                throw new NoSuchElementException("Wrong Banking Type option. Only supports Checking and Savings.");
        }

        //asserting balance on the account card - transferee
        switch (firstTransactionMap.get("transfereeType").toLowerCase()) {
            case "checking":
                String transfereeCheckingCardBalance = viewCheckingPage.getChqAccountCardBalance(firstTransactionMap.get("transfereeAccountName"));
                assertEquals(firstTransactionMap.get("transfereeBalance"), transfereeCheckingCardBalance, "Transferee checking card balance mismatch");

                Map<String,String> checkingHistoryTable = viewCheckingPage.getChqTableLatestTransactionDetails(firstTransactionMap.get("transfereeAccountName"));
                assertEquals(String.valueOf(today), checkingHistoryTable.get("date"), "Transferee table Date mismatch");
                assertTrue(checkingHistoryTable.get("description").contains("Transfer from"), "Transferee table Description mismatch");
                assertEquals(firstTransactionMap.get("transfereeBalance"), checkingHistoryTable.get("balance"), "Transferee table Balance mismatch");
                break;
            case "savings":
                Map<String,String> transfereeSavingsCard = viewSavingsPage.getSvgAccountCardDetails(firstTransactionMap.get("transfereeAccountName"));
                assertEquals(firstTransactionMap.get("transfereeBalance"), transfereeSavingsCard.get("balance"), "Transferee savings card balance mismatch");

                Map<String,String> savingsHistoryTable = viewSavingsPage.getSvgTableLatestTransactionDetails(firstTransactionMap.get("transfereeAccountName"));
                assertEquals(String.valueOf(today), savingsHistoryTable.get("date"), "Transferee table Date mismatch");
                assertTrue(savingsHistoryTable.get("description").contains("Transfer from"), "Transferee table Description mismatch");
                assertEquals(firstTransactionMap.get("transfereeBalance"), savingsHistoryTable.get("balance"), "Transferee table Balance mismatch");
                break;
            default:
                throw new NoSuchElementException("Wrong Banking Type option. Only supports Checking and Savings.");
        }

    }

    @Then("a {string} field displays a {string} error message")
    public void transfer_error_message_is_displayed(String emptyFieldName, String expectedErrorMessage) {

        if (emptyFieldName.equalsIgnoreCase("fromAccount")) {
            assertEquals(expectedErrorMessage,transferPage.getFromAccountDropdownErrorMessage(), "Field error messages mismatch");
        } else if (emptyFieldName.equalsIgnoreCase("toAccount")){
            assertEquals(expectedErrorMessage,transferPage.getToAccountDropdownErrorMessage(), "Field error messages mismatch");
        } else if (emptyFieldName.equalsIgnoreCase("transferAmount")){
            assertEquals(expectedErrorMessage,transferPage.getTransferAmountTxtBoxErrorMessage(), "Field error messages mismatch");
        } else if (emptyFieldName.equalsIgnoreCase("red error message")){
            assertEquals(expectedErrorMessage,transferPage.getFullRedErrorMessage(), "Red error message messages mismatch");
        }
    }

    @When("the user makes the following transfer with a non-existent account:")
    public void transfer_with_non_existent_account(List<Map<String,String>> transferDetailsListOfMaps) {
        Map<String,String> firstRowTransfer = transferDetailsListOfMaps.get(0);
        nonExistentAccountException = transferPage.transferBetweenNonExistentAccounts(firstRowTransfer.get("fromAccount"), firstRowTransfer.get("toAccount"));
        nonExistentAccountException = nonExistentAccountException.substring(10,nonExistentAccountException.indexOf(":"));
    }

    @Then("the system throws a {string} exception")
    public void throws_exception(String expectedException) {
        assertEquals(expectedException, nonExistentAccountException, "Non-existent account exception mismatch");
    }


}
