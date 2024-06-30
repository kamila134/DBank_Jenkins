package co.wedevx.digitalbank.automation.ui.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserRegistration {
    static WebDriver driver = Driver.getDriver();

    public static void main(String[] args) {
        UserRegistration dbr  = new UserRegistration();

    }

    public void registerRandomUser(){
//        driver.get("https://dbank-qa.wedevx.co/bank/login");

        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-US"), new RandomService());


        String title = new Faker().name().title();
        String firstName = new Faker().name().firstName();
        String lastName = new Faker().name().lastName();
        String[] genderOptions = {"M","F"};
        int index = new Faker().random().nextInt(genderOptions.length);
        String gender = genderOptions[index];
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String dob = sdf.format(new Faker().date().birthday());
//        String ssn2 = fakeValuesService.bothify("###-##-####");
        String ssn = new Faker().idNumber().ssnValid();
        String email = fakeValuesService.bothify(firstName + "_##?@yahoo.com");
//        String email2 = new Faker().internet().emailAddress();
        String pw = new Faker().internet().password(8,10,true);
//        String pw2 = fakeValuesService.regexify("[A-z1-9]{10}");
        String address = new Faker().address().streetAddress();
        String localityCity = new Faker().address().city();
        String regionState = new Faker().address().state();
        String postalCode = new Faker().address().zipCode();
        String country = new Faker().address().country();
        String homePhone = String.format("",new Faker().phoneNumber().cellPhone());

    }

}

//    Title (Hint: Use Faker to generate "Mr.," "Mrs.," or "Ms." with Faker.name().title())
//    First name (Faker.name().firstName())
//    Last name (Faker.name().lastName())
//    Gender (Hint: Use Faker to randomly select "M" or "F" with Faker.random().nextElement(["M", "F"]))
//    Date of Birth (Hint: Use Faker to generate a random date in MM/DD/YYYY format with Faker.date().birthday())
//    Social Security Number (Hint: Use Faker to create a random SSN in the format XXX-XX-XXXX with Faker.idNumber().ssnValid())
//    Email address (Hint: Use Faker to generate an email with Faker.internet().emailAddress())
//    Password (Faker.internet().password())
//    Confirm password (Same as the password)
//    Address (Hint: Use Faker to generate a random street address for the user with Faker.address().streetAddress())
//    Locality (Hint: Use Faker to provide a random locality or city with Faker.address().city())
//    Region (Hint: Use Faker to generate an abbreviation for the region, such as CA, LA, or NY with Faker.address().stateAbbr())
//    Postal Code (Hint: Use Faker to generate a random 5-digit postal code with Faker.address().zipCode())
//    Country (Hint: Use Faker to randomly select a country abbreviation like US, KZ, or TY with Faker.address().countryCode())
//    Home Phone (Hint: Generate phone numbers using Faker with a format like (XXX) XXX-XXXX with Faker.phone().cellPhone())
//    Mobile Phone
//    Work Phone
