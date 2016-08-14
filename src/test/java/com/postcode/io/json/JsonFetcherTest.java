package com.postcode.io.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Deepak
 *
 */
public class JsonFetcherTest {

    @Test
    public void testByURL() throws MalformedURLException, UnirestException {
        JSONObject json = JsonFetcher.urlToJson(new URL("http://api.postcodes.io/postcodes/bs347np"));
        Unirest.post("http://api.postcodes.io/postcodes").header("accept", "application/json")
                .queryString("postcodes", new String[] { "BS34 7NP" }).field("postcodes", new String[] { "BS34 7NP" })
                .asJson();
        assertTrue(json.has("status"));
        assertTrue(json.has("result"));
        test(json.getJSONObject("result"));
        Unirest.post("http://api.postcodes.io/postcodes").header("accept", "application/json")
                .field("postcodes", "OX49 5NU").field("postcodes", "BS34 7NP").asJson().getBody().getObject();

    }

    @Test
    public void testByFile() throws MalformedURLException {
        JSONObject json = JsonFetcher
                .urlToJson(new File(System.getProperty("user.dir").concat("/src/test/resources/postcodeLookup.json"))
                        .toURI().toURL());
        assertTrue(json.has("status"));
        assertTrue(json.has("result"));
        test(json.getJSONObject("result"));
    }

    public void test(JSONObject result) {
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

    @Test
    public void testPostURLToJson() throws Exception {
        JSONObject jsonO = new JSONObject();
        JSONArray ja = new JSONArray();
        ja.put("BS34 7NP");
        ja.put("ST4 2EU");
        jsonO.put("postcodes", ja);
        String url = "http://api.postcodes.io/postcodes/";
        JSONObject json = JsonFetcher.postURLToJson(new URL(url), jsonO);
        assertEquals(200, json.getInt("status"));
        JSONArray jsonArray = json.getJSONArray("result");
        for (int i = 0; i < 2; i++) {
            if (jsonArray.getJSONObject(i).getJSONObject("result").getString("postcode").equals("BS34 7NP")) {
                test(jsonArray.getJSONObject(i).getJSONObject("result"));
            } else {
                JSONObject result = jsonArray.getJSONObject(i).getJSONObject("result");
                assertEquals("ST4 2EU", (result.getString("postcode")));
                assertEquals(1, (result.getInt("quality")));
                assertEquals(387884, (result.getInt("eastings")));
                assertEquals(346021, (result.getInt("northings")));
                assertEquals("England", (result.getString("country")));
                assertEquals("West Midlands", (result.getString("nhs_ha")));
                assertEquals(-2.18203015918483, result.getDouble("longitude"), 1);
                assertEquals(53.0113602891873, result.getDouble("latitude"), 2);
                assertEquals("Stoke-on-Trent Central", (result.getString("parliamentary_constituency")));
                assertEquals("West Midlands", (result.getString("european_electoral_region")));
                assertEquals("Stoke on Trent", (result.getString("primary_care_trust")));
                assertEquals("West Midlands", (result.getString("region")));
                assertEquals("Stoke-on-Trent 016E", (result.getString("lsoa")));
                assertEquals("Stoke-on-Trent 016", (result.getString("msoa")));
                assertEquals("2EU", (result.getString("incode")));
                assertEquals("ST4", (result.getString("outcode")));
                assertEquals("Stoke-on-Trent", (result.getString("admin_district")));
                assertEquals("Stoke-on-Trent, unparished area", (result.getString("parish")));
                assertEquals(JSONObject.NULL, result.get("admin_county"));
                assertEquals("Hanley Park and Shelton", (result.getString("admin_ward")));
                assertEquals("NHS Stoke on Trent", (result.getString("ccg")));
                assertEquals("Stoke-on-Trent", (result.getString("nuts")));
                JSONObject codes = result.getJSONObject("codes");
                assertEquals("E06000021", (codes.getString("admin_district")));
                assertEquals("E99999999", (codes.getString("admin_county")));
                assertEquals("E05008734", (codes.getString("admin_ward")));
                assertEquals("E43000017", (codes.getString("parish")));
                assertEquals("E38000175", (codes.getString("ccg")));
                assertEquals("UKG23", (codes.getString("nuts")));
            }
        }

    }

}
