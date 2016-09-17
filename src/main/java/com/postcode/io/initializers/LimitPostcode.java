package com.postcode.io.initializers;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Deepak
 *
 */
public class LimitPostcode {

    private static final String AUTOCOMPLETE_URL = "https://api.postcodes.io/postcodes/";

    private static String postcode;

    private static int limit;

    public LimitPostcode(String postcode) {
        LimitPostcode.postcode = postcode;
    }

    /**
     * Limits number of postcodes matches to return. Defaults to 10. Needs to be less than 100.
     * 
     * @param limit
     * @return
     */
    public LimitPostcode limit(int limit) {
        LimitPostcode.limit = limit;
        return this;
    }

    public JSONObject asJson() throws UnirestException {
        return Unirest.get(AUTOCOMPLETE_URL.concat(postcode).concat("/autocomplete"))
                .queryString("limit", limit != 0 ? limit : 10).asJson().getBody().getObject();
    }
}
