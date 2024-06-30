Feature: Dashboard icons

  Background:
    Given the user logs in with existing credentials
      | email           | password |
      | alana@gmail.com | 123      |

  @GeneralDB
  Scenario: Menu Toggle Btn
    When the user clicks on the Notification Bell
    Then a dropdown with notifications opens
