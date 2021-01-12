Feature: Back-end Tasks

  Scenario: Task 1
    Given I have the API key
    When Execute GET call on cryptocurrency map
    And Retrieve BTC, USTD, ETH IDs
    Then Use ids in tools price conversion call successfully

  Scenario: Task 2
    Given I have the API key
    When Execute GET call on cryptocurrency info with 1027 id
    Then Confirm following assertions with base path 'data.1027.'
      |path                 |value                                                        |
      |logo	                |https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png |
      |urls.technical_doc[0]|https://github.com/ethereum/wiki/wiki/White-Paper            |
      |symbol       	    |ETH                                                          |
      |date_added   	    |2015-08-07T00:00:00.000Z                                     |
      |platform     	    |null                                                         |
      |tags          	    |mineable                                                     |

  Scenario: Task 3
    Given I have the API key
    When Execute GET call on cryptocurrency info for the first 10 ids
    Then Check the 10 currencies have 'mineable' tag
    And Verify that the correct cryptocurrencies are printed out

