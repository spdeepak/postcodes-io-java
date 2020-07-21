package com.postcode.io;

import com.postcode.io.initializers.PostcodeLookup;
import org.junit.Test;

import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Deepak
 *
 */
public class PostCodeDetailsTest {

    @Test
    public void testPostCodeGenerate() throws MalformedURLException {
        PostCodeDetails postCodeDetails = PostCodeDetails.generate(PostcodeLookup.postcode("bs347np").build());
        assertEquals("BS34 7NP", postCodeDetails.getPostcode());
        assertEquals(Integer.valueOf(1), postCodeDetails.getQuality());
        assertEquals(Integer.valueOf(360605), postCodeDetails.getEastings());
        assertEquals(Integer.valueOf(178655), postCodeDetails.getNorthings());
        assertEquals("England", postCodeDetails.getCountry());
        assertEquals("South West", postCodeDetails.getNhs_ha());
        assertEquals(-2.56899282995555, postCodeDetails.getLongitude(), 1);
        assertEquals(51.5054493186496, postCodeDetails.getLatitude(), 2);
        assertEquals("Filton and Bradley Stoke", postCodeDetails.getParliamentary_constituency());
        assertEquals("South West", postCodeDetails.getEuropean_electoral_region());
        assertEquals("South Gloucestershire", postCodeDetails.getPrimary_care_trust());
        assertEquals("South West", postCodeDetails.getRegion());
        assertEquals("South Gloucestershire 018D", postCodeDetails.getLsoa());
        assertEquals("South Gloucestershire 018", postCodeDetails.getMsoa());
        assertEquals("7NP", postCodeDetails.getIncode());
        assertEquals("BS34", postCodeDetails.getOutcode());
        assertEquals("South Gloucestershire", postCodeDetails.getAdmin_district());
        assertEquals("Filton", postCodeDetails.getParish());
        assertFalse(postCodeDetails.getAdmin_county());
        assertEquals("Filton", postCodeDetails.getAdmin_ward());
        assertEquals("NHS Bristol, North Somerset and South Gloucestershire", postCodeDetails.getCcg());
        assertEquals("Bath and North East Somerset, North Somerset and South Gloucestershire",
                postCodeDetails.getNuts());
        assertEquals("E06000025", postCodeDetails.getCodes().getAdmin_district());
        assertEquals("E99999999", postCodeDetails.getCodes().getAdmin_county());
        assertEquals("E05012113", postCodeDetails.getCodes().getAdmin_ward());
        assertEquals("E04001052", postCodeDetails.getCodes().getParish());
        assertEquals("E38000222", postCodeDetails.getCodes().getCcg());
        assertEquals("UKK12", postCodeDetails.getCodes().getNuts());
    }

}
