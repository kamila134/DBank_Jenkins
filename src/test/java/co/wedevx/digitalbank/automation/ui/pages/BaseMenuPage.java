package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BaseMenuPage extends BasePage{

    public BaseMenuPage(WebDriver driver) {
        super(driver);
    }

    // checking options
    @FindBy(id="checking-menu")
    protected WebElement checkingMenuBtn;

    @FindBy(id="new-checking-menu-item")
    protected WebElement newCheckingMenuBtn;


    @FindBy(id ="view-checking-menu-item")
    protected WebElement viewCheckingBtn;

    @FindBy(xpath = "//span[text()='Confirmation']")
    protected WebElement successMessage;

    //savings option
    @FindBy(id ="savings-menu")
    protected WebElement savingsMenuBtn;
    @FindBy(id="new-savings-menu-item")
    protected WebElement newSavingsMenuBtn;
    @FindBy(id ="view-savings-menu-item")
    protected WebElement viewSavingsBtn;

    //transactions:
    @FindBy(id="deposit-menu-item")
    protected WebElement depositMenuBtn;
    @FindBy(id="withdraw-menu-item")
    protected WebElement withdrawMenuBtn;

    @FindBy(id="transfer-menu-item")
    protected WebElement transferMenuBtn;


    @FindBy(xpath = "//img[contains(@alt,'Avatar')]")
    protected WebElement avatarBtn;

    @FindBy(xpath = "//img[contains(@alt,'Avatar')]/../../div/a[1]")
    protected WebElement myProfileBtn;


    @FindBy(id="external-accounts-menu")
    protected WebElement externalAccountMenuBtn;

    @FindBy(id="link-external-menu-item")
    protected WebElement linkExternalAccountBtn;

    @FindBy(xpath = "//span[text()='Error']")
    protected WebElement redErrorMessage;

    @FindBy(xpath = "//span[text()='Error']/following-sibling::span")
    protected WebElement fullRedErrorMessage;

    @FindBy(id = "visa-transfer-menu-item")
    protected WebElement visaTransferBtn;

    @FindBy(id = "menuToggle")
    protected WebElement menuToggleBtn;

    @FindBy(id = "notification")
    protected WebElement notificationBell;

    @FindBy(xpath = "//div[@class='dropdown-menu show']/a")
    protected List<WebElement> notificationItems;



    protected void selectAccount(Select selectAccount, String accountName) {
        List<WebElement> accountOptions = selectAccount.getOptions();
        boolean optionSelected = false;
        for (WebElement option: accountOptions) {
            String optionVisibleText = option.getText();
            if (optionVisibleText.contains(accountName)) {
                selectAccount.selectByVisibleText(optionVisibleText);
                optionSelected = true;
                break;
            }
        } if (!optionSelected) {
            List<String> availableOptions = new ArrayList<>();
            for (int i = 1; i < accountOptions.size(); i++) {
                availableOptions.add(accountOptions.get(i).getText());
            }
            throw new NoSuchElementException("No such account found. Please select from the following: " + availableOptions.toString());
        }
    }

    public String getAccountCreationSuccessMessage(){
        return successMessage.getText();
    }

    public int checkNotificationItems(){
        notificationBell.click();
        return notificationItems.size();
    }

}
