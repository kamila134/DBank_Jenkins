package co.wedevx.digitalbank.automation.ui.utils;

import co.wedevx.digitalbank.automation.ui.pages.RegistrationPage;
import co.wedevx.digitalbank.automation.ui.pages.UpdateProfilePage;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;

public class ProfileUpdate {

    private WebDriver driver = Driver.getDriver();

    private String[] titles = {"Ms.", "Mrs.", "Mr."};
    private int titleIndex = new Random().nextInt(titles.length);
    private String title = titles[titleIndex];
    private String firstName = new Faker().name().firstName();
    private String lastName = new Faker().name().lastName();
    private String address = new Faker().address().streetAddress();
    private String localityCity = new Faker().address().city();
    private String regionState = new Faker().address().state();
    private String postalCode = new Faker().address().zipCode();
    private String country = new Faker().address().country();
    private String homePhone = new Faker().phoneNumber().cellPhone();

    public ProfileUpdate(String username, String password) {
        driver.get("https://dbank-qa.wedevx.co/bank/login");

        //logging in the account
        WebElement usernameTxtBox = driver.findElement(By.id("username"));
        WebElement passwordTxtBox = driver.findElement(By.id("password"));
        WebElement logInBtn = driver.findElement(By.id("submit"));
        usernameTxtBox.sendKeys(username);
        passwordTxtBox.sendKeys(password);
        logInBtn.submit();

        //using the "UpdateProfilePage" method
        UpdateProfilePage updateProfilePage = new UpdateProfilePage(driver);
        updateProfilePage.update_Profile(title, firstName,lastName,address,localityCity,regionState,country,postalCode,homePhone);
        System.out.println(toString());
    }

    public ProfileUpdate(){
        RegistrationPage rp = new RegistrationPage(driver);
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + "\nlastName: " + lastName
                + "\naddress: " + address + "\nlocality: " + localityCity
                + "\nregion: " + regionState + "\ncountry: " + country
                + "\npostal code: " + postalCode + "\nphone: " + homePhone;
    }
}
