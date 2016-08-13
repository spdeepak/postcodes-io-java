package com.postcode.io.initializers;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;

import org.junit.Test;

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

}