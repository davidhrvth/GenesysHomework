Feature: Case 4

  @regression
  Scenario: Case 4 - iFrame and tab handling
    Given user opens url "https://demo.guru99.com/test/guru99home/"
    And closes cookie window
    When user clicks on image in iFrame
    And checks if page title is "Selenium Live Project: FREE Real Time Project for Practice"
    And closes tab
    And clicks on Selenium link in Testing menu
    Then button with value property "Submit" should be displayed
    #The website changed the "Join Now" button to "Submit"