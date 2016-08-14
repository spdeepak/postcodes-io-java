package com.postcode.io.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Fetch Json From URL
 * 
 * @author Deepak
 *
 */
public class JsonFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFetcher.class);

    /**
     * Pass {@link URL} to get {@link JSONObject} from it
     * 
     * @param urlString
     * @return
     */
    public static JSONObject urlToJson(URL urlString) {
        StringBuilder sb = null;
        URL url;
        URLConnection urlCon;
        try {
            url = urlString;
            urlCon = url.openConnection();
            BufferedReader in;
            if (urlCon.getHeaderField("Content-Encoding") != null
                    && urlCon.getHeaderField("Content-Encoding").equals("gzip")) {
                LOGGER.info("reading data from URL as GZIP Stream");
                in = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlCon.getInputStream())));
            } else {
                LOGGER.info("reading data from URL as InputStream");
                in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            }
            String inputLine;
            sb = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            LOGGER.info("Exception while reading JSON from URL - {}", e);
        }
        if (sb != null) {
            return new JSONObject(sb.toString());
        } else {
            return new JSONObject("");
        }
    }

    /**
     * Gets {@link JSONObject} from given {@link URL} & {@link JSONObject} via POST
     * 
     * @param url
     * @param jsonObject
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static JSONObject postURLToJson(URL url, JSONObject jsonObject) throws IOException {
        StringEntity input = null;
        HttpPost postRequest = null;
        CloseableHttpClient httpClient = null;
        HttpResponse response = null;
        BufferedReader br = null;
        StringBuilder sb = null;
        try {
            LOGGER.info("Create POST Input");
            input = new StringEntity(jsonObject.toString());
            input.setContentType("application/json;charset=UTF-8");
            input.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
            LOGGER.info("Creating POST Request");
            postRequest = new HttpPost(url.toString());
            postRequest.setEntity(input);
            postRequest.setHeader("Accept", "application/json");
            postRequest.setEntity(input);
            LOGGER.info("Creating HTTPClient");
            httpClient = HttpClientBuilder.create().build();
            LOGGER.info("Executing PostRequest");
            response = httpClient.execute(postRequest);
            LOGGER.info("creating BufferedReader");
            br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;
            sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                LOGGER.info("Appending JSON to String  Builder");
                sb.append(output);
            }
            LOGGER.info("Closing HTTP Connection");
            httpClient.close();
        } catch (MalformedURLException e) {
            LOGGER.info("URL is Malformed {}", e);
            throw new MalformedURLException(e.toString());
        } catch (IOException e) {
            LOGGER.info("Error while reading JSON from URL", e);
            throw new IOException(e.toString());
        }
        if (sb != null) {
            return new JSONObject(sb.toString());
        } else {
            return new JSONObject("");
        }
    }
}
