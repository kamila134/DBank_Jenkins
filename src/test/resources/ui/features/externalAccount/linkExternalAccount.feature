Feature: Link external account

  Background:
    Given the user registers a new account as follows with mock email and ssn:
      | title | firstName | lastName | gender | dateOfBirth | password  | address | locality | region | postalCode | country | homePhone  |
      | Mr.   | Peter     | Kale     | M      | 11/05/1994  | Hello123! | Main st | LA       | CA     | 90808      | US      | 3607857533 |
    And the user signs in with their credentials

  @VisaTransfer
  Scenario: Positive - Error
    When the user wants to link an external account
    Then the user is displayed with an error "There are no banking providers available." message
    Given the existing field "Open Banking Providers" doesn't have any options
    When the user click on the Submit button
    Then the user sees the "Please select an item in the list." error message