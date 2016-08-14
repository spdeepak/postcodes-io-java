package com.postcode.io.initializers;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;

import org.junit.Test;

public class PostcodeTest {

    @Test
    public void testBuild() throws MalformedURLException {
        Postcode pc = new Postcode("");
        try {
            pc.build();
        } catch (IllegalArgumentException e) {
            assertEquals("postcode/postcodes are mandatory", e.getMessage());
        }
    }

}
