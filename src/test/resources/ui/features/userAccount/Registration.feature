Feature: New account

  @Registration
  Scenario: Registering a new account
    Given the user "peter2020@test.co" doesn't exist in the DB
    When the user registers a new account as follows:
      | title | firstName | lastName | gender | dateOfBirth | ssn         | email             | password  | address | locality | region | postalCode | country | homePhone  |
      | Mr.   | Peter     | Kale     | M      | 11/05/1994  | 123-45-9834 | peter2020@test.co | Peter123! | Main st | LA       | CA     | 90808      | US      | 3607857533 |
    Then a new account is created
    And the following info should be saved in the DB:
      | title | firstName | lastName | gender | dateOfBirth | ssn         | email             | password  | address | locality | region | postalCode | country | homePhone  | accountNonExpired | accountNonLocked | credentialsNonExpired | enabled |
      | Mr.   | Peter     | Kale     | M      | 11/05/1994  | 123-45-9834 | peter2020@test.co | Peter123! | Main st | LA       | CA     | 90808      | US      | 3607857533 | true              | true             | true                  | true    |


  @Login
  Scenario:
    Given the user is on the login page
