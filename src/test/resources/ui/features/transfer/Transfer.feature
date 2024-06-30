Feature: Making a transfer

  Background:
    Given the user registers a new account as follows with mock email and ssn:
      | title | firstName | lastName | gender | dateOfBirth | password  | address | locality | region | postalCode | country | homePhone  |
      | Mrs.  | Kira      | Pols     | F      | 11/05/2009  | Hello123! | Main st | LA       | CA     | 00001      | US      | 3607857533 |
    And the user signs in with their credentials


  Scenario Outline: Positive - Transfer between accounts
    When a user opens new accounts as follows:
      | bankingType    | accountType    | ownershipType | accountName | initialDepositAmount |
      | <bankingType1> | <accountType1> | Individual    | Account 1   | 10000.00             |
      | <bankingType2> | <accountType2> | Joint         | Account 2   | 5000.00              |
    Then a green "Confirmation" success message is displayed

    When the user makes the following transfer:
      | fromAccount | toAccount | transferAmount |
      | Account 1   | Account 2 | 3000.00        |
    Then the accounts' details are as follows:
      | transferorType | transferorAccountName | transferorBalance | transfereeType | transfereeAccountName | transfereeBalance |
      | <bankingType1> | Account 1             | 7000.00           | <bankingType2> | Account 2             | 8000.00           |

    Examples:
      | bankingType1 | accountType1      | bankingType2 | accountType2      |
      | Savings      | Money Market      | Savings      | Savings           |
      | Savings      | Savings           | Checking     | Standard Checking |
      | Checking     | Standard Checking | Savings      | Money Market      |
      | Checking     | Interest Checking | Checking     | Interest Checking |

  Scenario Outline: Negative - empty fields
    When a user opens new accounts as follows:
      | bankingType    | accountType    | ownershipType | accountName | initialDepositAmount |
      | <bankingType1> | <accountType1> | Individual    | Account 1   | 10000.00             |
      | <bankingType2> | <accountType2> | Joint         | Account 2   | 5000.00              |
    Then a green "Confirmation" success message is displayed
    When the user makes the following transfer:
      | fromAccount   | toAccount   | transferAmount   |
      | <fromAccount> | <toAccount> | <transferAmount> |
    Then a "<fieldName>" field displays a "<errorMessage>" error message

    Examples:
      | bankingType1 | accountType1      | bankingType2 | accountType2      | fromAccount | toAccount | transferAmount | fieldName      | errorMessage                       |
      | Savings      | Money Market      | Savings      | Savings           | Account 1   | Account 2 |                | transferAmount | Please fill out this field.        |
      | Savings      | Savings           | Checking     | Standard Checking | Account 1   |           | 500.00         | toAccount      | Please select an item in the list. |
      | Checking     | Standard Checking | Savings      | Money Market      |             | Account 2 | 500.00         | fromAccount    | Please select an item in the list. |
#      | Checking     | Interest Checking | Checking     | Interest Checking | Account 1   | Account 2 | -50.00         | transferAmount    | Please match the requested format.                                                                    |
#      | Checking     | Interest Checking | Checking     | Interest Checking | Account 1   | Account 2 | thousand       | transferAmount    | Please match the requested format.                                                                    |
#      | Checking     | Interest Checking | Checking     | Interest Checking | Account 1   | Account 2 | 300,99         | transferAmount    | Please match the requested format.                                                                    |
#      | Checking     | Interest Checking | Checking     | Interest Checking | Account 1   | Account 2 | 5000000.00     | red error message | Error The amount ($5000000.00) requested for transfer is more than the available balance ($10000.00). |

  @Transfer
  Scenario Outline: Negative - Choosing a non-existent account
    When a user opens new accounts as follows:
      | bankingType    | accountType    | ownershipType | accountName | initialDepositAmount |
      | <bankingType1> | <accountType1> | Individual    | Account 1   | 10000.00             |
    Then a green "Confirmation" success message is displayed

    When the user makes the following transfer with a non-existent account:
      | fromAccount   | toAccount   | transferAmount |
      | <fromAccount> | <toAccount> | 3000.00        |
    Then the system throws a "NoSuchElementException" exception

    Examples:
      | bankingType1 | accountType1      | fromAccount | toAccount |
      | Savings      | Money Market      | Account 1   | Account 2 |
      | Savings      | Savings           | Account 2   | Account 1 |
      | Checking     | Standard Checking | Account 1   | Account 2 |
      | Checking     | Interest Checking | Account 2   | Account 1 |

