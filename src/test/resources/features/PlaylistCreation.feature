Feature: Playlist Creation

  @run
  Scenario: User creates a new playlist successfully
    Given I am logged in
    And The user is on the Playlist creation page
    When The user selects the playlist type as New Playlist
    And The user enters and submits a valid playlist name "My New Playlist"
    Then The new playlist should be created successfully
    And The playlist "My New Playlist" should be displayed in the app

  @run
  Scenario: User tries to create a playlist with the same name
    Given I am logged in
    And The user is on the Playlist creation page
    When The user selects the playlist type as New Playlist
    And The user enters and submits an existing playlist name "My New Playlist"
    Then An error message should be displayed "A playlist with this name already exists"

  # @skip
  @run
  Scenario: User tries to create a playlist with an invalid name
    Given I am logged in
    And The user is on the Playlist creation page
    When The user selects the playlist type as New Playlist
    # Test for names shorter than 3 characters
    And The user enters and submits an invalid name with chars less than 3
    Then The playlist should not be created
    # Test for names longer than 10 characters
    And The user enters and submits an invalid name with chars less than 3
    #And The user enters and submits an invalid name more than 10 chars
    Then The playlist should not be created

  # Uncomment and implement this scenario when it's ready for execution
  # Scenario: User creates a playlist with a name within the valid length
  #   Given The user is on the Playlist creation page
  #   When The user selects the playlist type as New Playlist
  #   And The user enters a playlist name with more than 3 characters but less than 10 "My Playlist"
  #   And The user submits the playlist creation form
  #   Then The playlist should be created successfully
  #   And The playlist name should be displayed correctly in the app or database

#** pendingThen A red border should be displayed around the name input field