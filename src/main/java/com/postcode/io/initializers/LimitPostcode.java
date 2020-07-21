package com.postcode.io.initializers;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Deepak
 *
 */
public class LimitPostcode {

    private static final String POSTCODE_LIMIT_URL = "https://api.postcodes.io/postcodes/";

    private int limit;
    private String postcode;

    public LimitPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Limits number of postcodes matches to return. Defaults to 10. Needs to be less than 100.
     * 
     * @param limit
     * @return
     */
    public LimitPostcode limit(int limit) {
        this.limit = limit;
        return this;
    }

    public JSONObject asJson() throws UnirestException {
        return Unirest.get(POSTCODE_LIMIT_URL.concat(postcode).concat("/autocomplete"))
                .queryString("limit", limit != 0 ? limit : 10).asJson().getBody().getObject();
    }
}
