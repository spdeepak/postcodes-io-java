package com.postcode.io.initializers;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.plexus.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.postcode.io.PostCodeDetails;
import com.postcode.io.json.JsonFetcher;

/**
 * @author Deepak
 *
 */
public class Postcode {

    private static final String LOOKUP_URL = "http://api.postcodes.io/postcodes/";

    private String postcode;

    private String[] postcodes;

    private JSONObject json;

    public Postcode(String postcode) {
        this.postcode = postcode;
    }

    public Postcode(String[] postcodes) {
        this.postcodes = postcodes;
        createJsonPostcodes(this.postcodes);
    }

    private void createJsonPostcodes(String[] postcodes) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (String string : postcodes) {
            jsonArray.put(string);
        }
        this.json = json.put("postcodes", jsonArray);
    }

    /**
     * Get JSON for the given {@link #postcode} / {@link #postcodes}
     * 
     * @return
     * @throws Exception
     */
    public JSONObject asJson() throws Exception {
        if (!StringUtils.isEmpty(postcode)) {
            return JsonFetcher.urlToJson(new URL(LOOKUP_URL.toString().concat(postcode)));
        } else if (json != null) {
            return JsonFetcher.postURLToJson(new URL(LOOKUP_URL.toString()), json);
        } else {
            throw new IllegalArgumentException("postcode/postcodes are mandatory");
        }
    }

    /**
     * Use this to create {@link PostCodeDetails}<br/>
     * Pass this to {@link PostCodeDetails#generate}
     * 
     * @return
     * @throws MalformedURLException
     */
    public PostcodeLookup build() throws MalformedURLException {
        if (postcode != null && !postcode.trim().isEmpty()) {
            return new PostcodeLookup(new URL(LOOKUP_URL.toString().concat(postcode)), postcode, postcodes);
        } else if (postcode == null && postcodes != null) {
            return new PostcodeLookup(new URL(LOOKUP_URL.toString()), postcode, postcodes);
        } else {
            throw new IllegalArgumentException("postcode/postcodes are mandatory");
        }
    }

}
