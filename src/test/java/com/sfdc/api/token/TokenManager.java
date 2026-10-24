package com.sfdc.api.token;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;

//DONOT USE IT YET...
public class TokenManager
{

    static String accessToken;

    // Token is cached on disk so it survives across separate test runs (each run is a new JVM)
    private static final Path TOKEN_FILE = Paths.get("target", "token.properties");

    // Salesforce does not return expires_in for client_credentials, so use a safe window
    private static final Duration TOKEN_VALIDITY = Duration.ofHours(1);

    public static synchronized String getAccessToken() {
        if(accessToken == null)
        {
            accessToken = readCachedToken();
        }
        if(accessToken == null)
        {
            accessToken = generateAccessToken();
            saveToken(accessToken);
        }
        return accessToken;
    }

    private static String generateAccessToken()
    {
        HashMap<String,String> formParam = new HashMap<>();
        formParam.put("grant_type","client_credentials");
        formParam.put("client_id","3MVG97Tt9.zIfTDunOdt7b5XDuawaML5uXc2ZpCsujBb5TWEaPXt5sQKM7xHtMcxf986IX4SDQdFa6I2au6Gn");
        formParam.put("client_secret","A1E90F4231A619D25FA4C8CB76513F45CC627A2647AFA4EE01EE7B5F1BCE4B1F");

        Response response = given()
                .baseUri("https://aveggse2e-dev-ed.develop.my.salesforce.com")
                .contentType(ContentType.URLENC)
                .formParams(formParam)
                .log().all().
                when().post("/services/oauth2/token").
                then().log().all().extract().response();

        String access_token = response.path("access_token");
        if(access_token == null)
        {
            throw new IllegalStateException("Failed to generate access token. Status: "
                    + response.statusCode() + ", Body: " + response.asString());
        }
        System.out.println("Generating Access Token by calling the API");

        return access_token;
    }

    private static String readCachedToken()
    {
        if(!Files.exists(TOKEN_FILE))
        {
            return null;
        }
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(TOKEN_FILE)) {
            props.load(in);
            String token = props.getProperty("access_token");
            Instant issuedAt = Instant.ofEpochMilli(Long.parseLong(props.getProperty("issued_at")));
            if(token == null || Instant.now().isAfter(issuedAt.plus(TOKEN_VALIDITY)))
            {
                return null;
            }
            System.out.println("Reusing cached Access Token from " + TOKEN_FILE);
            return token;
        } catch (IOException | RuntimeException e) {
            System.out.println("Ignoring unreadable token cache: " + e.getMessage());
            return null;
        }
    }

    private static void saveToken(String token)
    {
        Properties props = new Properties();
        props.setProperty("access_token", token);
        props.setProperty("issued_at", String.valueOf(Instant.now().toEpochMilli()));
        try {
            Files.createDirectories(TOKEN_FILE.getParent());
            try (OutputStream out = Files.newOutputStream(TOKEN_FILE)) {
                props.store(out, "Cached Salesforce access token");
            }
        } catch (IOException e) {
            // Caching is best-effort; the in-memory token is still usable for this run
            System.out.println("Could not write token cache: " + e.getMessage());
        }
    }
}
