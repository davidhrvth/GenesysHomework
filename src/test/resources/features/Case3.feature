Feature: Case 3

  @regression
  Scenario: Case 3 - Rich Text Editor
    Given user opens url "https://onlinehtmleditor.dev/"
    When the user types the text with decoration into the editor
    Then "Automation Test Example" should appear in the rich text editor

