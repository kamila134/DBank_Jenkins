package co.wedevx.digitalbank.automation.ui.models;

public class AccountDetails {

    private String title;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String ssn;
    private String emailAddress;
    private String password;
    private String address;
    private String locality;
    private String region;
    private String postalCode;
    private String country;
    private String homePhone;

    public AccountDetails(String title, String firstName, String lastName, String gender, String dateOfBirth, String ssn, String emailAddress, String password, String address, String locality, String region, String postalCode, String country, String homePhone) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.ssn = ssn;
        this.emailAddress = emailAddress;
        this.password = password;
        this.address = address;
        this.locality = locality;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
        this.homePhone = homePhone;
    }


    public String getFirstName() {
        return firstName;
    }
    public String getTitle() {
        return title;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSsn() {
        return ssn;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality() {
        return locality;
    }

    public String getRegion() {
        return region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getHomePhone() {
        return homePhone;
    }
}
