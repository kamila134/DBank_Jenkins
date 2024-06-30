package co.wedevx.digitalbank.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class UpdateProfilePage extends BaseMenuPage{

    public UpdateProfilePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="title")
    private WebElement titleDropdownTxtBox;

    @FindBy(id="firstName")
    private WebElement firstNameTxtBox;

    @FindBy(id="lastName")
    private WebElement lastNameTxtBox;

    @FindBy(id="address")
    private WebElement addressTxtBox;

    @FindBy(id="locality")
    private WebElement localityTxtBox;

    @FindBy(id="region")
    private WebElement regionTxtBox;

    @FindBy(id="postalCode")
    private WebElement postalCodeTxtBox;

    @FindBy(id="country")
    private WebElement countryTxtBox;

    @FindBy(id="homePhone")
    private WebElement homePhoneTxtBox;


    @FindBy(xpath="//div[@class='card-footer']/button[@type='submit']")
    private WebElement submitBtn;

    //action methods
    public void update_Profile(String title, String firstName, String lastName, String address,
                             String locality, String region, String country, String postalCode, String homePhone){
        avatarBtn.click();
        myProfileBtn.click();

        Select selectTitle = new Select(titleDropdownTxtBox);
        selectTitle.selectByVisibleText(title);
        firstNameTxtBox.clear();
        firstNameTxtBox.sendKeys(firstName);
        lastNameTxtBox.clear();
        lastNameTxtBox.sendKeys(lastName);
        addressTxtBox.clear();
        addressTxtBox.sendKeys(address);
        localityTxtBox.clear();
        localityTxtBox.sendKeys(locality);
        regionTxtBox.clear();
        regionTxtBox.sendKeys(region);
        countryTxtBox.clear();
        countryTxtBox.sendKeys(country);
        postalCodeTxtBox.clear();
        postalCodeTxtBox.sendKeys(postalCode);
        homePhoneTxtBox.clear();
        homePhoneTxtBox.sendKeys(homePhone);
        submitBtn.submit();
    }

}
