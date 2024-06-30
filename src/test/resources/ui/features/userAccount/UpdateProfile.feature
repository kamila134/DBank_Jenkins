Feature: Existing account

  Scenario: Updating the user's account profile
    Given the user is signed in as "Peter101@gmail.com" and "Peter123!"
    When a user updates the profile as follows:
      |title | firstName | lastName | address    | locality | region | postalCode | country | homePhone  |
      |Mr.   | Peter     | Kale     | Gallery st | LA       | CA     | 23045      | US      | 3607857533 |
    Then the profile is updated