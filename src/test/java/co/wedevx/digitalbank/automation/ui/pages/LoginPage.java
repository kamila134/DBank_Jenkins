package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage extends BaseMenuPage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "username")
    private WebElement usernameTxtBox;

    @FindBy(id = "password")
    private WebElement passwordTxtBox;

    @FindBy(id = "submit")
    private WebElement loginBtn;

    @FindBy(xpath = "//span[text()='Error']")
    protected List<WebElement> redErrorMessageList;

    public void logIn() {
        usernameTxtBox.clear();
        passwordTxtBox.clear();
        String existingUsername = RegistrationPage.getUsername();
        String existingPassword = RegistrationPage.getPassword();
        if (existingUsername!= null && existingPassword != null) {
            usernameTxtBox.sendKeys(existingUsername);
            passwordTxtBox.sendKeys(existingPassword);
        } else {
            existingUsername = "Peter101@gmail.com";
            existingPassword = "Peter123!";
            usernameTxtBox.sendKeys(existingUsername);
            passwordTxtBox.sendKeys(existingPassword);
        }
        System.out.println(existingUsername);
        System.out.println(existingPassword);
        loginBtn.submit();
    }

    public void logIn(String existingUsername, String existingPassword) {
        usernameTxtBox.clear();
        passwordTxtBox.clear();
        if (existingUsername!= null && existingPassword != null) {
            usernameTxtBox.sendKeys(existingUsername);
            passwordTxtBox.sendKeys(existingPassword);
        } else throw new RuntimeException("Login credentials cannot be null");

        loginBtn.submit();

        if (!redErrorMessageList.isEmpty()) throw new RuntimeException("Invalid credentials. User does not exist.");

    }


}
