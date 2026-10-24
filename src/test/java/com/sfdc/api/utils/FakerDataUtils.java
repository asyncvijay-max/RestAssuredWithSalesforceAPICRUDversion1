package com.sfdc.api.utils;

import com.github.javafaker.Faker;

public class FakerDataUtils {

    public static String  getFakeTestName()
    {
        Faker faker = new Faker();
        return "TestAccount-"+faker.regexify("[a-zA-Z0-9 ,_-]{9}");
    }



}


/**
     *         <dependency>
     *             <groupId>com.github.javafaker</groupId>
     *             <artifactId>javafaker</artifactId>
     *             <version>1.0.2</version>
     *         </dependency>
 */