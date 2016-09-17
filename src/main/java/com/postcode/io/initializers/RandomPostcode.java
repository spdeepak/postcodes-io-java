package com.postcode.io.initializers;

import java.io.IOException;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Deepak
 *
 */
public class RandomPostcode {

    private static final String RANDOM_LOOKUP_URL = "https://api.postcodes.io/random/postcodes";

    private static String outcode = "";

    public RandomPostcode() {
    }

    public RandomPostcode outcode(String outcode) {
        RandomPostcode.outcode = outcode;
        return this;
    }

    public JSONObject asJson() throws IOException, UnirestException {
        return Unirest.get(RANDOM_LOOKUP_URL).queryString("outcode", outcode).asJson().getBody().getObject();
    }
}
