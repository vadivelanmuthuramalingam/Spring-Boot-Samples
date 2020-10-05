package com.pst.httpclient.builder.Interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class OAuthInterceptor implements ClientHttpRequestInterceptor { //(1)

    private static final Logger log = LoggerFactory.getLogger(OAuthInterceptor.class);

    
    String userid;

    String password;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        String plainCredentials = userid + ":" + password;
        String base64Credentials = Base64.getEncoder()
                .encodeToString(plainCredentials.getBytes(StandardCharsets.UTF_8));
        
        request.getHeaders().add("Authorization", "Basic " + base64Credentials); // (1)

        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }

}