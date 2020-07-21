package com.postcode.io.initializers;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * @author Deepak
 */
public class OutwardCode {

    private static final String OUTWARD_CODE_URL = "https://api.postcodes.io/outcodes/";

    private String outwardCode;

    protected OutwardCode(String outwardCode) {
        this.outwardCode = outwardCode;
    }

    public JSONObject asJson() throws UnirestException {
        return Unirest.get(OUTWARD_CODE_URL.concat(outwardCode))
                .asJson()
                .getBody()
                .getObject();
    }
}
