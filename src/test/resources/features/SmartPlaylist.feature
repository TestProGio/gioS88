Feature: Create Smart Playlist

  As a user, I want to create a Smart playlist in the app,
  so that I can enjoy the music and modify my settings and preferences.

  # Creating a single rule playlist
  # @smoke @regression

  Scenario Outline: Create Smart Playlist with One Rule
    Given the user is logged into the app
    When the user clicks playlist creation
    And the user selects New Smart Playlist from menu
    And the user sets the playlist name as "<SingleRule Playlist>"
    And the user selects "<Rule Types>"
    And the user inputs "<SearchThis>"
    And the user saves the Smart playlist
    Then the Smart playlist "<SingleRule Playlist>" should be created successfully
    Then the results should be verified

    Examples:
      | SingleRule Playlist  | Rule Types  | SearchThis          |
      | 1- SPL Valid Title   | Title       | Pluto           |
      | 2- SPL Valid Album   | Album       | Airbit          |
      | 3- SPL Valid Artist  | Artist      | Makaih Beats    |
      | 4- SPL Valid Plays   | Plays       | 11              |
      | 5- SPL Valid Played  | Last Played | 10/16/2024      |
      | 6-SPL Valid Length   | Length      | 374             |

  # Creating a playlist with multiple rules

  Scenario Outline: Create Smart Playlist with Multiple Rules
    Given the user is logged into the app
    When the user clicks playlist creation
    And the user selects New Smart Playlist from menu
    And the user sets the playlist name as "<MultiRule Playlist>"
    And the user adds multiple different rules with options and inputs

      | Rule Options | Input                               |
      | Title        | M33 Project - Emotional Soundtrack  |
      | Album        | Midnight in Mississippi             |
      | Artist       | Lobo Loco                           |
      | Plays        | 5                                   |

    And the user saves the Smart playlist
    Then the Smart playlist "<MultiRule Playlist>" should be created successfully
    Then the results should be verified

    Examples:
      | MultiRule Playlist  |
      | Mixed Playlist 2     |

  # Creating a playlist with grouped rules

  Scenario Outline: Create Smart Playlist with Group
    Given the user is logged into the app
    When the user clicks playlist creation
    And the user selects New Smart Playlist from menu
    And the user sets the playlist name as "<GroupRule Playlist>"
    And the user adds Group option with multiple rules options and inputs
      | Rule Options  | Input           |
      | Title         | Epic Song       |
      | Album         | Till Paradiso   |
      | Artist        | Dee Yan-Key     |
      | Plays         | 52              |
    And the user saves the Smart playlist
    Then the Smart playlist "<GroupRule Playlist>" should be created successfully
    Then the results should be verified

    Examples:
      | GroupRule Playlist   |
      | GroupPlaylist        |


  # Creating a playlist with no matching songs

  Scenario Outline: No Matching Songs for Smart Playlist Rule
    Given the user is logged into the app
    When the user clicks playlist creation
    And the user selects New Smart Playlist from menu
    And the user sets the playlist name as "<NoMatches Playlist>"
    And the user selects "<Rule Types>"
    And the user inputs "<SearchThis>"
    And the user saves the Smart playlist
    Then the Smart playlist "<NoMatches Playlist>" should be created successfully
    Then "No songs match the playlist's criteria." should appear
    Then the results should be verified

    Examples:
      | NoMatches Playlist | Rule Types  | SearchThis        |
      | 1-Invalid Song     | Title       | Personal Jesus     |



  Scenario Outline: Check Playlist Creation For Naming Rules
    Given the user is logged into the app
    When the user clicks playlist creation
    And the user selects New Smart Playlist from menu
    And the user sets the playlist name as "<PlaylistNameRules>"
    And the user selects "<Rule Types>"
    And the user inputs "<SearchThis>"
    And the user saves the Smart playlist
    Then the Smart playlist "<PlaylistNameRules>" should be created successfully
    And the playlist name cannot have more than 256 characters
    Then the results should be verified

    Examples:
      | PlaylistNameRules                  | Rule Types  | SearchThis        |
      | 1-(@Special_Characters!#$%^&*)        | Title       | Epic Song         |
      | 2- AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA | Title       | Pluto         |
