package com.postcode.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.net.MalformedURLException;

import org.junit.Test;

import com.postcode.io.initializers.PostcodeLookup;

/**
 * @author Deepak
 *
 */
public class PostCodeTest {

    @Test
    public void test() throws MalformedURLException {
        PostCode postCode = PostCode.generate(new PostcodeLookup.Builder().postcode("bs347np").build());
        assertEquals("BS34 7NP", postCode.getPostcode());
        assertEquals(Integer.valueOf(1), postCode.getQuality());
        assertEquals(Integer.valueOf(360605), postCode.getEastings());
        assertEquals(Integer.valueOf(178655), postCode.getNorthings());
        assertEquals("England", postCode.getCountry());
        assertEquals("South West", postCode.getNhs_ha());
        assertEquals(-2.56899282995555, postCode.getLongitude(), 1);
        assertEquals(51.5054493186496, postCode.getLatitude(), 2);
        assertEquals("Filton and Bradley Stoke", postCode.getParliamentary_constituency());
        assertEquals("South West", postCode.getEuropean_electoral_region());
        assertEquals("South Gloucestershire", postCode.getPrimary_care_trust());
        assertEquals("South West", postCode.getRegion());
        assertEquals("South Gloucestershire 018D", postCode.getLsoa());
        assertEquals("South Gloucestershire 018", postCode.getMsoa());
        assertEquals("7NP", postCode.getIncode());
        assertEquals("BS34", postCode.getOutcode());
        assertEquals("South Gloucestershire", postCode.getAdmin_district());
        assertEquals("Filton", postCode.getParish());
        assertFalse(postCode.getAdmin_county());
        assertEquals("Filton", postCode.getAdmin_ward());
        assertEquals("NHS South Gloucestershire", postCode.getCcg());
        assertEquals("Bath and North East Somerset, North Somerset and South Gloucestershire", postCode.getNuts());
        assertEquals("E06000025", postCode.getCodes().getAdmin_district());
        assertEquals("E99999999", postCode.getCodes().getAdmin_county());
        assertEquals("E05002055", postCode.getCodes().getAdmin_ward());
        assertEquals("E04001052", postCode.getCodes().getParish());
        assertEquals("E38000155", postCode.getCodes().getCcg());
        assertEquals("UKK12", postCode.getCodes().getNuts());
    }

}
