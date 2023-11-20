Feature: Purchace Process

  @regression
  Scenario: Logging in with credentials in credentials.json
    Given the user navigates to SwagLabs login page
    When the user fills out the login form
    And clicks login
    Then the user should be logged in successfully

  @regression
  Scenario Outline: User Completes A Purchase
    Given user is in products page
    When user adds "<listOfItems>" to cart
    And verifies cart product quantity badge equals to "<listOfItems>" size
    And clicks on cart icon
    And clicks on checkout button
    And fills out checkout information with "<firstname>", "<lastname>", "<zipcode>"
    And clicks continue
    And clicks finish
    Then thank you for your order message should appear

    Examples:
      | listOfItems                                   | firstname | lastname | zipcode |
      | Sauce Labs Backpack, Sauce Labs Fleece Jacket | David     | Black    | 1234    |

  @regression
  Scenario: Logout
    Given user is in thank you page
    Then user logs out
