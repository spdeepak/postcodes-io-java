package com.postcode.io.initializers;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Lookup a postcode
 * 
 * @author Deepak
 *
 */
public class PostcodeLookup {

    private static final String LOOKUP_URL = "http://api.postcodes.io/postcodes/";

    private String postcode;

    private String url;

    private PostcodeLookup(URL url, String postcode) {
        this.url = url.toString();
        this.postcode = postcode;
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

        public Builder() {
        }

        public PostcodeLookup build() throws MalformedURLException {
            return new PostcodeLookup(new URL(LOOKUP_URL.toString().concat(postcode)), postcode);
        }

        public Builder postcode(String postcode) {
            this.postcode = postcode;
            return this;
        }
    }

}
