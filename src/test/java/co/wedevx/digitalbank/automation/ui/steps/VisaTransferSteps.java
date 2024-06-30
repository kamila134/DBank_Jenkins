package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.models.BankingType;
import co.wedevx.digitalbank.automation.ui.pages.NewCheckingPage;
import co.wedevx.digitalbank.automation.ui.pages.NewSavingsPage;
import co.wedevx.digitalbank.automation.ui.pages.VisaTransferPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.NoSuchElementException;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisaTransferSteps {

    NewCheckingPage newCheckingPage = new NewCheckingPage(getDriver());
    NewSavingsPage newSavingsPage = new NewSavingsPage(getDriver());
    VisaTransferPage visaTransferPage = new VisaTransferPage(getDriver());



    @When("the user makes a direct transfer of {string} to Visa")
    public void the_user_makes_the_following_visa_direct_transfer(String transferAmount) {
        visaTransferPage.transferToVisa(transferAmount);
    }

    @Then("the user encounters a {string} error message")
    public void the_user_encounters_a_error_message(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage,visaTransferPage.getRedErrorMessage(),"Error message mismatch");
    }
}
