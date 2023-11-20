package org.genesys.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;

public class Case5Step {
    OkHttpClient client;
    JSONArray jsonArray;
    private static final Logger logger = LogManager.getLogger(Case5Step.class);

    @Given("prepare scenario for api testing")
    public void prepare_scenario_for_api_testing() {
        client = new OkHttpClient();
    }

    @When("get request is sent to {string} with endpoint {string}")
    public void request_is_sent_to_api(String url, String endpoint) {
        Request request = new Request.Builder().url(url + "/" + endpoint).get().build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String jsonData = responseBody.string();
                    jsonArray = new JSONArray(jsonData);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject user = jsonArray.getJSONObject(i);
                        String name = user.getString("name");
                        String email = user.getString("email");
                        logger.info(name + " | " + email);
                    }
                }
            }

        } catch (IOException ex) {
            logger.error("Something went wrong: " + ex.getMessage());
        }
    }

    @Then("email addresses should contain {string}")
    public void email_addresses_should_contain(String str) {
        JSONObject firstUser = jsonArray.getJSONObject(0);
        String firstEmail = firstUser.getString("email");
        Assert.assertTrue(firstEmail.contains(str.trim()));
        logger.info("Case 5 – REST API testing IS SUCCESSFUL!");
    }


}
