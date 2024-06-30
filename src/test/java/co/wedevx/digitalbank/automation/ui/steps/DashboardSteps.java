package co.wedevx.digitalbank.automation.ui.steps;

import co.wedevx.digitalbank.automation.ui.pages.LoginPage;
import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static co.wedevx.digitalbank.automation.ui.utils.Driver.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardSteps {

    LoginPage loginPage = new LoginPage(getDriver());
    int notificationDropdownItems;

    @Given("the user logs in with existing credentials")
    public void the_user_logs_in_with_existing_credentials(List<Map<String,String>> credentialsList) {
        getDriver().get(ConfigReader.getPropertiesValue("digitalbank.general.login.url"));
        Map<String,String> credentialsMap = credentialsList.get(0);
        loginPage.logIn(credentialsMap.get("email"),credentialsMap.get("password"));
        System.out.println("Success");


    }
    @When("the user clicks on the Notification Bell")
    public void notification_bell() {
        notificationDropdownItems = loginPage.checkNotificationItems();
        System.out.println("Success");

    }
    @Then("a dropdown with notifications opens")
    public void notification_dropdown() {
        WebElement bellNotificationItems = getDriver().findElement(By.xpath("//button[@id='notification']/span"));
        int bellNotificationItemsNumber = Integer.parseInt(bellNotificationItems.getText());

        assertEquals(bellNotificationItemsNumber,notificationDropdownItems);

        System.out.println("Success");
    }
}
