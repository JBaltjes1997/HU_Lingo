Feature: Training for Lingo
  As a Lingo participant
  I want to practice guessing 5,6, 7 letter words
  In order to prepare for my tv appearance.

  Scenario: Start a new game
    When I start a new game
    Then the word to guess has "5" letters
    And I should see the first letter
    And my score should be "0"

  Scenario: Start a new round
    Given I am playing a game
    And I won the previous round
    And the last word had "5" letters
    When I start a new round
    Then the word to guess has "6" letters

  Scenario Outline: Start a new round
    Given I am playing a game
    And I won the previous round
    And the last word had "<previous length>" letters
    When I start a new round
    Then the word to guess has "<next length>" letters
    Examples:
    |previous length| next length|
    |5              | 6     |
    |6              | 7     |
    |7              | 5     |

    Given I am playing a game
    And the round was lost
    Then I cannot start a new round

  Scenario Outline:
    Given I am playing a game
    And the word to guess is "<word to guess>"
    When I guess "<attempt>"
    Then I get feedback "<feedback>"
    Examples:
    | word to guess | attempt | feedback |