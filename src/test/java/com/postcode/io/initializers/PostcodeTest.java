package com.postcode.io.initializers;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostcodeTest {

    @Test
    void testBuild() throws Exception {
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
    void testPostcodeValidator() throws JSONException, UnirestException {
        assertTrue(Postcode.isValid("ST42EU"));
        assertFalse(Postcode.isValid("ST4"));
    }

}
