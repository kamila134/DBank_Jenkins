@Withdrawal
Feature: Checking account - making a withdrawal

  Background:
    Given the user registers a new account as follows with mock email and ssn:
      | title | firstName | lastName | gender | dateOfBirth | password  | address | locality | region | postalCode | country | homePhone  |
      | Ms.   | Lexi      | Kale     | F      | 11/05/2009  | Hello123! | Main st | LA       | CA     | 00001      | US      | 3607857533 |
    And the user signs in with their credentials


  @PositiveWithdrawal
  Scenario Outline: Positive
    When a user opens a new account as follows:
      | bankingType   | accountType   | ownershipType | accountName | initialDepositAmount |
      | <bankingType> | <accountType> | Individual    | Account 1   | 2500.00              |
    Then a green "Confirmation" success message is displayed
    When the user makes a withdrawal of $"<withdrawalAmount>"
    Then the user's account balance is "<accountBalance>"

    Examples:
      | bankingType | accountType       | withdrawalAmount | accountBalance |
      | Savings     | Money Market      | 300.00           | 2200.00        |
      | Checking    | Standard Checking | 2500.00          | 0.00           |


  Scenario Outline: Negative - exceeding amount
    When a user opens a new account as follows:
      | bankingType   | accountType   | ownershipType | accountName | initialDepositAmount |
      | <bankingType> | <accountType> | Individual    | Account 1   | 2500.00              |
    Then a green "Confirmation" success message is displayed
    Given the overdraft fee is $25
    When the user makes a withdrawal with an amount of $"2530"
    Then the user is displayed with a red "Error" message

    Examples:
      | bankingType | accountType       |
      | Savings     | Money Market      |
      | Checking    | Standard Checking |

  @NegativeWithdrawal
  Scenario Outline: Negative - blank fields
    When a user opens a new account as follows:
      | bankingType   | accountType   | ownershipType | accountName | initialDepositAmount |
      | <bankingType> | <accountType> | Individual    | Account 1   | 2500.00              |
    Then a green "Confirmation" success message is displayed
    When the user makes an invalid withdrawal with an empty field
      | accountName   | withdrawalAmount   |
      | <accountName> | <withdrawalAmount> |
    Then the "<fieldName>" field displays a "<errorMessage>" error message

    Examples:
      | bankingType | accountType       | accountName | withdrawalAmount | fieldName        | errorMessage                       |
      | Savings     | Money Market      |             | 500.00           | accountType      | Please select an item in the list. |
      | Checking    | Standard Checking | Account 1   |                  | withdrawalAmount | Please fill out this field.        |
      | Checking    | Standard Checking | Account 1   | -300.50          | withdrawalAmount | Please match the requested format. |
      | Savings     | Money Market      | Account 1   | 100,50           | withdrawalAmount | Please match the requested format. |
      | Checking    | Standard Checking | Account 1  | thirty           | withdrawalAmount | Please match the requested format. |

#  Scenario: Negative
#    Given the user is on the login page


