package com.sfdc.api.token;

import com.sfdc.api.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GetAccessToken {


    // Generates Access Token,it doesnot have expiry date set in the payload.
    //Call once manually by running in postman tool,copy the access token to a variable in  api layer and use it in every CRUD call
    public static String tokenCreation()
    {
        HashMap<String,String> formParam = new HashMap<>();
        formParam.put("grant_type","client_credentials");
        formParam.put("client_id", ConfigLoader.getInstance().getClientId());
        formParam.put("client_secret",ConfigLoader.getInstance().getClientSecret());


        //formParam.put("client_id","3MVG97Tt9.zIfTDunOdt7b5XDuawaML5uXc2ZpCsujBb5TWEaPXt5sQKM7xHtMcxf986IX4SDQdFa6I2au6Gn");
        //formParam.put("client_secret","A1E90F4231A619D25FA4C8CB76513F45CC627A2647AFA4EE01EE7B5F1BCE4B1F");

        Response response = given()
                .baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com")
                .contentType(ContentType.URLENC)
                .formParams(formParam)
                .log().all().
                when().post("/services/oauth2/token").
                then().log().all().extract().response();

        return response.path("access_token");

    }
}
