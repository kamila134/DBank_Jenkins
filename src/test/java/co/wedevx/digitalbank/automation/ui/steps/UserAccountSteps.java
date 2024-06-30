package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.utils.DBUtils;
import co.wedevx.digitalbank.automation.ui.utils.Driver;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import co.wedevx.digitalbank.automation.ui.models.AccountDetails;
import co.wedevx.digitalbank.automation.ui.models.AccountUpdate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import co.wedevx.digitalbank.automation.ui.pages.RegistrationPage;
import co.wedevx.digitalbank.automation.ui.pages.UpdateProfilePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAccountSteps {

    WebDriver driver = Driver.getDriver();
    RegistrationPage registrationPage = new RegistrationPage(driver);
    UpdateProfilePage updateProfilePage = new UpdateProfilePage(driver);


//    @Given("the user is on the login page")
//    public void the_user_is_on_the_login_page() {
//        driver.get("https://dbank-qa.wedevx.co/bank/login");
//
//    }

    @When("the user registers a new account as follows:")
    public void the_user_registers_a_new_account(List<Map<String,String>> accountDetailsList) throws InterruptedException {

        Map<String,String> firstAccount = accountDetailsList.get(0);
        registrationPage.registerUser(accountDetailsList);
    }

    @Then("a new account is created")
    public void a_new_account_is_created() {
        WebElement successMessage = driver.findElement(By.xpath("//span[text()='Success']"));
        System.out.println(successMessage.getText());
        assertEquals("Success", successMessage.getText());

    }

    // updating profile:

//    @Given("the user is signed in as {string} and {string}")
//    public void the_user_is_signed_in(String email, String password) {
//        driver.get("https://dbank-qa.wedevx.co/bank/login");
//        WebElement usernameTxtBox = driver.findElement(By.id("username"));
//        usernameTxtBox.clear();
//        usernameTxtBox.sendKeys(email);
//
//        WebElement pwTxtBox = driver.findElement(By.id("password"));
//        pwTxtBox.clear();
//        pwTxtBox.sendKeys(password);
//
//        WebElement signInBtn = driver.findElement(By.id("submit"));
//        signInBtn.submit();
//    }

    @When("a user updates the profile as follows:")
    public void a_user_updates_the_profile_as_follows(List<AccountUpdate> accountUpdateList) {
        AccountUpdate firstAccount = accountUpdateList.get(0);

        updateProfilePage.update_Profile(firstAccount.getTitle(), firstAccount.getFirstName(), firstAccount.getLastName(), firstAccount.getAddress(), firstAccount.getLocality(),
                firstAccount.getRegion(), firstAccount.getCountry(), firstAccount.getPostalCode(),
                firstAccount.getHomePhone());
    }
    @Then("the profile is updated")
    public void the_profile_is_updated() {
        WebElement successMsgTxtBox = driver.findElement(By.xpath("//span[text()='Success']"));
        WebElement successMsgTxtBox2 = successMsgTxtBox.findElement(By.xpath("./following-sibling::span"));
        assertEquals("Success Profile Updated Successfully.",successMsgTxtBox.getText() + " " + successMsgTxtBox2.getText());
    }


}

















