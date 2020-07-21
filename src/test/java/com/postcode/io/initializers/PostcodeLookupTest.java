package com.postcode.io.initializers;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.postcode.io.initializers.ReverseGeocoding.Reverse;
import com.postcode.io.json.JsonFetcher;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Deepak
 */
class PostcodeLookupTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostcodeLookupTest.class);

    @Test
    void testPostcodeLookupBuilder() throws MalformedURLException {
        PostcodeLookup postcodeLookup = PostcodeLookup.postcode("bs347np")
                .build();
        LOGGER.info("asserting postcode");
        assertEquals(postcodeLookup.getPostcode(), "bs347np");
        LOGGER.info("asserting URL");
        assertEquals(postcodeLookup.getUrl(), "http://api.postcodes.io/postcodes/bs347np");
        postcodeLookup = PostcodeLookup.postcodes(new String[] { "bs347np", "st42eu" })
                .build();
        LOGGER.info("asserting postcode");
        assertEquals(postcodeLookup.getPostcodes()[0], "bs347np");
        assertEquals(postcodeLookup.getPostcodes()[1], "st42eu");
        LOGGER.info("asserting URL");
        assertEquals(postcodeLookup.getUrl(), "http://api.postcodes.io/postcodes/");
    }

    @Test
    void testPostCodeLookupAsJsonForSinglePostcode() throws JSONException, MalformedURLException, Exception {
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/postcodes/".concat("bs347np"))
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.postcode("bs347np")
                .asJson(), JSONCompareMode.STRICT);
    }

    @Test
    void testPostCodeLookupAsJsonForMultiplePostcodes() throws JSONException, MalformedURLException, Exception {
        JSONAssert.assertEquals(JsonFetcher.urlToJson(new File(System.getProperty("user.dir")
                .concat("/src/test/resources/postcodeLookupViaPOST.json")).toURI()
                .toURL()), PostcodeLookup.postcodes(new String[] { "OX49 5NU", "M32 0JG", "NE30 1DP" })
                .asJson(), JSONCompareMode.LENIENT);
    }

    @Test
    void testReverseGeocodings() throws UnirestException, IOException, JSONException {
        List<Reverse> reverseList = new ArrayList<>();
        ReverseGeocoding reverseGeocoding = new ReverseGeocoding();
        Reverse reverse = reverseGeocoding.new Reverse();
        reverse.setLongitude(0.629834723775309);
        reverse.setLatitude(51.7923246977375);
        reverse.setLimit(0);
        reverse.setRadius(0);
        reverse.setWideSearch(false);
        reverseList.add(reverse);
        reverse = reverseGeocoding.new Reverse(-2.49690382054704, 53.5351312861402, 5, 1000, false);
        reverseList.add(reverse);
        JSONAssert.assertEquals(JsonFetcher.urlToJson(new File(System.getProperty("user.dir")
                .concat("/src/test/resources/bulkReverseGeocoding.json")).toURI()
                .toURL()), PostcodeLookup.reverseGeocodings(reverseList)
                .asJson(), JSONCompareMode.LENIENT);
    }

    @Test
    void testReverseGeocoding() throws Exception {
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/postcodes")
                .queryString("lon", 0.629834723775309)
                .queryString("lat", 51.7923246977375)
                .queryString("limit", 100)
                .queryString("radius", 2000)
                .queryString("widesearch", true)
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.nearestPostcode(0.629834723775309, 51.7923246977375)
                .limit(100)
                .radius(2000)
                .wideSearch(true)
                .asJson(), JSONCompareMode.STRICT);
    }

    @Test
    void testRandomPostcode() throws JSONException, UnirestException, IOException {
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/random/postcodes")
                .queryString("outcode", "bs347np")
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.randomPostcode()
                .outcode("bs347np")
                .asJson(), JSONCompareMode.LENIENT);
        assertTrue(PostcodeLookup.randomPostcode()
                .asJson()
                .has("result"));
        assertEquals(200, PostcodeLookup.randomPostcode()
                .asJson()
                .getInt("status"));
    }

    @Test
    void testPostcodeValidator() throws JSONException, UnirestException {
        assertTrue(PostcodeLookup.isValid("ST42EU"));
        assertFalse(PostcodeLookup.isValid("ST4"));
    }

    @Test
    void testNearestPostcode() throws Exception {
        assertTrue(PostcodeLookup.nearestPostcode("ST4 2EU")
                .asJson()
                .get("status")
                .equals(200));
        assertEquals(7, PostcodeLookup.nearestPostcode("ST4 2EU")
                .asJson()
                .getJSONArray("result")
                .length());
        assertTrue(PostcodeLookup.nearestPostcode("ST4")
                .asJson()
                .get("status")
                .equals(404));
        assertEquals(5, PostcodeLookup.nearestPostcode("ST42EU")
                .limit(5)
                .asJson()
                .getJSONArray("result")
                .length());
        assertEquals(7, PostcodeLookup.nearestPostcode("ST42EU")
                .limit(20)
                .radius(100)
                .asJson()
                .getJSONArray("result")
                .length());
    }

    @Test
    void testAutoComplete() throws Exception {
        assertEquals(200, PostcodeLookup.autocomplete("ST4")
                .asJson()
                .get("status"));
        assertEquals("ST4 1AA", PostcodeLookup.autocomplete("ST4")
                .asJson()
                .getJSONArray("result")
                .get(0));
        assertEquals(10, PostcodeLookup.autocomplete("ST4")
                .asJson()
                .getJSONArray("result")
                .length());
        assertEquals(20, PostcodeLookup.autocomplete("ST4")
                .limit(20)
                .asJson()
                .getJSONArray("result")
                .length());
    }

    @Test
    void testLookupOutwardCode() throws Exception {
        assertEquals(200, PostcodeLookup.lookupOutwardCode("ST4")
                .asJson()
                .get("status"));
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/outcodes/ST4")
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.lookupOutwardCode("ST4")
                .asJson(), JSONCompareMode.STRICT);
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/outcodes/")
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.lookupOutwardCode("")
                .asJson(), JSONCompareMode.STRICT);
    }

    @Test
    void testNearestOutwardCode() throws Exception {
        assertEquals(200, PostcodeLookup.nearestOutwardCode("ST4")
                .asJson()
                .get("status"));
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/outcodes/ST4/nearest")
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.nearestOutwardCode("ST4")
                .asJson(), JSONCompareMode.STRICT);
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/outcodes//nearest")
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.nearestOutwardCode("")
                .asJson(), JSONCompareMode.STRICT);
        assertEquals(5, PostcodeLookup.nearestOutwardCode("ST4")
                .limit(5)
                .asJson()
                .getJSONArray("result")
                .length());
        assertEquals(10, PostcodeLookup.nearestOutwardCode("ST4")
                .radius(25000)
                .asJson()
                .getJSONArray("result")
                .length());
    }

    @Test
    void testOutcodeReverseGeocoding() throws Exception {
        assertEquals(200, PostcodeLookup.outcodeReverseGeocoding(0.637189329739338, 51.8051006359272)
                .asJson()
                .get("status"));
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/outcodes/")
                .queryString("lon", 0.637189329739338)
                .queryString("lat", 51.8051006359272)
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.outcodeReverseGeocoding(0.637189329739338, 51.8051006359272)
                .asJson(), JSONCompareMode.STRICT);
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/outcodes/")
                .queryString("lon", 0.637189329739338)
                .queryString("lat", 51.8051006359272)
                .queryString("limit", 20)
                .queryString("radius", 10000)
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.outcodeReverseGeocoding(0.637189329739338, 51.8051006359272)
                .limit(20)
                .radius(10000)
                .asJson(), JSONCompareMode.STRICT);
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/outcodes/")
                .queryString("lon", 0.637189329739338)
                .queryString("lat", 51.8051006359272)
                .queryString("limit", 20)
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.outcodeReverseGeocoding(0.637189329739338, 51.8051006359272)
                .limit(20)
                .asJson(), JSONCompareMode.STRICT);
        JSONAssert.assertEquals(Unirest.get("https://api.postcodes.io/outcodes/")
                .queryString("lon", 0.637189329739338)
                .queryString("lat", 51.8051006359272)
                .queryString("radius", 10000)
                .asJson()
                .getBody()
                .getObject(), PostcodeLookup.outcodeReverseGeocoding(0.637189329739338, 51.8051006359272)
                .radius(10000)
                .asJson(), JSONCompareMode.STRICT);
    }

}