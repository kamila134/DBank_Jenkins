package co.wedevx.digitalbank.automation.api.utils;

import co.wedevx.digitalbank.automation.ui.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestHttpRequest {

    //base uri
    //add headers (content type, accept + auth)
    //authToken

    public static RequestSpecification requestSpecificationDB = RestAssured.given();
    public final static String authToken;

    static {
        requestSpecificationDB.baseUri(ConfigReader.getPropertiesValue("digitalbank.api.baseuri"));
        authToken = generateAuthToken();
        addHeaders();
    }

    private static String generateAuthToken() {
        Response authResponse = requestSpecificationDB
                .queryParam("username", ConfigReader.getPropertiesValue("digitalbank.api.admin.username"))
                .queryParam("password", ConfigReader.getPropertiesValue("digitalbank.api.admin.password"))
                .when()
                .post("/auth");
        System.out.println(authResponse.jsonPath().getString("authToken"));
        return authResponse.jsonPath().getString("authToken");
    }

    private static void addHeaders() {
        requestSpecificationDB
                .header("Authorization", "Bearer " + authToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    public static Response sendPostRequestWithPathParams(String pathParamKey, Object pathParamValue, String endpoint, String body) {
        return requestSpecificationDB
                .pathParam(pathParamKey,pathParamValue)
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response sendDeleteRequestWithPathParams(String pathParamKey, Object pathParamValue, String endpoint) {
        return requestSpecificationDB
                .pathParam(pathParamKey,pathParamValue)
                .when()
                .delete(endpoint);
    }

}
