package com.sfdc.api.sobject;

import com.sfdc.api.restapi.RestResourceApi;
import com.sfdc.api.spec.SpecBuilder;
import com.sfdc.api.token.TokenManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SalesforceApi {

    private static String access_token = "00DjV0000035sTI!AQEAQDDjDWvLwv0rPAJXe0n_w6nkbRLiN76DNjiA9aCAiocVg_HBESGdQ7J0PUuAOX_35U38DNV8hpym0mRXtkkxewzhFbQI";

    public static Response post(String accountPayload)
    {
        return RestResourceApi.post(accountPayload,access_token,"/services/data/v66.0/sobjects/Account");

    }

    public static Response get(String accountId)
    {
        String endPoint = "/services/data/v66.0/sobjects/Account/"+ accountId;
        return RestResourceApi.get(access_token,endPoint);



    //OLD code before Rest Resource API Layer
    //                given(SpecBuilder.getRequestSpec())
    //                    //.baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com")
    //                    .header("Authorization", "Bearer " + access_token)
    //                    .log().all().
    //                when()
    //                    .get("/services/data/v66.0/sobjects/Account/"+ accountId).
    //                then().spec(SpecBuilder.getResponseSpec())
    //                    //.log().all()
    //                    .extract().response();


    }



    public static Response update(String accountId,String accountPayLoad)
    {
        //payload is { "Phone": "{{$randomPhoneNumber}}"} since patch,not sending full body for patch

        String endPoint = "/services/data/v66.0/sobjects/Account/"+ accountId;

        Response response = RestResourceApi.update(access_token,accountPayLoad,endPoint);
        return response;

        //or ,write in single line
        //return RestResourceApi.update(access_token,accountPayLoad,endPoint);

//                        given(SpecBuilder.getRequestSpec())
//                    //.baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com")
//                    .header("Authorization", "Bearer " + access_token)
//                    .body(accountPayLoad)
//                    .log().all().
//                when()
//                    .patch("/services/data/v66.0/sobjects/Account/"+ accountId).
//                then().spec(SpecBuilder.getResponseSpec())
//                        //.log().all()
//                        .extract().response();


    }

    //Use for negative test where invalid Token is send,its a Overloaded Post Method
    //Need to refactor to add RestResource layer
    public static Response post(int invalidToken,String accountPayload)
    {
        Response response =
                given()
                        .baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com")
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + invalidToken)
                        .body(accountPayload)
                        .log().all().
                        when()
                        .post("/services/data/v66.0/sobjects/Account").
                        then()
                        .log().all()
                        .extract().response();

        return response;
    }




}
