package com.postcode.io.initializers;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;

public class NearestPostcode {

    private static final String LOOKUP_URL = "http://api.postcodes.io/postcodes/";

    private static String postcode;

    private static int limit;

    private static int radius;

    public NearestPostcode(String postcode) {
        NearestPostcode.postcode = postcode;
    }

    /**
     * Limits number of postcodes matches to return. Defaults to 10. Needs to be less than 100.
     * 
     * @param limit
     * @return
     */
    public NearestPostcode limit(int limit) {
        NearestPostcode.limit = limit;
        return this;
    }

    /**
     * Limits number of postcodes matches to return. Defaults to 100m. Needs to be less than 2,000m.
     * 
     * @param radius
     * @return
     */
    public NearestPostcode radius(int radius) {
        NearestPostcode.radius = radius;
        return this;
    }

    /**
     * Get JSON for the given {@link #postcode} / {@link #postcodes}
     * 
     * @return
     * @throws Exception
     */
    public JSONObject asJson() throws Exception {
        return Unirest.get(LOOKUP_URL.concat(postcode).concat("/nearest")).queryString("limit", limit != 0 ? limit : 10)
                .queryString("radius", radius != 0 ? radius : 100).asJson().getBody().getObject();
    }
}
