package com.postcode.io.initializers;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Deepak
 *
 */
public class OutwardCode {

    private static final String OUTWARD_CODE_URL = "https://api.postcodes.io/outcodes/";

    private static String outwardCode;

    protected OutwardCode(String outwardCode) {
        OutwardCode.outwardCode = outwardCode;
    }

    public JSONObject asJson() throws UnirestException {
        try {
            return Unirest.get(OUTWARD_CODE_URL.concat(outwardCode)).asJson().getBody().getObject();
        } finally {
            clear();
        }
    }

    private void clear() {
        outwardCode = null;
    }
}
