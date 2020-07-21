package com.postcode.io.initializers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.postcode.io.initializers.ReverseGeocoding.Reverse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONException;

import java.util.List;

/**
 * Lookup a postcode
 *
 * @author Deepak
 */
@Data
@AllArgsConstructor
public class PostcodeLookup {

    private String url;
    private String postcode;
    private String[] postcodes;

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
     * @param postcodes Postcodes to Lookup (Max Limit is 100. Default is 10)
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
    public static ReverseGeocoding nearestPostcode(Double longitude, Double latitude) {
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

    /**
     * Convenience method to get random postcode and all available data for that postcode.
     *
     * @return
     */
    public static RandomPostcode randomPostcode() {
        return new RandomPostcode();
    }

    /**
     * Convenience method to validate a postcode. Returns true or false (meaning valid or invalid
     * respectively)
     *
     * @param postcode
     * @return
     * @throws JSONException
     * @throws UnirestException
     */
    public static boolean isValid(String postcode) throws JSONException, UnirestException {
        return Postcode.isValid(postcode);
    }

    /**
     * Convenience method to get nearest outcodes for a given outcode.
     *
     * @param postcode
     * @return
     */
    public static NearestPostcode nearestPostcode(String postcode) {
        return new NearestPostcode(postcode);
    }

    /**
     * Convenience method to return an list of matching postcodes.
     *
     * @param postcode
     * @return
     */
    public static LimitPostcode autocomplete(String postcode) {
        return new LimitPostcode(postcode);
    }

    /**
     * Geolocation data for the centroid of the outward code specified. The outward code represents
     * the first half of any postcode (separated by a space).
     *
     * @param outwardCode
     * @return
     */
    public static OutwardCode lookupOutwardCode(String outwardCode) {
        return new OutwardCode(outwardCode);
    }

    /**
     * Returns nearest outcodes for a given outcode.
     *
     * @param outwardCode
     * @return
     */
    public static OutcodeReverseGeocoding nearestOutwardCode(String outwardCode) {
        return new OutcodeReverseGeocoding(outwardCode);
    }

    /**
     * Returns nearest outcodes for a given longitude and latitude.
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public static OutcodeReverseGeocoding outcodeReverseGeocoding(Double longitude, Double latitude) {
        return new OutcodeReverseGeocoding(String.valueOf(longitude), String.valueOf(latitude));
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

}
