package com.github.leofalco.cep4j;

import com.github.leofalco.cep4j.model.Response;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class Http {
    private Http() {
    }

    private static final HttpClient client = HttpClientBuilder.create().build();

    public static Response get(String uri) throws IOException {
        HttpResponse httpResponse = client.execute(new HttpGet(uri));
        return readResponse(httpResponse);
    }

    public static Response post(String uri, String body, Map<String, String> headers) throws IOException {
        HttpPost post = new HttpPost(uri);
        headers.forEach(post::addHeader);
        post.setEntity(new StringEntity(body));

        HttpResponse httpResponse = client.execute(post);
        return readResponse(httpResponse);
    }

    private static Response readResponse(HttpResponse httpResponse) throws IOException {
        try (InputStream responseAsStream = httpResponse.getEntity().getContent()) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(responseAsStream, StandardCharsets.UTF_8)) {
                try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    String responseAsString = bufferedReader.lines().collect(Collectors.joining());
                    Header contentType = httpResponse.getEntity().getContentType();
                    return new Response(statusCode, contentType, responseAsString);
                }
            }
        }
    }
}
