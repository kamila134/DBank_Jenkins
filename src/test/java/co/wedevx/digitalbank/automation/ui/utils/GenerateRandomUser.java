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

public class GenerateRandomUser {

    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-US"), new RandomService());

    private String[] titles = {"Ms.", "Mrs.", "Mr."};
    private int titleIndex = new Random().nextInt(titles.length);
    private String title = titles[titleIndex];
    private String firstName = new Faker().name().firstName();
    private String lastName = new Faker().name().lastName();
    private String[] genderOptions = {"M","F"};
    private int genderIndex = new Faker().random().nextInt(genderOptions.length);
    private String gender = genderOptions[genderIndex];
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private String dob = sdf.format(new Faker().date().birthday());
    private String ssn = new Faker().idNumber().ssnValid();
    private String email = fakeValuesService.bothify(firstName + "_##?@yahoo.com");
    private String pw = new Faker().internet().password(8,10,true);
    private String address = new Faker().address().streetAddress();
    private String localityCity = new Faker().address().city();
    private String regionState = new Faker().address().state();
    private String postalCode = new Faker().address().zipCode();
    private String country = new Faker().address().country();
    private String homePhone = new Faker().phoneNumber().cellPhone();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocalityCity() {
        return localityCity;
    }

    public void setLocalityCity(String localityCity) {
        this.localityCity = localityCity;
    }

    public String getRegionState() {
        return regionState;
    }

    public void setRegionState(String regionState) {
        this.regionState = regionState;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public GenerateRandomUser(WebDriver driver, String username, String password) {
        driver.get("https://dbank-qa.wedevx.co/bank/login");
        WebElement usernameTxtBox = driver.findElement(By.id("username"));
        WebElement passwordTxtBox = driver.findElement(By.id("password"));
        WebElement logInBtn = driver.findElement(By.id("submit"));
        usernameTxtBox.sendKeys(username);
        passwordTxtBox.sendKeys(password);
        logInBtn.submit();

        UpdateProfilePage updateProfilePage = new UpdateProfilePage(driver);
        updateProfilePage.update_Profile(title, firstName,lastName,address,localityCity,regionState,country,postalCode,homePhone);
        System.out.println(toString());
    }

    public GenerateRandomUser(WebDriver driver) {
        driver.get("https://dbank-qa.wedevx.co/bank/login");
        RegistrationPage rp = new RegistrationPage(driver);
//        rp.registerUser(title, firstName,lastName,gender,dob,pw,address,localityCity,regionState,country,postalCode,homePhone);

//        WebElement usernameTxtBox = driver.findElement(By.id("username"));
//        WebElement passwordTxtBox = driver.findElement(By.id("password"));
//        WebElement logInBtn = driver.findElement(By.id("submit"));
//        usernameTxtBox.sendKeys(email);
//        passwordTxtBox.sendKeys(pw);
//        logInBtn.submit();
//
//        WebElement avatarBtn = driver.findElement(By.xpath("//img[contains(@alt,'Avatar')]"));
//        WebElement myProfile = driver.findElement(By.xpath("//img[contains(@alt,'Avatar')]/../../div/a[1]"));

        UpdateProfilePage up = new UpdateProfilePage(driver);
        up.update_Profile(title, firstName,lastName,address,localityCity,regionState,country,postalCode,homePhone);
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + "\nlastName: " + lastName
                + "\naddress: " + address + "\nlocality: " + localityCity
                + "\nregion: " + regionState + "\ncountry: " + country
                + "\npostal code: " + postalCode + "\nphone: " + homePhone;
    }

}
