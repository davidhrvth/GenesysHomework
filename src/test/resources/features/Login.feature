Feature: Login feature

  @regression
  Scenario: Logging in with credentials in credentials.json
    Given the user navigates to SwagLabs page
    When the user fills out the login form
    And clicks login
    Then the user should be logged in successfully, or valid reasoning should be given as to why the user has not been logged in

