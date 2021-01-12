$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/BETasks.feature");
formatter.feature({
  "name": "Back-end Tasks",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Task 1",
  "description": "",
  "keyword": "Scenario"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I have the API key",
  "keyword": "Given "
});
formatter.match({
  "location": "API.i_have_the_api_key()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Execute GET call on cryptocurrency map",
  "keyword": "When "
});
formatter.match({
  "location": "API.execute_get_call_on_cryptocurrency_map()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Retrieve BTC, USTD, ETH IDs",
  "keyword": "And "
});
formatter.match({
  "location": "API.retrieve_the_following_ids()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Use ids in tools price conversion call successfully",
  "keyword": "Then "
});
formatter.match({
  "location": "API.use_ids_in_tools_price_conversion_call()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
formatter.scenario({
  "name": "Task 2",
  "description": "",
  "keyword": "Scenario"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I have the API key",
  "keyword": "Given "
});
formatter.match({
  "location": "API.i_have_the_api_key()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Execute GET call on cryptocurrency info with 1027 id",
  "keyword": "When "
});
formatter.match({
  "location": "API.execute_get_call_on_cryptocurrency_info_with_id(int)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Confirm following assertions with base path \u0027data.1027.\u0027",
  "rows": [
    {
      "cells": [
        "path",
        "value"
      ]
    },
    {
      "cells": [
        "logo",
        "https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png"
      ]
    },
    {
      "cells": [
        "urls.technical_doc[0]",
        "https://github.com/ethereum/wiki/wiki/White-Paper"
      ]
    },
    {
      "cells": [
        "symbol",
        "ETH"
      ]
    },
    {
      "cells": [
        "date_added",
        "2015-08-07T00:00:00.000Z"
      ]
    },
    {
      "cells": [
        "platform",
        "null"
      ]
    },
    {
      "cells": [
        "tags",
        "mineable"
      ]
    }
  ],
  "keyword": "Then "
});
formatter.match({
  "location": "API.confirm_the_following_assertions_with_base_path(String,DataTable)"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
formatter.scenario({
  "name": "Task 3",
  "description": "",
  "keyword": "Scenario"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "I have the API key",
  "keyword": "Given "
});
formatter.match({
  "location": "API.i_have_the_api_key()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Execute GET call on cryptocurrency info for the first 10 ids",
  "keyword": "When "
});
formatter.match({
  "location": "API.execute_get_call_on_cryptocurrency_info_for_the_first_ids(int)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Check the 10 currencies have \u0027mineable\u0027 tag",
  "keyword": "Then "
});
formatter.match({
  "location": "API.retrieve_the_first_currencies(int,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Verify that the correct cryptocurrencies are printed out",
  "keyword": "And "
});
formatter.match({
  "location": "API.verify_that_the_correct_cryptocurrencies_are_printed_out()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});