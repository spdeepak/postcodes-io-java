package com.postcode.io.initializers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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

}
