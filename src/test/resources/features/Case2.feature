Feature: Case 2

  @regression
  Scenario: Trying to reach /inventory without logging in
    Given user tries to access inventory page without being logged in
    And is redirected to login page
    Then correct reasoning should be given as to why they were redirected

  @regression
  Scenario: Login
    Given the user navigates to SwagLabs login page
    When the user tries to log in with "standard_user" and "secret_sauce"
    And clicks login
    Then the user should be logged in successfully

  @regression
  Scenario: Validate footer content
    Given user is in products page
    When the user scrolls to the bottom of the page
    Then the footer should contain "2023, Terms of Service"
