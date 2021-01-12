package stepDef;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.IsEqual.equalTo;

public class API {
    private static String apiKey;
    private static Response response = null;

    @Before
    public void beforeTest(){
        RestAssured.baseURI = "https://pro-api.coinmarketcap.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Given("^I have the API key$")
    public void i_have_the_api_key() {
        apiKey = "a5633ba8-c4de-45ab-8e6f-e3b0a8f9ff98";
    }

    @When("^Execute GET call on cryptocurrency map$")
    public void execute_get_call_on_cryptocurrency_map() {
        response =
                given()
                    .header("X-CMC_PRO_API_KEY", apiKey)
            .when()
                    .get("/v1/cryptocurrency/map")
            .then()
                    .assertThat().statusCode(200)
            .extract()
                    .response();
    }

    @And("^Retrieve BTC, USTD, ETH IDs$")
    public List<String> retrieve_the_following_ids(){
        JsonPath jsonPath = new JsonPath(response.getBody().asString());

        List<String> ids = jsonPath.getList("data.findAll{i -> i.symbol == 'BTC' && " +
                "i.symbol == 'USDT' && i.symbol == 'ETH'}.id");

        return ids;
    }

    @Then("^Use ids in tools price conversion call successfully$")
    public void use_ids_in_tools_price_conversion_call(){
        for (String id : retrieve_the_following_ids()) {
            response =
                    given()
                            .header("X-CMC_PRO_API_KEY", apiKey)
                            .queryParams("amount", "10.43", "id", id, "convert_id", "2889")
                    .when()
                            .get("/v1/tools/price-conversion")
                    .then()
                            .assertThat().statusCode(200)
                    .extract()
                            .response();
        }
    }

    @When("^Execute GET call on cryptocurrency info with (.*) id$")
    public void execute_get_call_on_cryptocurrency_info_with_id(int id) {
        response =
                given()
                        .header("X-CMC_PRO_API_KEY", apiKey)
                        .queryParam("id", id)
                .when()
                        .get("/v1/cryptocurrency/info")
                .then()
                        .assertThat().statusCode(200)
                .extract()
                        .response();
    }

    @Then("^Confirm following assertions with base path '([^']*)'$")
    public void confirm_the_following_assertions_with_base_path(String basePath, DataTable dt){
        List<Map<String, String>> inputMap = dt.asMaps(String.class, String.class);

        for (int i=0 ; i<inputMap.size()-2 ; i++) {
            response
                    .then()
                        .rootPath(basePath)
                        .body(inputMap.get(i).get("path"), equalTo(inputMap.get(i).get("value")));

        }

        response
                .then()
                    .rootPath(basePath)
                    .body(inputMap.get(4).get("path"), equalTo(null)).and()
                    .body(inputMap.get(5).get("path"), hasItem(inputMap.get(5).get("value")));
    }

    List<Response> responseList = new ArrayList<Response>();
    @When("^Execute GET call on cryptocurrency info for the first (.*) ids$")
    public void execute_get_call_on_cryptocurrency_info_for_the_first_ids(int id) {
        for (int i=1 ; i<id+1 ; i++) {
            response =
                    given()
                            .header("X-CMC_PRO_API_KEY", apiKey)
                            .queryParam("id", i)
//                        .log().all()
                    .when()
                            .get("/v1/cryptocurrency/info")
                    .then()
//                        .log().all()
                            .assertThat().statusCode(200)
                    .extract()
                            .response();

            responseList.add(response);
        }
    }

    @Then("^Check the (.*) currencies have '([^']*)' tag$")
    public void retrieve_the_first_currencies(int id, String tag){
        for (int i=1 ; i<responseList.size()+1 ; i++){
            responseList.get(i-1)
                    .then()
                        .body("data." + i + ".tags", hasItem(tag));
        }
    }

    @And("^Verify that the correct cryptocurrencies are printed out$")
    public void verify_that_the_correct_cryptocurrencies_are_printed_out(){
        for (int i=1 ; i<responseList.size()+1 ; i++){
            responseList.get(i-1)
                    .then()
                        .body("data", hasKey(String.valueOf(i)));
        }
    }



}
