package com.postcode.io.initializers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.postcode.io.json.JsonFetcher;

/**
 * @author Deepak
 *
 */
public class ReverseGeocoding {

    private static final String LOOKUP_URL = "http://api.postcodes.io/postcodes?";

    private static Double longitude;

    private static Double latitude;

    private static int limit;

    private static int radius;

    private static boolean wideSearch;

    private static JSONObject json;

    private static List<Reverse> reverses = new ArrayList<>();

    public ReverseGeocoding() {
    }

    public ReverseGeocoding(Double longitude, Double latitude) {
        ReverseGeocoding.longitude = longitude;
        ReverseGeocoding.latitude = latitude;
    }

    public ReverseGeocoding limit(int limit) {
        ReverseGeocoding.limit = limit;
        return this;
    }

    public ReverseGeocoding(List<Reverse> reverses) {
        ReverseGeocoding.reverses = reverses;
        ReverseGeocoding.json = createGeocodings(ReverseGeocoding.reverses);
    }

    public ReverseGeocoding radius(int radius) {
        ReverseGeocoding.radius = radius;
        return this;
    }

    /**
     * (not required) Search up to 20km radius, but subject to a maximum of 10 results. Since
     * lookups over a wide area can be very expensive, we've created this method to allow you choose
     * to make the trade off between search radius and number of results. Defaults to false. When
     * enabled, radius and limits over 10 are ignored.
     * 
     * @param wideSearch
     * @return
     */
    public ReverseGeocoding wideSearch(boolean wideSearch) {
        ReverseGeocoding.wideSearch = wideSearch;
        return this;
    }

    /**
     * @return {@link JSONObject}
     * @throws IOException
     * @throws UnirestException
     */
    public JSONObject asjson() throws IOException, UnirestException {
        String url = "";
        url = url.concat("lon=").concat(String.valueOf(longitude));
        url = url.concat("&lat=").concat(String.valueOf(latitude));
        if (getLimit() != 0) {
            url = url.concat("&limit=").concat(String.valueOf(limit));
        }
        if (getRadius() != 0) {
            url = url.concat("&radius=").concat(String.valueOf(radius));
        }
        url = url.concat("&widesearch=").concat(String.valueOf(wideSearch));
        try {
            if (json != null) {
                return JsonFetcher.postURLToJson(new URL(LOOKUP_URL), json);
            } else {
                return Unirest.get(LOOKUP_URL.concat(url)).asJson().getBody().getObject();
            }
        } finally {
            clear();
        }
    }

    private void clear() {
        ReverseGeocoding.longitude = null;
        ReverseGeocoding.latitude = null;
        ReverseGeocoding.limit = 0;
        ReverseGeocoding.radius = 0;
        ReverseGeocoding.wideSearch = false;
        ReverseGeocoding.json = null;
        ReverseGeocoding.reverses = null;
    }

    private JSONObject createGeocodings(List<Reverse> reverses) {
        JSONObject tempJson;
        JSONArray jsonArray = new JSONArray();
        for (Reverse reverse : reverses) {
            tempJson = new JSONObject();
            tempJson.put("longitude", reverse.getLongitude().toString());
            tempJson.put("latitude", reverse.getLatitude().toString());
            if (reverse.getLimit() != 0) {
                tempJson.put("limit", String.valueOf(reverse.getLimit()));
            }
            if (reverse.getRadius() != 0) {
                tempJson.put("radius", String.valueOf(reverse.getRadius()));
            }
            if (reverse.isWideSearch()) {
                tempJson.put("wideSearch", String.valueOf(reverse.isWideSearch()));
            }
            jsonArray.put(tempJson);
        }
        return new JSONObject().put("geolocations", jsonArray);
    }

    public class Reverse {

        private Double longitude;

        private Double latitude;

        private int limit;

        private int radius;

        private boolean wideSearch;

        public Reverse() {
        }

        public Reverse(Double longitude, Double latitude, int limit, int radius, boolean wideSearch) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.limit = limit;
            this.radius = radius;
            this.wideSearch = wideSearch;
        }

        public Double getLongitude() {
            return longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public int getLimit() {
            return limit;
        }

        public int getRadius() {
            return radius;
        }

        public boolean isWideSearch() {
            return wideSearch;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public void setWideSearch(boolean wideSearch) {
            this.wideSearch = wideSearch;
        }
    }

    private static int getLimit() {
        return limit;
    }

    private static int getRadius() {
        return radius;
    }

}
