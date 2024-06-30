@Deposit
Feature: Checking account - making a deposit

  Background:
#    Given the user is on the login page
    Given the user registers a new account as follows with mock email and ssn:
      | title | firstName | lastName | gender | dateOfBirth | password  | address | locality | region | postalCode | country | homePhone  |
      | Mr.   | Peter     | Kale     | M      | 11/05/1994  | Hello123! | Main st | LA       | CA     | 90808      | US      | 3607857533 |
    And the user signs in with their credentials


  Scenario Outline: Positive
    When a user opens a new account as follows:
      | bankingType   | accountType   | ownershipType   | accountName | initialDepositAmount |
      | <bankingType> | <accountType> | <ownershipType> | Account 1   | 10000.00             |
    Then a green "Confirmation" success message is displayed
    When the user makes a deposit of <depositAmount>
    Then the user's account balance is "<accountBalance>"
    Examples:
      | bankingType | accountType       | ownershipType | depositAmount | accountBalance |
      | Checking    | Interest Checking | Individual    | 50.00         | 10050.00       |
      | Checking    | Standard Checking | Joint         | 1000.10       | 11000.10       |
      | Savings     | Money Market      | Individual    | 340.00        | 10340.00       |
      | Savings     | Savings           | Joint         | 25.10         | 10025.10       |

  Scenario Outline: Positive - Reset button
    When a user opens a new account as follows:
      | bankingType   | accountType   | ownershipType   | accountName | initialDepositAmount |
      | <bankingType> | <accountType> | <ownershipType> | Account 1   | 10000.00             |
    Then a green "Confirmation" success message is displayed
    When the user makes a $2500 deposit and clicks the "Reset" button
    Then the deposit fields are cleared and a deposit is not made
    And the user's account balance is "10000.00"
    Examples:
      | bankingType | accountType       | ownershipType |
      | Checking    | Interest Checking | Individual    |
      | Checking    | Standard Checking | Joint         |
      | Savings     | Money Market      | Individual    |
      | Savings     | Savings           | Joint         |

#  Scenario Outline: Negative case - deposit with negative deposit amount
#    When a user makes a deposit to new checking account but enters an invalid "<invalidAmount>" deposit amount
#    Then a red "The deposit amount ($-10.00) must be greater than $0.00" error message is displayed

  Scenario Outline: Negative
    When a user opens a new account as follows:
      | bankingType   | accountType   | ownershipType   | accountName | initialDepositAmount |
      | <bankingType> | <accountType> | <ownershipType> | Account 1   | 10000.00             |
    Then a green "Confirmation" success message is displayed
    When a user makes a deposit with the following data:
      | bankingType   | accountName   | depositAmount   |
      | <bankingType> | <accountName> | <depositAmount> |
    Then the "<fieldWithError>" field displays a "<errorMessage>" error message


    Examples:
      | bankingType | accountType       | ownershipType | accountName | depositAmount | fieldWithError | errorMessage                       |
      | checking    | Standard Checking | Joint         |             | 400.5         | accountName    | Please select an item in the list. |
      | checking    | Interest Checking | Individual    | Account 1   |               | depositAmount  | Please fill out this field.        |
      | savings     | Money Market      | Individual    |             | 2000          | accountName    | Please select an item in the list. |
      | savings     | Savings           | Joint         | Account 1   |               | depositAmount  | Please fill out this field.        |
      | checking    | Standard Checking | Joint         | Account 1   | -10           | depositAmount  | Please match the requested format. |
      | checking    | Interest Checking | Individual    | Account 1   | twenty        | depositAmount  | Please match the requested format. |
      | checking    | Interest Checking | Joint         | Account 1   | 20,99         | depositAmount  | Please match the requested format. |
      | savings     | Money Market      | Individual    | Account 1   | -250.50       | depositAmount  | Please match the requested format. |
      | savings     | Savings           | Joint         | Account 1   | thousand      | depositAmount  | Please match the requested format. |
      | savings     | Money Market      | Joint         | Account 1   | $9000         | depositAmount  | Please match the requested format. |
