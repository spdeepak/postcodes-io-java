package com.postcode.io.initializers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.postcode.io.json.JsonFetcher;

/**
 * @author Deepak
 *
 */
public class PostcodeLookupTest {

    @Test
    public void testPostcodeLookupBuilder() throws MalformedURLException {
        PostcodeLookup postcodeLookup = new PostcodeLookup.Builder().postcode("bs347np").build();
        assertEquals(postcodeLookup.getPostcode(), "bs347np");
        assertEquals(postcodeLookup.getUrl(), "http://api.postcodes.io/postcodes/bs347np");
    }

    @Test
    public void testPostCodeLookupAsJsonForSinglePostcode() throws JSONException, MalformedURLException, Exception {
        JSONAssert.assertEquals(
                JsonFetcher.urlToJson(
                        new File(System.getProperty("user.dir").concat("/src/test/resources/postcodeLookup.json"))
                                .toURI().toURL()),
                new PostcodeLookup.Builder().postcode("bs347np").asJson(), JSONCompareMode.STRICT);

    }

    @Test
    public void testPostCodeLookupAsJsonForMultiplePostcodes() throws JSONException, MalformedURLException, Exception {
        JSONAssert.assertEquals(JsonFetcher.urlToJson(
                new File(System.getProperty("user.dir").concat("/src/test/resources/postcodeLookupViaPOST.json"))
                        .toURI().toURL()),
                new PostcodeLookup.Builder().postcodes(new String[] { "OX49 5NU", "M32 0JG", "NE30 1DP" }).asJson(),
                JSONCompareMode.LENIENT);
    }

}