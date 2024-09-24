Feature: Manage Songs in Favorites Playlist

  @run
  Scenario: Favorites Playlist is empty when no songs are saved
    Given I'm logged in
    And I'm on the home page
    When I navigate to the Favorites Playlist page
    Then the playlist should be empty
@skip
  Scenario: User saves songs to their Favorites Playlist
    When I navigate to the All Songs page
    And I mark songs as favorites for “HoliznaCC0 - Way Of The Samurai” and “Epic Song”
    And I navigate to the Favorites Playlist page
    Then I should see the songs in my Favorites Playlist
  @skip
  Scenario: User downloads a song from the Favorites Playlist
    Given I have saved songs in my Favorites Playlist
    When I navigate to the Favorites Playlist page
    And I download the song “Epic Song” from my Favorites Playlist
    Then the song should be downloaded to my device
  @skip
  Scenario: User deletes a song from the Favorites Playlist
    Given I have saved songs in my Favorites Playlist
    When I navigate to the Favorites Playlist page
    And I delete the song “HoliznaCC0 - Way Of The Samurai” from my Favorites Playlist
    Then the song should be removed from my Favorites Playlist