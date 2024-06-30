package co.wedevx.digitalbank.automation.ui.models;

public class AccountUpdate {

    private String title;
    private String firstName;
    private String lastName;
    private String address;
    private String locality;
    private String region;
    private String postalCode;
    private String country;
    private String homePhone;

    public AccountUpdate(String title, String firstName, String lastName, String address, String locality, String region, String postalCode, String country, String homePhone) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.locality = locality;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
        this.homePhone = homePhone;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
