package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.pages.ExternalAccountPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.*;

public class LinkExternalAccountSteps {

    ExternalAccountPage externalAccountPage = new ExternalAccountPage(getDriver());

//    @FindBy(xpath = "//span[text()='Error']/following-sibling::span")
//    private WebElement fullRedErrorMessage;


    @When("the user wants to link an external account")
    public void the_user_wants_to_link_an_external_account() {
        externalAccountPage.linkExternalAccount();
    }


    @Then("the user is displayed with an error {string} message")
    public void the_user_is_displayed_with_an_error_message(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage,externalAccountPage.getFullRedErrorMessage(), "Red Error messages mismatch");
    }

    @Given("the existing field {string} doesn't have any options")
    public void the_existing_field_does_not_have_any_options(String bankingProviderField) {
        assertEquals(1, externalAccountPage.getDropdownOptionsList().size());
    }

    @When("the user click on the Submit button")
    public void click_submit() {
        externalAccountPage.clickSubmit();
    }

    @Then("the user sees the {string} error message")
    public void the_user_is_not_able_to_link_an_external_account(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage,externalAccountPage.getDropdownErrorMessage(), "Dropdown error message mismatch");
    }
}
