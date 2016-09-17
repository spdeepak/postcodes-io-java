package com.postcode.io.initializers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.postcode.io.initializers.ReverseGeocoding.Reverse;
import com.postcode.io.json.JsonFetcher;

/**
 * @author Deepak
 *
 */
public class PostcodeLookupTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostcodeLookupTest.class);

    @Test
    public void testPostcodeLookupBuilder() throws MalformedURLException {
        PostcodeLookup postcodeLookup = PostcodeLookup.postcode("bs347np").build();
        LOGGER.info("asserting postcode");
        assertEquals(postcodeLookup.getPostcode(), "bs347np");
        LOGGER.info("asserting URL");
        assertEquals(postcodeLookup.getUrl(), "http://api.postcodes.io/postcodes/bs347np");
        postcodeLookup = PostcodeLookup.postcodes(new String[] { "bs347np", "st42eu" }).build();
        LOGGER.info("asserting postcode");
        assertEquals(postcodeLookup.getPostcodes()[0], "bs347np");
        assertEquals(postcodeLookup.getPostcodes()[1], "st42eu");
        LOGGER.info("asserting URL");
        assertEquals(postcodeLookup.getUrl(), "http://api.postcodes.io/postcodes/");
    }

    @Test
    public void testPostCodeLookupAsJsonForSinglePostcode() throws JSONException, MalformedURLException, Exception {
        JSONAssert.assertEquals(JsonFetcher
                .urlToJson(new File(System.getProperty("user.dir").concat("/src/test/resources/postcodeLookup.json"))
                        .toURI().toURL()),
                PostcodeLookup.postcode("bs347np").asJson(), JSONCompareMode.STRICT);
    }

    @Test
    public void testPostCodeLookupAsJsonForMultiplePostcodes() throws JSONException, MalformedURLException, Exception {
        JSONAssert.assertEquals(JsonFetcher.urlToJson(
                new File(System.getProperty("user.dir").concat("/src/test/resources/postcodeLookupViaPOST.json"))
                        .toURI().toURL()),
                PostcodeLookup.postcodes(new String[] { "OX49 5NU", "M32 0JG", "NE30 1DP" }).asJson(),
                JSONCompareMode.LENIENT);
    }

    @Test
    public void testReverseGeocodings() throws MalformedURLException, UnirestException, IOException {
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
        JSONAssert.assertEquals(
                JsonFetcher.urlToJson(
                        new File(System.getProperty("user.dir").concat("/src/test/resources/bulkReverseGeocoding.json"))
                                .toURI().toURL()),
                PostcodeLookup.reverseGeocodings(reverseList).asjson(), JSONCompareMode.LENIENT);
    }

    @Test
    public void testReverseGeocoding() throws Exception {
        JSONAssert.assertEquals(
                Unirest.get("https://api.postcodes.io/postcodes").queryString("lon", 0.629834723775309)
                        .queryString("lat", 51.7923246977375).queryString("limit", 100).queryString("radius", 2000)
                        .queryString("widesearch", true).asJson().getBody().getObject(),
                PostcodeLookup.reverseGeocoding(0.629834723775309, 51.7923246977375).limit(100).radius(2000)
                        .wideSearch(true).asjson(),
                JSONCompareMode.STRICT);
    }

    @Test
    public void testRandomPostcode() throws JSONException, UnirestException, IOException {
        JSONAssert.assertEquals(
                Unirest.get("https://api.postcodes.io/random/postcodes").queryString("outcode", "bs347np").asJson()
                        .getBody().getObject(),
                PostcodeLookup.randomPostcode("bs347np").asjson(), JSONCompareMode.LENIENT);
        assertTrue(PostcodeLookup.randomPostcode().asjson().has("result"));
        assertEquals(200, PostcodeLookup.randomPostcode().asjson().getInt("status"));
    }

    @Test
    public void testPostcodeValidator() throws JSONException, UnirestException {
        assertTrue(PostcodeLookup.validate("ST42EU"));
        assertFalse(PostcodeLookup.validate("ST4"));
    }

    @Test
    public void testNearestPostcode() throws Exception {
        assertTrue(PostcodeLookup.nearestPostcode("ST4 2EU").asJson().get("status").equals(200));
        assertEquals(7, PostcodeLookup.nearestPostcode("ST4 2EU").asJson().getJSONArray("result").length());
        assertTrue(PostcodeLookup.nearestPostcode("ST4").asJson().get("status").equals(404));
        assertEquals(5, PostcodeLookup.nearestPostcode("ST42EU").limit(5).asJson().getJSONArray("result").length());
        assertEquals(7, PostcodeLookup.nearestPostcode("ST42EU").limit(20).radius(100).asJson().getJSONArray("result")
                .length());
    }

}