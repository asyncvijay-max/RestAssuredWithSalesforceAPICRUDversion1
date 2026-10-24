package com.sfdc.api.tests;

import com.sfdc.api.sobject.SalesforceApi;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SalesforceCRUDAPITest {


    @Test
    public void createNewAccountTest()
    {
        String payload = "{\n" +
                "  \"Name\": \"IrityAV Test Account\",\n" +
                "  \"Phone\": \"990345567342\",\n" +
                "  \"Website\": \"https://exampleitnorway.com\",\n" +
                "  \"Industry\": \"Technology\",\n" +
                "  \"BillingCity\": \"London\",\n" +
                "  \"BillingCountry\": \"United Kingdom\"\n" +
                "}";

        //Pass The Account Payload as paramater to post() method
        Response response = SalesforceApi.post(payload);

        assertThat(response.statusCode(),equalTo(201));

        System.out.println("The response Id is " + response.path("id"));


        /**
         * {
         *     "id": "001jV00000HwOL7QAN",
         *     "success": true,
         *     "errors": []
         * }
         *
         * ==============norway =======
         * {
         *     "Name": "NorwayAV Test Account",
         *     "Phone": "252567342",
         *     "Website": "https://examplenorway.com",
         *     "Industry": "Technology",
         *     "BillingCity": "London",
         *     "BillingCountry": "United Kingdom"
         *
         *     response is {
         *     "id": "001jV00000HwXg9QAF",
         *     "success": true,
         *     "errors": [
         *
         *     ]
         * }
         * }
         * /

        //Few  Test data
        // id is 001jV00000HsWabQAF -Swiss Account
        //001jV00000HwOL7QAN  - John Lewis
    }




        /**
         * payload sample:
         * {
         *     "attributes": {
         *         "type": "Account",
         *         "url": "/services/data/v66.0/sobjects/Account/001jV00000HwOL7QAN"
         *     },
         *     "Id": "001jV00000HwOL7QAN",
         *     "IsDeleted": false,
         *     "MasterRecordId": null,
         *     "Name": "johnLewisTest",
         *     "Type": null,
         *     "ParentId": null,
         *     "BillingStreet": null,
         *     "BillingCity": "London",
         *     "BillingState": null,
         *     "BillingPostalCode": null,
         *     "BillingCountry": "United Kingdom",
         *     "BillingStateCode": null,
         *     "BillingCountryCode": "GB",
         *     "BillingLatitude": null,
         *     "BillingLongitude": null,
         *     "BillingGeocodeAccuracy": null,
         *     "BillingAddress": {
         *         "city": "London",
         *         "country": "United Kingdom",
         *         "countryCode": "GB",
         *         "geocodeAccuracy": null,
         *         "latitude": null,
         *         "longitude": null,
         *         "postalCode": null,
         *         "state": null,
         *         "stateCode": null,
         *         "street": null
         *     },
         *     "ShippingStreet": null,
         *     "ShippingCity": null,
         *     "ShippingState": null,
         *     "ShippingPostalCode": null,
         *     "ShippingCountry": null,
         *     "ShippingStateCode": null,
         *     "ShippingCountryCode": null,
         *     "ShippingLatitude": null,
         *     "ShippingLongitude": null,
         *     "ShippingGeocodeAccuracy": null,
         *     "ShippingAddress": null,
         *     "Phone": "23443434",
         *     "Fax": null,
         *     "AccountNumber": null,
         *     "Website": "https://example.com",
         *     "PhotoUrl": "/services/images/photo/001jV00000HwOL7QAN",
         *     "Sic": null,
         *     "Industry": "Technology",
         *     "AnnualRevenue": null,
         *     "NumberOfEmployees": null,
         *     "Ownership": null,
         *     "TickerSymbol": null,
         *     "Description": null,
         *     "Rating": null,
         *     "Site": null,
         *     "OwnerId": "005jV000002sQ61QAE",
         *     "CreatedDate": "2026-10-24T06:21:19.000+0000",
         *     "CreatedById": "005jV000002sQ61QAE",
         *     "LastModifiedDate": "2026-10-24T06:21:19.000+0000",
         *     "LastModifiedById": "005jV000002sQ61QAE",
         *     "SystemModstamp": "2026-10-24T06:21:19.000+0000",
         *     "LastActivityDate": null,
         *     "LastViewedDate": "2026-10-24T06:21:19.000+0000",
         *     "LastReferencedDate": "2026-10-24T06:21:19.000+0000",
         *     "Jigsaw": null,
         *     "JigsawCompanyId": null,
         *     "CleanStatus": "Pending",
         *     "AccountSource": null,
         *     "DunsNumber": null,
         *     "Tradestyle": null,
         *     "NaicsCode": null,
         *     "NaicsDesc": null,
         *     "YearStarted": null,
         *     "SicDesc": null,
         *     "CustomerPriority__c": null,
         *     "SLA__c": null,
         *     "Active__c": null,
         *     "NumberofLocations__c": null,
         *     "UpsellOpportunity__c": null,
         *     "SLASerialNumber__c": null,
         *     "SLAExpirationDate__c": null
         * }
         */

    }

    @Test
    public void getaSingleAccountTest()
    {
        //Pass The AccountId as paramater to get() method
        Response response = SalesforceApi.get("001jV00000HwOL7QAN");

        //Extract actual value from response using path() method
        String name = response.path("Name");
        String billingCity = response.path("BillingCity");


        //Assertions here
        assertThat(response.statusCode(), equalTo(200));
        assertThat(name, equalTo("johnLewisTest"));
        assertThat(billingCity, equalTo("London"));

    }


    @Test
    public void updateASingleAccountTest()
    {
        String payload = "{\n" +
                "\"Phone\": \"987654321\"\n" +
                "}";


        Response response = SalesforceApi.update("001jV00000HsWabQAF",payload);

        String phone = response.path("Phone");

        //Assertions here
        assertThat(response.statusCode(),equalTo(200));
        assertThat(phone,is(equalTo("987654321")));

        /**
         * payload sample:
         * {
         *     "attributes": {
         *         "type": "Account",
         *         "url": "/services/data/v66.0/sobjects/Account/001jV00000HwOL7QAN"
         *     },
         *     "Id": "001jV00000HwOL7QAN",
         *     "IsDeleted": false,
         *     "MasterRecordId": null,
         *     "Name": "johnLewisTest",
         *     "Type": null,
         *     "ParentId": null,
         *     "BillingStreet": null,
         *     "BillingCity": "London",
         *     "BillingState": null,
         *     "BillingPostalCode": null,
         *     "BillingCountry": "United Kingdom",
         *     "BillingStateCode": null,
         *     "BillingCountryCode": "GB",
         *     "BillingLatitude": null,
         *     "BillingLongitude": null,
         *     "BillingGeocodeAccuracy": null,
         *     "BillingAddress": {
         *         "city": "London",
         *         "country": "United Kingdom",
         *         "countryCode": "GB",
         *         "geocodeAccuracy": null,
         *         "latitude": null,
         *         "longitude": null,
         *         "postalCode": null,
         *         "state": null,
         *         "stateCode": null,
         *         "street": null
         *     },
         *     "ShippingStreet": null,
         *     "ShippingCity": null,
         *     "ShippingState": null,
         *     "ShippingPostalCode": null,
         *     "ShippingCountry": null,
         *     "ShippingStateCode": null,
         *     "ShippingCountryCode": null,
         *     "ShippingLatitude": null,
         *     "ShippingLongitude": null,
         *     "ShippingGeocodeAccuracy": null,
         *     "ShippingAddress": null,
         *     "Phone": "23443434",
         *     "Fax": null,
         *     "AccountNumber": null,
         *     "Website": "https://example.com",
         *     "PhotoUrl": "/services/images/photo/001jV00000HwOL7QAN",
         *     "Sic": null,
         *     "Industry": "Technology",
         *     "AnnualRevenue": null,
         *     "NumberOfEmployees": null,
         *     "Ownership": null,
         *     "TickerSymbol": null,
         *     "Description": null,
         *     "Rating": null,
         *     "Site": null,
         *     "OwnerId": "005jV000002sQ61QAE",
         *     "CreatedDate": "2026-10-24T06:21:19.000+0000",
         *     "CreatedById": "005jV000002sQ61QAE",
         *     "LastModifiedDate": "2026-10-24T06:21:19.000+0000",
         *     "LastModifiedById": "005jV000002sQ61QAE",
         *     "SystemModstamp": "2026-10-24T06:21:19.000+0000",
         *     "LastActivityDate": null,
         *     "LastViewedDate": "2026-10-24T06:21:19.000+0000",
         *     "LastReferencedDate": "2026-10-24T06:21:19.000+0000",
         *     "Jigsaw": null,
         *     "JigsawCompanyId": null,
         *     "CleanStatus": "Pending",
         *     "AccountSource": null,
         *     "DunsNumber": null,
         *     "Tradestyle": null,
         *     "NaicsCode": null,
         *     "NaicsDesc": null,
         *     "YearStarted": null,
         *     "SicDesc": null,
         *     "CustomerPriority__c": null,
         *     "SLA__c": null,
         *     "Active__c": null,
         *     "NumberofLocations__c": null,
         *     "UpsellOpportunity__c": null,
         *     "SLASerialNumber__c": null,
         *     "SLAExpirationDate__c": null
         * }
         */

    }


}
