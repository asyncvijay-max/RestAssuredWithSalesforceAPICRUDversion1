package com.sfdc.api.token;

import com.sfdc.api.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GetAccessToken {

    static String access_token = "00DjV0000035sTI!AQEAQDDjDWvLwv0rPAJXe0n_w6nkbRLiN76DNjiA9aCAiocVg_HBESGdQ7J0PUuAOX_35U38DNV8hpym0mRXtkkxewzhFbQI";

    // Generates Access Token,it doesnot have expiry date set in the payload.
    //Call once manually by running in postman tool,copy the access token to a variable in  api layer and use it in every CRUD call
    public static String tokenCreation()
    {
        HashMap<String,String> formParam = new HashMap<>();
        formParam.put("grant_type","client_credentials");
        formParam.put("client_id", ConfigLoader.getInstance().getClientId());
        formParam.put("client_secret",ConfigLoader.getInstance().getClientSecret());




        Response response = given()
                .baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com")
                .contentType(ContentType.URLENC)
                .formParams(formParam)
                .log().all().
                when().post("/services/oauth2/token").
                then().log().all().extract().response();

        return response.path("access_token");

    }

    public static String theAccessToken()
    {
        return access_token;
    }
}
