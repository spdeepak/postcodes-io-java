package com.postcode.io.initializers;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class OutcodeReverseGeocoding {

    private static final String OUTWARD_CODE_URL = "https://api.postcodes.io/outcodes/";

    private static String outwardCode;

    private static boolean nearest = false;

    private static boolean latlong = false;

    private static String latitude;

    private static String longitude;

    private static int limit;

    private static int radius;

    protected OutcodeReverseGeocoding(String longitude, String latitude) {
        OutcodeReverseGeocoding.longitude = longitude;
        OutcodeReverseGeocoding.latitude = latitude;
        OutcodeReverseGeocoding.latlong = true;
    }

    protected OutcodeReverseGeocoding(String outwardCode, boolean nearest) {
        OutcodeReverseGeocoding.outwardCode = outwardCode;
        OutcodeReverseGeocoding.nearest = nearest;
    }

    /**
     * (not required) Limits number of postcodes matches to return. Defaults to 10. Needs to be less
     * than 100.
     * 
     * @param limit
     * @return
     */
    public OutcodeReverseGeocoding limit(int limit) {
        OutcodeReverseGeocoding.limit = limit;
        return this;
    }

    /**
     * (not required) Limits number of postcodes matches to return. Defaults to 5,000m. Needs to be
     * less than 25,000m.
     * 
     * @param radius
     * @return
     */
    public OutcodeReverseGeocoding radius(int radius) {
        OutcodeReverseGeocoding.radius = radius;
        return this;
    }

    public JSONObject asJson() throws UnirestException {
        try {
            if (nearest) {
                return Unirest.get(OUTWARD_CODE_URL.concat(outwardCode).concat("/nearest"))
                        .queryString("limit", limit != 0 ? limit : 10)
                        .queryString("radius", radius != 0 ? radius : 5000).asJson().getBody().getObject();
            }
            if (latlong) {
                return Unirest.get(OUTWARD_CODE_URL).queryString("lon", longitude).queryString("lat", latitude)
                        .queryString("limit", limit != 0 ? limit : 10)
                        .queryString("radius", radius != 0 ? radius : 5000).asJson().getBody().getObject();
            }
            return Unirest.get(OUTWARD_CODE_URL.concat(outwardCode)).asJson().getBody().getObject();
        } finally {
            clear();
        }
    }

    private void clear() {
        latitude = null;
        longitude = null;
        nearest = false;
        latlong = false;
        limit = 0;
        radius = 0;
    }
}
