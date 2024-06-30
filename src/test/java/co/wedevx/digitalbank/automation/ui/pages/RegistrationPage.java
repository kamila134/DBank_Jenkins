package co.wedevx.digitalbank.automation.ui.pages;

import co.wedevx.digitalbank.automation.ui.utils.MockData;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.cucumber.java.eo.Se;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegistrationPage extends BasePage{

    private static String username = null;
    private static String password = null;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        RegistrationPage.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        RegistrationPage.password = password;
    }

    MockData mockData = new MockData();

    @FindBy(xpath = "//a[contains(text(),'Sign')]")
    private WebElement signUpLink;

    @FindBy(id = "title")
    private WebElement titleDropdownTxtBox;

    @FindBy(id = "firstName")
    private WebElement firstNameTxtBox;

    @FindBy(id = "lastName")
    private WebElement lastNameTxtBox;

    @FindBy(xpath = "//input[@value='M']")
    private WebElement maleGenderRadioBtn;

    @FindBy(xpath = "//input[@value='F']")
    private WebElement femaleGenderRadioBtn;

    @FindBy(id = "dob")
    private WebElement dateOfBirthTxtBox;

    @FindBy(id = "ssn")
    private WebElement ssnTxtBox;

    @FindBy(id = "emailAddress")
    private WebElement emailAddressTxtBox;

    @FindBy(id = "password")
    private WebElement passwordTxtBox;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordTxtBox;

    @FindBy(xpath = "//button")
    private WebElement nextBtn;

    @FindBy(id = "address")
    private WebElement addressTxtBox;

    @FindBy(id = "locality")
    private WebElement localityTxtBox;

    @FindBy(id = "region")
    private WebElement regionTxtBox;

    @FindBy(id = "postalCode")
    private WebElement postalCodeTxtBox;

    @FindBy(id = "country")
    private WebElement countryTxtBox;

    @FindBy(id = "homePhone")
    private WebElement homePhoneTxtBox;

    @FindBy(xpath = "//input[@type='checkbox']/..")
    private WebElement agreeTermsCheckbox;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-flat m-b-30 m-t-30']")
    private WebElement registerBtn;

    //action methods
    public void registerUser(List<Map<String, String>> registrationDetailsListOfMaps) throws InterruptedException {
        Map<String, String> firstRow = registrationDetailsListOfMaps.get(0);

        Select selectTitle = new Select(titleDropdownTxtBox);
        selectTitle.selectByVisibleText(firstRow.get("title"));
        firstNameTxtBox.sendKeys(firstRow.get("firstName"));
        lastNameTxtBox.sendKeys(firstRow.get("lastName"));
        if (firstRow.get("gender").equalsIgnoreCase("F")) {
            femaleGenderRadioBtn.click();
        } else if (firstRow.get("gender").equalsIgnoreCase("M")) {
            maleGenderRadioBtn.click();
        }
        dateOfBirthTxtBox.sendKeys(firstRow.get("dateOfBirth"));

//        Map<String, String> randomNameAndEmailAmp = mockData.generateRandomNameAndEmail();

        ssnTxtBox.sendKeys(firstRow.get("ssn"));
        emailAddressTxtBox.sendKeys(firstRow.get("email"));

        passwordTxtBox.sendKeys(firstRow.get("password"));
        confirmPasswordTxtBox.sendKeys(firstRow.get("password"));
        nextBtn.submit();
        addressTxtBox.sendKeys(firstRow.get("address"));
        localityTxtBox.sendKeys(firstRow.get("locality"));
        regionTxtBox.sendKeys(firstRow.get("region"));
        countryTxtBox.sendKeys(firstRow.get("country"));
        postalCodeTxtBox.sendKeys(firstRow.get("postalCode"));
        homePhoneTxtBox.sendKeys(firstRow.get("homePhone"));
        agreeTermsCheckbox.click();
        registerBtn.click();

        username = firstRow.get("email");
        password = firstRow.get("password");
    }
}




//    public void registerRandomUser(){
//        driver.get("https://dbank-qa.wedevx.co/bank/login");
//
//        FakeValuesService fakeValuesService = new FakeValuesService(
//                new Locale("en-US"), new RandomService());
//
//
//        String title = new Faker().name().title();
//        String firstName = new Faker().name().firstName();
//        String lastName = new Faker().name().lastName();
//        String[] genderOptions = {"M","F"};
//        int index = new Faker().random().nextInt(genderOptions.length);
//        String gender = genderOptions[index];
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//        String dob = sdf.format(new Faker().date().birthday());
//        String ssn = new Faker().idNumber().ssnValid();
//        String email = fakeValuesService.bothify(firstName + "_##?@yahoo.com");
//        String pw = new Faker().internet().password(8,10,true);
//        String address = new Faker().address().streetAddress();
//        String localityCity = new Faker().address().city();
//        String regionState = new Faker().address().state();
//        String postalCode = new Faker().address().zipCode();
//        String country = new Faker().address().country();
//        String homePhone = String.format("",new Faker().phoneNumber().cellPhone());
////        Select selectTitle = new Select(titleDropdownTxtBox);
////
////        selectTitle.selectByVisibleText(title);
//        signUpLink.click();
//        firstNameTxtBox.sendKeys(firstName);
//        lastNameTxtBox.sendKeys(lastName);
//        if (gender.equals("F")) {
//            femaleGenderRadioBtn.click();
//        } else {
//            maleGenderRadioBtn.click();
//        }
//        dateOfBirthTxtBox.sendKeys(dob);
//        ssnTxtBox.sendKeys(ssn);
//        emailAddressTxtBox.sendKeys(email);
//        passwordTxtBox.sendKeys(pw);
//        confirmPasswordTxtBox.sendKeys(pw);
//        nextBtn.submit();
//        addressTxtBox.sendKeys(address);
//        localityTxtBox.sendKeys(localityCity);
//        regionTxtBox.sendKeys(regionState);
//        countryTxtBox.sendKeys(country);
//        postalCodeTxtBox.sendKeys(postalCode);
//        homePhoneTxtBox.sendKeys(homePhone);
//        agreeTermsCheckbox.click();
//        registerBtn.submit();
//
//        System.out.println(firstName);
//        System.out.println(lastName);
//        System.out.println(gender);
//        System.out.println(dob);
//        System.out.println(ssn);
//        System.out.println(email);
//        System.out.println(pw);
//        System.out.println(address);
//        System.out.println(localityCity);
//        System.out.println(regionState);
//        System.out.println(postalCode);
//        System.out.println(country);
//        System.out.println(homePhone);
//    }





