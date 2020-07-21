package com.postcode.io.json;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * Fetch Json From URL
 *
 * @author Deepak
 */
@Slf4j
public class JsonFetcher {

    private JsonFetcher() {
    }

    /**
     * Pass {@link URL} to get {@link JSONObject} from it
     *
     * @param url
     * @return
     */
    public static JSONObject urlToJson(URL url) {
        URLConnection urlConnection;
        try {
            urlConnection = url.openConnection();
            BufferedReader bufferedReader;
            if (urlConnection.getHeaderField(HTTP.CONTENT_ENCODING) != null && urlConnection.getHeaderField(HTTP.CONTENT_ENCODING)
                    .equals("gzip")) {
                log.info("reading data from URL as GZIP Stream");
                bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(urlConnection.getInputStream())));
            } else {
                log.info("reading data from URL as InputStream");
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            }
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();
            return new JSONObject(stringBuilder.toString());
        } catch (IOException e) {
            log.info("Exception while reading JSON from URL: {}. Stacktrace: {}", e.getMessage(), e.getStackTrace());
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

        try {
            StringEntity input = createStringEntity(jsonObject);
            HttpPost postRequest = createHttpPost(url, input);

            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .build();
            log.info("Executing PostRequest");
            HttpResponse response = httpClient.execute(postRequest);
            log.info("creating BufferedReader");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((response.getEntity()
                    .getContent())));
            StringBuilder stringBuilder = new StringBuilder();
            String output;
            while ((output = bufferedReader.readLine()) != null) {
                log.info("Appending JSON to String  Builder");
                stringBuilder.append(output);
            }
            log.info("Closing HTTP Connection");
            httpClient.close();
            bufferedReader.close();
            return new JSONObject(stringBuilder.toString());
        } catch (MalformedURLException e) {
            log.info("URL is Malformed: {}", e.getMessage());
            throw new MalformedURLException(e.toString());
        } catch (IOException e) {
            log.info("Error while reading JSON from URL: {}", e.getMessage());
            throw new IOException(e.toString());
        }
    }

    private static StringEntity createStringEntity(JSONObject jsonObject) throws UnsupportedEncodingException {
        log.info("Creating String Entity POST Input");
        StringEntity input = new StringEntity(jsonObject.toString());
        input.setContentType(ContentType.APPLICATION_JSON.toString());
        input.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));
        return input;
    }

    private static HttpPost createHttpPost(final URL url, final StringEntity input) {
        log.info("Creating POST Request");
        HttpPost postRequest = new HttpPost(url.toString());
        postRequest.setEntity(input);
        postRequest.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        postRequest.setEntity(input);
        return postRequest;
    }
}
