package com.postcode.io.initializers;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.postcode.io.json.JsonFetcher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Deepak
 */
@Data
public class ReverseGeocoding {

    private static final String LOOKUP_URL = "http://api.postcodes.io/postcodes?";

    private int limit;
    private int radius;
    private JSONObject json;
    private Double latitude;
    private Double longitude;
    private boolean wideSearch;
    private List<Reverse> reverses = new ArrayList<>();

    public ReverseGeocoding() {
    }

    public ReverseGeocoding(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public ReverseGeocoding(List<Reverse> reverses) {
        this.reverses = reverses;
        this.json = createGeoCodings(this.reverses);
    }

    private JSONObject createGeoCodings(List<Reverse> reverses) {
        JSONArray jsonArray = new JSONArray();
        reverses.forEach(reverse -> {
            JSONObject tempJson = new JSONObject();
            tempJson.put("longitude", reverse.getLongitude()
                    .toString());
            tempJson.put("latitude", reverse.getLatitude()
                    .toString());
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
        });
        return new JSONObject().put("geolocations", jsonArray);
    }

    public ReverseGeocoding limit(int limit) {
        this.limit = limit;
        return this;
    }

    public ReverseGeocoding radius(int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * (not required) Search up to 20km radius, but subject to a maximum of 10 results. Since lookups over a wide area can be very expensive, we've created this method to
     * allow you choose to make the trade off between search radius and number of results. Defaults to false. When enabled, radius and limits over 10 are ignored.
     *
     * @param wideSearch
     * @return
     */
    public ReverseGeocoding wideSearch(boolean wideSearch) {
        this.wideSearch = wideSearch;
        return this;
    }

    /**
     * @return {@link JSONObject}
     * @throws IOException
     * @throws UnirestException
     */
    public JSONObject asJson() throws IOException, UnirestException {
        String url = "";
        url = url.concat("lon=")
                .concat(String.valueOf(longitude));
        url = url.concat("&lat=")
                .concat(String.valueOf(latitude));
        if (getLimit() != 0) {
            url = url.concat("&limit=")
                    .concat(String.valueOf(limit));
        }
        if (getRadius() != 0) {
            url = url.concat("&radius=")
                    .concat(String.valueOf(radius));
        }
        url = url.concat("&widesearch=")
                .concat(String.valueOf(wideSearch));
        try {
            if (json != null) {
                return JsonFetcher.postURLToJson(new URL(LOOKUP_URL), json);
            } else {
                return Unirest.get(LOOKUP_URL.concat(url))
                        .asJson()
                        .getBody()
                        .getObject();
            }
        } finally {
            clear();
        }
    }

    private void clear() {
        this.longitude = null;
        this.latitude = null;
        this.limit = 0;
        this.radius = 0;
        this.wideSearch = false;
        this.json = null;
        this.reverses = null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Reverse {

        private Double longitude;
        private Double latitude;
        private int limit;
        private int radius;
        private boolean wideSearch;
    }

}
