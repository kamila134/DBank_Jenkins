package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.pages.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTableSteps {

    DepositPage depositPage = new DepositPage(getDriver());
    WithdrawPage withdrawPage = new WithdrawPage(getDriver());
    TransferPage transferPage = new TransferPage(getDriver());
    BaseViewAccountsPage baseViewAccountsPage = new BaseViewAccountsPage(getDriver());

    private int totalTransactionsNumber;

    @When("the user does the following transactions:")

    public void the_user_does_the_following_transactions(List<Map<String, String>> transactionsList) {
        for (Map<String, String> transactionsMap : transactionsList) {
            if (transactionsMap.get("transactionType").equalsIgnoreCase("deposit")) {
                for (int i = 1; i <= Integer.parseInt(transactionsMap.get("numberOfTransactions")); i++) {
                    depositPage.makeADeposit(transactionsMap.get("amount"));
                }

            } else if (transactionsMap.get("transactionType").equalsIgnoreCase("withdrawal")) {
                for (int i = 1; i <= Integer.parseInt(transactionsMap.get("numberOfTransactions")); i++) {
                    withdrawPage.makeAWithdrawal(transactionsMap.get("amount"));
                }
            } else if (transactionsMap.get("transactionType").equalsIgnoreCase("transfer")) {
                for (int i = 1; i <= Integer.parseInt(transactionsMap.get("numberOfTransactions")); i++) {
                    transferPage.transferBetweenAccounts(BaseSteps.getAccountName(),BaseSteps.getAccountName2(),transactionsMap.get("amount"));
                }
            }
                String actualAccountCardBalance = baseViewAccountsPage.getAccountCardBalance();
                assertEquals(transactionsMap.get("accountBalance"),actualAccountCardBalance, "Card acc balance mismatch");

                String actualTableAccBalance = baseViewAccountsPage.getTableLatestTransactionDetails().get("balance");
                assertEquals(transactionsMap.get("accountBalance"),actualTableAccBalance, "Table acc balance mismatch");

                totalTransactionsNumber+=Integer.parseInt(transactionsMap.get("numberOfTransactions"));
        }
    }

    @Then("the transaction table is updated as follows including the initial deposit during account creation:")
    public void the_savings_transaction_table_is_updated_as_follows(List<Map<String,String>> tableDetailsList) {
        for (Map<String,String> tableDetailsMap: tableDetailsList) {
            baseViewAccountsPage.selectShowNumber(tableDetailsMap.get("showNumberOfTransactions"));

            int showNum = Integer.parseInt(tableDetailsMap.get("showNumberOfTransactions"));
            int totalNumOfPages = Integer.parseInt(tableDetailsMap.get("NumberOfPages"));
            assertEquals(totalNumOfPages, baseViewAccountsPage.getPageNumbers(), "Show " + showNum + ": Numbered pages amount mismatch");

            for (int i = 1; i <= totalNumOfPages; i++) {
                baseViewAccountsPage.clickPageNumber(i);

                if (i == totalNumOfPages) {
                    assertEquals(Integer.parseInt(tableDetailsMap.get("transactionsOnTheLastPage")),baseViewAccountsPage.getTransactionTableLength(),"Show " + showNum + ": Last page table length mismatch.");
                } else {
                    assertEquals(showNum,baseViewAccountsPage.getTransactionTableLength(), "Show " + showNum + ": Page " + i + " table length mismatch.");
                }

            }

            for (int i = totalNumOfPages; i > 1; i--) {
                assertEquals(i, baseViewAccountsPage.getActivePageNum(), "Show " + showNum + ": Active page num from Previous btn mismatch");
                baseViewAccountsPage.clickPreviousPageBtn();
            }

            for (int i = 1; i < totalNumOfPages; i++) {
                assertEquals(i, baseViewAccountsPage.getActivePageNum(), "Show " + showNum + ": Active page num from Next btn mismatch");
                baseViewAccountsPage.clickNextPageBtn();
            }

        }
    }

    // Testing search bar

    @When("uses the search bar to find specific transactions")
    public void uses_the_search_bar_to_find_specific_transactions() {
    }

    @Then("the the transaction table shows the following results including the initial deposit during account creation:")
    public void the_the_transaction_table_shows_the_following_results(List<Map<String, String>> searchItemsList) {
        for (Map<String, String> searchItemMap: searchItemsList) {
            String searchItem = searchItemMap.get("searchItem");
            int expectedNumberOfResults = Integer.parseInt(searchItemMap.get("TransactionsFound"));

            baseViewAccountsPage.inputIntoSearchBar(searchItem.toLowerCase());
            assertEquals(expectedNumberOfResults, baseViewAccountsPage.getTransactionTableLength(), searchItem+ " - Filtered table length mismatch - lowercase search");

            baseViewAccountsPage.clearSearchBar();
            baseViewAccountsPage.inputIntoSearchBar(searchItem.toUpperCase());
            assertEquals(expectedNumberOfResults, baseViewAccountsPage.getTransactionTableLength(), searchItem + " - Filtered table length mismatch - uppercase search");

            List<String> actualResultCellValues = baseViewAccountsPage.getAllTableCellValues();
            int actualNumOfFoundResults = 0;
            for (String cellValue: actualResultCellValues) {
                if (cellValue.toLowerCase().contains(searchItem.toLowerCase())) {
                    actualNumOfFoundResults++;
                }
            } assertEquals(expectedNumberOfResults,actualNumOfFoundResults, searchItem+ " - Table cell values mismatch");
            baseViewAccountsPage.clearSearchBar();
        }
    }


}

