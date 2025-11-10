package com.flowscript.codegen.helpers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Helper class for HTTP operations in generated FlowScript code.
 * Provides methods for GET, POST, and DELETE requests.
 *
 * This class will be copied into generated code to provide http.get(), http.post(), and http.delete() functionality.
 */
public class HttpHelper {

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    private static final Gson gson = new Gson();

    /**
     * Performs an HTTP GET request.
     * Corresponds to http.get(url) or http.get(url, headers) in FlowScript.
     *
     * @param url Target URL
     * @return Map with response data: { "status": 200, "body": {...}, "headers": {...} }
     */
    public static Map<String, Object> get(String url) {
        return get(url, null);
    }

    /**
     * Performs an HTTP GET request with custom headers.
     *
     * @param url Target URL
     * @param headers Map of HTTP headers (can be null)
     * @return Map with response data
     */
    public static Map<String, Object> get(String url, Map<String, Object> headers) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(30));

            // Add custom headers
            if (headers != null) {
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    requestBuilder.header(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("[HTTP GET] " + url + " | Status: " + response.statusCode());

            return buildResponse(response);

        } catch (IOException | InterruptedException e) {
            System.err.println("[HTTP ERROR] GET failed: " + url);
            System.err.println("[HTTP ERROR] " + e.getMessage());
            return buildErrorResponse(e);
        }
    }

    /**
     * Performs an HTTP POST request.
     * Corresponds to http.post(url, body) or http.post(url, body, headers) in FlowScript.
     *
     * @param url Target URL
     * @param body Request body (will be converted to JSON)
     * @return Map with response data
     */
    public static Map<String, Object> post(String url, Map<String, Object> body) {
        return post(url, body, null);
    }

    /**
     * Performs an HTTP POST request with custom headers.
     *
     * @param url Target URL
     * @param body Request body (will be converted to JSON)
     * @param headers Map of HTTP headers (can be null)
     * @return Map with response data
     */
    public static Map<String, Object> post(String url, Map<String, Object> body, Map<String, Object> headers) {
        try {
            // Convert body to JSON
            String jsonBody = gson.toJson(body);

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(30));

            // Add custom headers
            if (headers != null) {
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    requestBuilder.header(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("[HTTP POST] " + url + " | Status: " + response.statusCode());

            return buildResponse(response);

        } catch (IOException | InterruptedException e) {
            System.err.println("[HTTP ERROR] POST failed: " + url);
            System.err.println("[HTTP ERROR] " + e.getMessage());
            return buildErrorResponse(e);
        }
    }

    /**
     * Performs an HTTP DELETE request.
     * Corresponds to http.delete(url) or http.delete(url, headers) in FlowScript.
     *
     * @param url Target URL
     * @return Map with response data
     */
    public static Map<String, Object> delete(String url) {
        return delete(url, null);
    }

    /**
     * Performs an HTTP DELETE request with custom headers.
     *
     * @param url Target URL
     * @param headers Map of HTTP headers (can be null)
     * @return Map with response data
     */
    public static Map<String, Object> delete(String url, Map<String, Object> headers) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .timeout(Duration.ofSeconds(30));

            // Add custom headers
            if (headers != null) {
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    requestBuilder.header(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("[HTTP DELETE] " + url + " | Status: " + response.statusCode());

            return buildResponse(response);

        } catch (IOException | InterruptedException e) {
            System.err.println("[HTTP ERROR] DELETE failed: " + url);
            System.err.println("[HTTP ERROR] " + e.getMessage());
            return buildErrorResponse(e);
        }
    }

    /**
     * Builds a response map from HttpResponse.
     */
    private static Map<String, Object> buildResponse(HttpResponse<String> response) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", response.statusCode());

        // Parse body as JSON if possible
        String responseBody = response.body();
        Object parsedBody;

        try {
            // Try to parse as JSON object
            parsedBody = gson.fromJson(responseBody, Map.class);
        } catch (JsonSyntaxException e1) {
            try {
                // Try to parse as JSON array
                parsedBody = gson.fromJson(responseBody, List.class);
            } catch (JsonSyntaxException e2) {
                // Not JSON, use raw string
                parsedBody = responseBody;
            }
        }

        result.put("body", parsedBody);

        // Add headers
        Map<String, Object> headersMap = new LinkedHashMap<>();
        response.headers().map().forEach((key, values) -> {
            if (values.size() == 1) {
                headersMap.put(key, values.get(0));
            } else {
                headersMap.put(key, values);
            }
        });
        result.put("headers", headersMap);

        return result;
    }

    /**
     * Builds an error response map.
     */
    private static Map<String, Object> buildErrorResponse(Exception e) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", 0);
        result.put("error", e.getMessage());
        result.put("body", null);
        result.put("headers", new HashMap<>());
        return result;
    }
}
