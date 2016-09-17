package com.postcode.io.initializers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.json.JSONException;
import org.junit.Test;

import com.mashape.unirest.http.exceptions.UnirestException;

public class PostcodeTest {

    @Test
    public void testBuild() throws Exception {
        Postcode pc = new Postcode("");
        try {
            pc.build();
        } catch (IllegalArgumentException e) {
            assertEquals("postcode/postcodes are mandatory", e.getMessage());
        }
        try {
            pc.asJson();
        } catch (IllegalArgumentException e) {
            assertEquals("postcode/postcodes are mandatory", e.getMessage());
        }
    }

    @Test
    public void testPostcodeValidator() throws JSONException, UnirestException {
        assertTrue(Postcode.isValid("ST42EU"));
        assertFalse(Postcode.isValid("ST4"));
    }

}
