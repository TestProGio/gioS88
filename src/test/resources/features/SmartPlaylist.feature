Feature: Create Smart Playlist

  As a user, I want to create a Smart playlist in the app,
  so that I can enjoy the music and modify my settings and preferences.

  # Creating a single rule playlist
  # @smoke @regression
  Scenario Outline: Create Smart Playlist with One Rule
    Given the user is logged into the app
    When the user navigates to the "Create Smart Playlist" page
    And the user sets the playlist name as "<SingleRule Playlist>"
    And the user selects "<Rule Type>"
    And the user inputs "<Search>"
    And the user saves the Smart playlist
    Then the Smart playlist "<SingleRule Playlist>" should be created successfully
    And songs that match the rule should be listed in the playlist

    Examples:
      | SingleRule Playlist | Rule Type   | Search          |
      | SPL Valid Song Title| Title       | Pluto           |
      | SPL Valid Album     | Album       | Airbit          |
      | SPL Valid Artist    | Artist      | Makaih Beats    |
      | SPL Valid Plays     | Plays       | 11              |
      | SPL Valid Played    | Last Played | 10/16/2024      |
      | SPL Valid Length    | Length      | 374             |

  # Creating a playlist with multiple rules
  @skip
  Scenario Outline: Create Smart Playlist with Multiple Rules
    Given the user is logged into the app
    When the user navigates to the "Create Smart Playlist" page
    And the user adds multiple different rules
    And the user selects "<Rule Type>"
    And the user inputs "<Search>"
    And the user sets the playlist name as "<MultiRule Playlist>"
    And the user saves the Smart playlist
    Then the Smart playlist "<MultiRule Playlist>" should be created successfully
    And songs that match the rules should be listed in the playlist

    Examples:
      | MultiRule Playlist    | Rule Type   | Search                              |
      | SPL MultiRule Playlist| Title       | M33 Project - Emotional Soundtrack  |
      | SPL MultiRule Playlist| Album       | Midnight in Mississippi             |
      | SPL MultiRule Playlist| Artist      | Lobo Loco                           |
      | SPL MultiRule Playlist| Plays       | 5                                   |
      | SPL MultiRule Playlist| Last Played | 10/16/2024                          |
      | SPL MultiRule Playlist| Length      | 374                                 |

  # Creating a playlist with grouped rules
  @skip
  Scenario Outline: Create Smart Playlist with Group
    Given the user is logged into the app
    When the user navigates to the "Create Smart Playlist" page
    And the user clicks on the Group button
    And the user sets the playlist name as "<GroupRule Playlist>"
    And the user selects "<Rule Type>"
    And the user inputs "<Search>"
    And the user saves the Smart playlist
    Then the Smart playlist "<GroupRule Playlist>" should be created successfully
    And songs that match the rules should be listed in the playlist

    Examples:
      | GroupRule Playlist    | Rule Type   | Search          |
      | Group Playlist        | Title       | Epic Song       |
      | Group Playlist        | Album       | Till Paradiso   |
      | Group Playlist        | Artist      | Dee Yan-Key     |
      | Group Playlist        | Plays       | 52              |
      | Group Playlist        | Last Played | 10/16/2024      |
      | Group Playlist        | Length      | 374             |

  # Creating a playlist with no matching songs
  @skip
  Scenario: No Matching Songs for Smart Playlist Rule
    Given the user is logged into the app
    When the user navigates to the "Create Smart Playlist" page
    And the user sets the playlist name as "Electronic Rock"
    And the user adds a rule to include song "Personal Jesus"
    And the user saves the Smart playlist
    Then an empty Smart playlist should be created
    And the playlist should display "No songs match the playlist's criteria."

  # Validating playlist name edge cases
  @skip
  Scenario Outline: Validate Playlist Name Rules
    Given the user is logged into the app
    When the user navigates to the "Create Smart Playlist" page
    And the user sets the playlist name as "<Playlist Name>"
    And the user adds a rule to include song title "Epic Song"
    And the user saves the Smart playlist
    Then <Expected Outcome>

    Examples:
      | Playlist Name                                                                                   | Expected Outcome                                                                 |
      | (@Special_Characters!#$%^&*)                                                                    | The Smart playlist "(@Special_Characters!#$%^&*)" should be created successfully |
      | AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA | The app should display an error "Playlist name must be between 1 and 256 characters" |
