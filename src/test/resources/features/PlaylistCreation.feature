Feature: Playlist Creation

  Scenario: User creates a new playlist successfully

    Given I am logged in
    And The user is on the Playlist creation page
    When The user selects the playlist type as New Playlist
    And The user enters and submits a valid playlist name "My New Playlist"
    Then The new playlist should be created successfully
    And The playlist "My New Playlist" should be displayed in the app


  # Scenario: User tries to create a playlist with the same name
  #   Given The user is on the Playlist creation page
  #   When The user selects the playlist type as New Playlist
  #   And The user enters an existing playlist name "My Existing Playlist"
  #   And The user submits the playlist creation form
  #   Then An error message should be displayed "Playlist already exists"

  # Scenario: User tries to create a playlist with an invalid name
  #   Given The user is on the Playlist creation page
  #   When The user selects the playlist type as New Playlist
  #   And The user enters an invalid playlist name "No"
  #   And The user submits the playlist creation form
  #   Then A red border should be displayed around the name input field
  #   And The playlist should not be created

  # Scenario: User creates a playlist with a name within the valid length
  #   Given The user is on the Playlist creation page
  #   When The user selects the playlist type as New Playlist
  #   And The user enters a playlist name with more than 3 characters but less than 10 "My Playlist"
  #   And The user submits the playlist creation form
  #   Then The playlist should be created successfully
  #   And The playlist name should be displayed correctly in the database
