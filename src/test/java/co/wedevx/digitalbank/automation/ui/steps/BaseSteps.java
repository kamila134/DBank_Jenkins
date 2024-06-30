package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.models.BankingType;
import co.wedevx.digitalbank.automation.ui.pages.LoginPage;
import co.wedevx.digitalbank.automation.ui.pages.NewCheckingPage;
import co.wedevx.digitalbank.automation.ui.pages.NewSavingsPage;
import co.wedevx.digitalbank.automation.ui.pages.RegistrationPage;
import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseSteps {
    private static String accountName;
    private static String accountName2;
    private static String bankingType;

    RegistrationPage registrationPage = new RegistrationPage(getDriver());
    LoginPage loginPage = new LoginPage(getDriver());

    NewCheckingPage newCheckingPage = new NewCheckingPage(getDriver());
    NewSavingsPage newSavingsPage = new NewSavingsPage(getDriver());

    public static String getAccountName() {
        return accountName;
    }
    public static String getAccountName2() {
        return accountName2;
    }

    public static String getBankingType() {
        return bankingType;
    }

    @Given("the user registers a new account as follows with mock email and ssn:")
    public void the_user_registers_a_new_account_as_follows_with_mock_email_and_ssn(List<Map<String,String>> registrationDetailsListOfMaps) throws InterruptedException {
        registrationPage.registerUser(registrationDetailsListOfMaps);
    }

    @Given("the user signs in with their credentials")
    public void the_user_signs_in() {
        loginPage.logIn();
    }

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        loginPage.logIn();
    }


    @When("a user opens a new account as follows:")
    public void a_user_opens_a_new_account_as_follows(List<BankingType> accountsList) {
        BankingType account = accountsList.get(0);
        if (account.getBankingType().equalsIgnoreCase("Checking")) {
            newCheckingPage.openCheckingAccount(account);
        } else if (account.getBankingType().equalsIgnoreCase("Savings")) {
            newSavingsPage.openSavingsAccount(account);
        } else {
            throw new NoSuchElementException("Wrong Banking Type option. Only supports Checking and Savings.");
        }
        accountName = account.getAccountName();
        BaseSteps.bankingType = account.getBankingType();
    }

    @When("a user opens new accounts as follows:")
    public void a_user_opens_new_accounts_as_follows(List<BankingType> accountsList) {
        int i = 0;
        for (BankingType account: accountsList) {
            i++;
            if (account.getBankingType().equalsIgnoreCase("Checking")) {
                newCheckingPage.openCheckingAccount(account);
            } else if (account.getBankingType().equalsIgnoreCase("Savings")) {
                newSavingsPage.openSavingsAccount(account);
            } else {
                throw new NoSuchElementException("Wrong Banking Type option. Only supports Checking and Savings.");
            }
            if (i == 1) {
                accountName = account.getAccountName();
            } else if (i == 2) {
                accountName2 = account.getAccountName();
            }

        }
    }


    @When("a green {string} success message is displayed")
    public void the_user_makes_a_deposit_of(String expectedSuccessMessage) {
        String actualSuccessMessage = newCheckingPage.getAccountCreationSuccessMessage();

        assertEquals(expectedSuccessMessage,actualSuccessMessage, "Account wasn't created");
    }

}
