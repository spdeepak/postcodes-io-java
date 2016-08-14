package com.postcode.io.initializers;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.plexus.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.postcode.io.PostCode;
import com.postcode.io.json.JsonFetcher;

/**
 * Lookup a postcode
 * 
 * @author Deepak
 *
 */
public class PostcodeLookup {

    private static final String LOOKUP_URL = "http://api.postcodes.io/postcodes/";

    private String postcode;

    private String[] postcodes;

    private String url;

    private PostcodeLookup(URL url, String postcode, String[] postcodes) {
        this.url = url.toString();
        this.postcode = postcode;
        this.postcodes = postcodes;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getUrl() {
        return url;
    }

    /**
     * {@link PostcodeLookup} {@link Builder} Class
     * 
     * @author Deepak
     *
     */
    public static class Builder {

        private String postcode;

        private String[] postcodes;

        private JSONObject json;

        public Builder() {
        }

        /**
         * Use this to create {@link PostCode}<br/>
         * Pass this to {@link PostCode#generate}
         * 
         * @return
         * @throws MalformedURLException
         */
        public PostcodeLookup build() throws MalformedURLException {
            return new PostcodeLookup(new URL(LOOKUP_URL.toString().concat(postcode)), postcode, postcodes);
        }

        public JSONObject asJson() throws Exception {
            if (!StringUtils.isEmpty(postcode)) {
                return JsonFetcher.urlToJson(new URL(LOOKUP_URL.toString().concat(postcode)));
            } else if (json != null) {
                return JsonFetcher.postURLToJson(new URL(LOOKUP_URL.toString()), json);
            } else {
                throw new IllegalArgumentException();
            }
        }

        /**
         * Use if information required is for only one {@link PostCode}
         * 
         * @param postcode
         * @return
         */
        public Builder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        /**
         * Use if information required is for multiple {@link PostCode}
         * 
         * @param postcodes
         * @return
         */
        public Builder postcodes(String[] postcodes) {
            this.postcodes = postcodes;
            createJsonPostcodes(postcodes);
            return this;
        }

        private void createJsonPostcodes(String[] postcodes) {
            JSONObject json = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (String string : postcodes) {
                jsonArray.put(string);
            }
            this.json = json.put("postcodes", jsonArray);
        }
    }

}
