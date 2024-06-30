@NegativeBankingAccount
Feature: Creating new account to test API

  Background:
    Given the user "kiraPolskk@gmail.com" exists in th DB
#    Given the user "kiraPolskk@gmail.com" doesn't exist in the DB
#    And a new user is created in the DB
#      | title | firstName | lastName | gender | dob        | emailAddress         | password  | ssn         | address | locality | region | postalCode | country | homePhone  |
#      | Mrs.  | Kira      | Pols     | F      | 11/05/2009 | kiraPolskk@gmail.com | Hello123! | 128-22-8456 | Main st | LA       | CA     | 00001      | US      | 3607857533 |


  Scenario: Positive - Creating a valid account
    And a "Account 101" banking account doesn't exist in the DB
    When the user creates a new banking account:
      | accountName | accountTypeCode | openingDeposit | ownerTypeCode |
      | Account 101 | ICK             | 400.00         | IND           |
    Then the following account is created:
      | accountName | accountTypeCode | openingDeposit | ownerTypeCode | accountStandingName |
      | Account 101 | ICK             | 400.00         | IND           | Open                |
    And the information is saved in the Db as follows:
      | accountName | accountTypeCode | openingDeposit | ownerTypeCode |
      | Account 101 | ICK             | 400.00         | IND           |

  @NegativeBankingAccount
  Scenario Outline: Negative - Creating an account with invalid inputs
    When the user tries to create a new banking account with invalid credentials:
      | accountName   | accountTypeCode   | openingDeposit   | ownerTypeCode   |
      | <accountName> | <accountTypeCode> | <openingDeposit> | <ownerTypeCode> |
    Then an "<errorMessage>" error message is displayed
    And the account "<accountName>" is not saved in the DB

    Examples:
      | accountName     | accountTypeCode | openingDeposit | ownerTypeCode | errorMessage                                                                                |
      |                 | ICK             | 400.00         | IND           | Account Name is required.                                                                   |
      | Account INVALID | INVALID         | 400.00         | IND           | Account Type Code must either be 'SCK' or 'ICK' for checking or 'SAV' or 'MMA' for savings. |
      | Account INVALID |                 | 400.00         | IND           | Account Type Code is required.                                                              |
      | Account INVALID | ICK             | 400.00         | INVALID       | Ownership Type Code must either be 'IND' or 'JNT'.                                          |
      | Account INVALID | ICK             | 400.00         |               | Ownership Type Code is required.                                                            |
      | Account INVALID | ICK             |                | IND           | Opening Deposit is required.                                                                |
      | Account INVALID | ICK             | 4.00           | IND           | 406 Not Acceptable                                                                          |
      | Account INVALID | ICK             | hundred        | IND           | 400 Bad Request                                                                             |

  Scenario: Positive - Delete account successfully
    And a "Account 101" banking account doesn't exist in the DB
    When the user creates a new banking account:
      | accountName | accountTypeCode | openingDeposit | ownerTypeCode |
      | Account 101 | ICK             | 400.00         | IND           |
    Then the following account is created:
      | accountName | accountTypeCode | openingDeposit | ownerTypeCode | accountStandingName |
      | Account 101 | ICK             | 400.00         | IND           | Open                |
    And the information is saved in the Db as follows:
      | accountName | accountTypeCode | openingDeposit | ownerTypeCode |
      | Account 101 | ICK             | 400.00         | IND           |
    When the user deletes the account
    Then the "Account 101" banking account is no longer saved in the DB

