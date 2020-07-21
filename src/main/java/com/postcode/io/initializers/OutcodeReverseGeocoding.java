package com.postcode.io.initializers;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class OutcodeReverseGeocoding {

    private static final String OUTCODE_URL = "https://api.postcodes.io/outcodes/";

    private int limit;
    private int radius;
    private boolean latlong;
    private String latitude;
    private String longitude;
    private String outwardCode;

    protected OutcodeReverseGeocoding(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.latlong = true;
    }

    protected OutcodeReverseGeocoding(String outwardCode) {
        this.outwardCode = outwardCode;
    }

    /**
     * (not required) Limits number of postcodes matches to return. Defaults to 10. Needs to be less than 100.
     *
     * @param limit
     * @return
     */
    public OutcodeReverseGeocoding limit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * (not required) Limits number of postcodes matches to return. Defaults to 5,000m. Needs to be less than 25,000m.
     *
     * @param radius
     * @return
     */
    public OutcodeReverseGeocoding radius(int radius) {
        this.radius = radius;
        return this;
    }

    public JSONObject asJson() throws UnirestException {
        try {
            return latlong ?
                    Unirest.get(OUTCODE_URL)
                            .queryString("lon", longitude)
                            .queryString("lat", latitude)
                            .queryString("limit", getValue(this.limit, 10))
                            .queryString("radius", getValue(this.radius, 5000))
                            .asJson()
                            .getBody()
                            .getObject() :
                    Unirest.get(OUTCODE_URL.concat(outwardCode)
                            .concat("/nearest"))
                            .queryString("limit", getValue(this.limit, 10))
                            .queryString("radius", getValue(this.radius, 5000))
                            .asJson()
                            .getBody()
                            .getObject();
        } finally {
            clear();
        }
    }

    private int getValue(int value, int defaultValue) {
        return value != 0 ? value : defaultValue;
    }

    private void clear() {
        this.latitude = null;
        this.longitude = null;
        this.latlong = false;
        this.limit = 0;
        this.radius = 0;
    }
}
