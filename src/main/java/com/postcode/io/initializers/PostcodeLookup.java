package com.postcode.io.initializers;

import java.net.URL;
import java.util.List;

import org.json.JSONException;

import com.mashape.unirest.http.exceptions.UnirestException;
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

    public static RandomPostcode randomPostcode() {
        return new RandomPostcode();
    }

    public static boolean isValid(String postcode) throws JSONException, UnirestException {
        return Postcode.isValid(postcode);
    }

    public static NearestPostcode nearestPostcode(String postcode) {
        return new NearestPostcode(postcode);
    }

    public static LimitPostcode autocomplete(String postcode) {
        return new LimitPostcode(postcode);
    }

    public static OutwardCode lookupOutwardCode(String outwardCode) {
        return new OutwardCode(outwardCode);
    }

    public static OutcodeReverseGeocoding nearestOutwardCode(String outwardCode) {
        return new OutcodeReverseGeocoding(outwardCode, true);
    }

    public static OutcodeReverseGeocoding outcodeReverseGeocoding(Double longitude, Double latitude) {
        return new OutcodeReverseGeocoding(String.valueOf(longitude), String.valueOf(latitude));
    }

}
