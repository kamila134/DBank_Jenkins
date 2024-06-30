Feature: Transactions table

  Background:
    Given the user registers a new account as follows with mock email and ssn:
      | title | firstName | lastName | gender | dateOfBirth | password  | address | locality | region | postalCode | country | homePhone  |
      | Mrs.  | Kira      | Pols     | F      | 11/05/2009  | Hello123! | Main st | LA       | CA     | 00001      | US      | 3607857533 |
    And the user signs in with their credentials

  Scenario Outline: Transaction table - testing table length on different pages
    When a user opens a new account as follows:
      | bankingType   | accountType   | ownershipType | accountName | initialDepositAmount |
      | <bankingType> | <accountType> | Individual    | Account 1   | 10000.00             |
    Then a green "Confirmation" success message is displayed

    When the user does the following transactions:
      | transactionType | amount  | numberOfTransactions | accountBalance |
      | deposit         | 1400.00 | 3                    | 14200.00       |
      | withdrawal      | 550.00  | 6                    | 10900.00       |
      | deposit         | 200.00  | 10                   | 12900.00       |
      | withdrawal      | 400.00  | 9                    | 9300.00        |
    Then the transaction table is updated as follows including the initial deposit during account creation:
      | showNumberOfTransactions | NumberOfPages | transactionsOnTheLastPage |
      | 10                       | 3             | 9                         |
      | 25                       | 2             | 4                         |
      | 50                       | 1             | 29                        |

    Examples:
      | bankingType | accountType       |
      | Savings     | Money Market      |
      | Checking    | Standard Checking |

  @TransactionTable
  Scenario: Transaction table - testing search bar
    When a user opens new accounts as follows:
      | bankingType | accountType       | ownershipType | accountName | initialDepositAmount |
      | Savings     | Savings           | Individual    | Account 1   | 10000.00             |
      | Checking    | Standard Checking | Joint         | Account 2   | 5000.00              |
    Then a green "Confirmation" success message is displayed
    When the user does the following transactions:
      | transactionType | amount  | numberOfTransactions | accountBalance |
      | transfer        | 500.00  | 2                    | 6000.00        |
      | deposit         | 1400.00 | 2                    | 8800.00        |
      | withdrawal      | 550.00  | 2                    | 7700.00        |
    And uses the search bar to find specific transactions
    Then the the transaction table shows the following results including the initial deposit during account creation:
      | searchItem | TransactionsFound |
      | transfer   | 2                 |
      | deposit    | 3                 |
      | withdrawl  | 2                 |
      | Income     | 5                 |
      | Misc       | 2                 |
      | 1400.00    | 2                 |
      | -550.00    | 2                 |
      | 8800.00    | 1                 |
      | 2024-04-16 | 7                 |




#  ==============================================
#  For date and time search (ex: input 2023-, 11-24, 12:30)
#  For category search
#  Description search (text search, validate it is case insensitive)
#  Amount (both negative and positive)
#  Balance (both negative and positive)