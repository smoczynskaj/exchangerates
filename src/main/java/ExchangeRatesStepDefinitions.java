import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

public class ExchangeRatesStepDefinitions {
    private static final String BASE_URL = "https://api.ratesapi.io/api/";
    RequestSpecification request;
    private  static Response response;

    @Given("Rates API for Latest Foreign Exchange rates")
    public void ratesAPIForLatestForeignExchangeRates() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "latest";

        request  =  RestAssured.given();
        request.header("Content-Type",  "application/json");
    }

    @When("The API is available")
    public void theAPIIsAvailable() {
        response = request.get();
    }

    @Then("An automated test suite should run which will assert the success status of the response")
    public void anAutomatedTestSuiteShouldRunWhichWillAssertTheSuccessStatusOfTheResponse() {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("An automated test suite should run which will assert the response")
    public void anAutomatedTestSuiteShouldRunWhichWillAssertTheResponse() {
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath.get("base"));
        Assert.assertNotNull(jsonPath.get("rates"));
        Assert.assertNotNull(jsonPath.get("date"));
    }

    @When("An incorrect or incomplete url is provided")
    public void anIncorrectOrIncompleteUrlIsProvided() {
        response = request.get("https://api.ratesapi.io/api/test");
    }

    @Then("Test case should assert the correct response supplied by the call")
    public void testCaseShouldAssertTheCorrectResponseSuppliedByTheCall() {
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals("time data 'test' does not match format '%Y-%m-%d'", jsonPath.get("error"));
    }

    @Given("Rates API for Specific date Foreign Exchange rates")
    public void ratesAPIForSpecificDateForeignExchangeRates() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "2020-02-10";

        request  =  RestAssured.given();
        request.header("Content-Type",  "application/json");
    }

    @When("A future date is provided in the url")
    public void aFutureDateIsProvidedInTheUrl() {
        response = request.get("https://api.ratesapi.io/api/2021-12-18");
    }

    @Then("An automated test suite should run which will validate that the response matches for the current date")
    public void anAutomatedTestSuiteShouldRunWhichWillValidateThatTheResponseMatchesForTheCurrentDate() {
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertNotNull(jsonPath.get("base"));
        Assert.assertNotNull(jsonPath.get("rates"));
        Assert.assertNotNull(jsonPath.get("date"));

        String today = RestAssured.get("https://api.ratesapi.io/api/latest").jsonPath().get("date");

        Assert.assertEquals(today, jsonPath.get("date").toString());
    }
}