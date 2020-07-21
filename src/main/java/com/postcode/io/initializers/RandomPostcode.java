package com.postcode.io.initializers;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Deepak
 *
 */
public class RandomPostcode {

    private static final String RANDOM_LOOKUP_URL = "https://api.postcodes.io/random/postcodes";

    private String outcode = "";

    public RandomPostcode() {
    }

    public RandomPostcode outcode(String outcode) {
        this.outcode = outcode;
        return this;
    }

    public JSONObject asJson() throws IOException, UnirestException {
        return Unirest.get(RANDOM_LOOKUP_URL).queryString("outcode", outcode).asJson().getBody().getObject();
    }
}
