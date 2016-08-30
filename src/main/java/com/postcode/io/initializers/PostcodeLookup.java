package com.postcode.io.initializers;

import java.net.URL;
import java.util.List;

import com.postcode.io.initializers.ReverseGeocoding.Reverse;

/**
 * Lookup a postcode
 * 
 * @author Deepak
 *
 */
public class PostcodeLookup {

    private String postcode;

    private String[] postcodes;

    private String url;

    PostcodeLookup(URL url, String postcode, String[] postcodes) {
        this.url = url.toString();
        this.postcode = postcode;
        this.postcodes = postcodes;
    }

    public String getPostcode() {
        return postcode;
    }

    public String[] getPostcodes() {
        return postcodes;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Lookup a postcode. Returns all available data if found. Returns 404 if postcode does not
     * exist.
     * 
     * @param postCode
     * @return
     */
    public static Postcode postcode(String postCode) {
        return new Postcode(postCode);
    }

    /**
     * Returns a list of matching postcodes and respective available data.
     * 
     * @param postcodes
     *            Postcodes to Lookup (Max Limit is 100. Default is 10)
     * @return
     */
    public static Postcode postcodes(String[] postcodes) {
        return new Postcode(postcodes);
    }

    /**
     * Returns nearest postcodes for a given longitude and latitude.
     * 
     * @param longitude
     * @param latitude
     * @return
     */
    public static ReverseGeocoding reverseGeocoding(Double longitude, Double latitude) {
        return new ReverseGeocoding(longitude, latitude);
    }

    /**
     * Bulk translates geolocations into Postcodes. Accepts up to 100 geolocations.
     * 
     * @param reverses
     * @return
     */
    public static ReverseGeocoding reverseGeocodings(List<Reverse> reverses) {
        return new ReverseGeocoding(reverses);
    }

    public static ReverseGeocoding randomPostcode() {
        return new ReverseGeocoding("");
    }

    public static ReverseGeocoding randomPostcode(String outcode) {
        return new ReverseGeocoding(outcode);
    }

}
