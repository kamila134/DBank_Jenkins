Feature: Visa Direct Transfer

  Background:
    Given the user registers a new account as follows with mock email and ssn:
      | title | firstName | lastName | gender | dateOfBirth | password  | address | locality | region | postalCode | country | homePhone  |
      | Mrs.  | Kira      | Pols     | F      | 11/05/2009  | Hello123! | Main st | LA       | CA     | 00001      | US      | 3607857533 |
    And the user signs in with their credentials

  @VisaTransfer
  Scenario Outline: Positive - Error message
    When a user opens a new account as follows:
      | bankingType   | accountType   | ownershipType | accountName | initialDepositAmount |
      | <bankingType> | <accountType> | Individual    | Account 1   | 10000.00             |
    Then a green "Confirmation" success message is displayed

    When the user makes a direct transfer of "3000.00" to Visa
    Then the user encounters a "Service Unavailable" error message

    Examples:
      | bankingType | accountType       |
      | Savings     | Money Market      |
      | Checking    | Standard Checking |