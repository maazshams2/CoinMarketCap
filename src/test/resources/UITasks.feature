Feature: UI Tasks

  Scenario: Test 1
    Given Open coinmarketcap website
    When Select 100 rows to be viewed
    Then Verify that results are displayed

  Scenario: Test 2
    Given Open coinmarketcap website
    When Add 5 to 10 cryptocurrencies to Watchlist
    And Open Watchlist on different browser tab
    Then Verify as many values possible

  Scenario: Test 3
    Given Open coinmarketcap website
    When Display dropdown meny on cryptocurrencies tab
    And Cick on any Full List option
    And Record data on current page
    And Apply any filter from dropdown menu
    Then Verify data recorded previously

