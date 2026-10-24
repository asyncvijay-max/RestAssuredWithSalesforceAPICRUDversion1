package com.sfdc.api.restapi;

import com.sfdc.api.spec.SpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class RestResourceApi {

    public static Response post(String payloadPojo,String token,String endPoint)
    {
        Response response = given(SpecBuilder.getRequestSpec())
                     //.baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com") -> moved to Spec Builder
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + token)
                    .body(payloadPojo).
                    //.log().all(). -> moved to Spec Builder
             when()
                    .post(endPoint).
             then().spec(SpecBuilder.getResponseSpec())
                //.log().all(). -> moved to Spec Builder
                .extract().response();

        return response;

    }

    public static Response get(String token,String endPoint)
    {
        Response response = given(SpecBuilder.getRequestSpec())
                 //.baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com") -> moved to Spec Builder
                .header("Authorization", "Bearer " + token).
                  //.log().all(). -> moved to Spec Builder
             when()
                .get(endPoint).
                then().spec(SpecBuilder.getResponseSpec())
                 //.log().all(). -> moved to Spec Builder
                .extract().response();

        return response;
    }

    public static Response update(String token, String payloadPojo,String endPoint)
    {
       Response response =  given(SpecBuilder.getRequestSpec())
                //.baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com") -> moved to Spec Builder
                     .header("Authorization", "Bearer " + token).
                     body(payloadPojo).
                //.log().all(). -> moved to Spec Builder
                 when()
                     .patch(endPoint).
                then().spec(SpecBuilder.getResponseSpec())
                 //.log().all(). -> moved to Spec Builder
                .extract().response();

        return response;
    }
}
