package com.postcode.io.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.junit.Test;

/**
 * @author Deepak
 *
 */
public class JsonFetcherTest {

    @Test
    public void testByURL() throws MalformedURLException {
        JSONObject json = JsonFetcher.urlToJson(new URL("http://api.postcodes.io/postcodes/bs347np"));
        test(json);
    }

    @Test
    public void testByFile() throws MalformedURLException {
        JSONObject json = JsonFetcher
                .urlToJson(new File(System.getProperty("user.dir").concat("/src/test/resources/postcodeLookup.json"))
                        .toURI().toURL());
        test(json);
    }

    public void test(JSONObject json) {
        assertTrue(json.has("status"));
        assertTrue(json.has("result"));
        JSONObject result = json.getJSONObject("result");
        assertEquals("BS34 7NP", (result.getString("postcode")));
        assertEquals(1, (result.getInt("quality")));
        assertEquals(360605, (result.getInt("eastings")));
        assertEquals(178655, (result.getInt("northings")));
        assertEquals("England", (result.getString("country")));
        assertEquals("South West", (result.getString("nhs_ha")));
        assertEquals(-2.56899282995555, result.getDouble("longitude"), 1);
        assertEquals(51.5054493186496, result.getDouble("latitude"), 2);
        assertEquals("Filton and Bradley Stoke", (result.getString("parliamentary_constituency")));
        assertEquals("South West", (result.getString("european_electoral_region")));
        assertEquals("South Gloucestershire", (result.getString("primary_care_trust")));
        assertEquals("South West", (result.getString("region")));
        assertEquals("South Gloucestershire 018D", (result.getString("lsoa")));
        assertEquals("South Gloucestershire 018", (result.getString("msoa")));
        assertEquals("7NP", (result.getString("incode")));
        assertEquals("BS34", (result.getString("outcode")));
        assertEquals("South Gloucestershire", (result.getString("admin_district")));
        assertEquals("Filton", (result.getString("parish")));
        assertEquals(JSONObject.NULL, result.get("admin_county"));
        assertEquals("Filton", (result.getString("admin_ward")));
        assertEquals("NHS South Gloucestershire", (result.getString("ccg")));
        assertEquals("Bath and North East Somerset, North Somerset and South Gloucestershire",
                (result.getString("nuts")));
        JSONObject codes = result.getJSONObject("codes");
        assertEquals("E06000025", (codes.getString("admin_district")));
        assertEquals("E99999999", (codes.getString("admin_county")));
        assertEquals("E05002055", (codes.getString("admin_ward")));
        assertEquals("E04001052", (codes.getString("parish")));
        assertEquals("E38000155", (codes.getString("ccg")));
        assertEquals("UKK12", (codes.getString("nuts")));
    }

}
