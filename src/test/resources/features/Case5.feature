Feature: Case 5

  @regression
  Scenario: Case 5 – REST API testing
    Given prepare scenario for api testing
    When get request is sent to "https://jsonplaceholder.typicode.com" with endpoint "/users"
    Then email addresses should contain '@'