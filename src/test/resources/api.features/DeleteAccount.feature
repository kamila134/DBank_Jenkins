Feature: Delete account - API endpoint
  Scenario: Positive - Delete account successfully
    Given the user "kiraPolskk@gmail.com" exists in th DB
    And a "Account 101" banking account doesn't exist in the DB
    When the user creates a new banking account:
      | accountName | accountTypeCode | openingDeposit | ownerTypeCode |
      | Account 101 | ICK             | 400.00         | IND           |
#    Then the following account is created:
#      | accountName | accountTypeCode | openingDeposit | ownerTypeCode | accountStandingName |
#      | Account 101 | ICK             | 400.00         | IND           | Open                |
#    And the information is saved in the Db as follows:
#      | accountName | accountTypeCode | openingDeposit | ownerTypeCode |
#      | Account 101 | ICK             | 400.00         | IND           |
    When the user deletes the account
    Then the "Account 101" banking account is no longer saved in the DB