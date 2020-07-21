package com.postcode.io.initializers;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * @author Deepak
 */
public class NearestPostcode {

    private static final String LOOKUP_URL = "http://api.postcodes.io/postcodes/";

    private int limit;
    private int radius;
    private String postcode;

    public NearestPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Limits number of postcodes matches to return. Defaults to 10. Needs to be less than 100.
     *
     * @param limit
     * @return
     */
    public NearestPostcode limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Limits number of postcodes matches to return. Defaults to 100m. Needs to be less than 2,000m.
     *
     * @param radius
     * @return
     */
    public NearestPostcode radius(int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Get JSON for the given {@link #postcode}
     *
     * @return
     * @throws Exception
     */
    public JSONObject asJson() throws UnirestException {
        return Unirest.get(LOOKUP_URL.concat(postcode)
                .concat("/nearest"))
                .queryString("limit", limit != 0 ? limit : 10)
                .queryString("radius", radius != 0 ? radius : 100)
                .asJson()
                .getBody()
                .getObject();
    }
}
