package com.pst.httpclient.builder.Interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;



public class LoggingInterceptor implements ClientHttpRequestInterceptor { //(1)

    private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        if (log.isInfoEnabled()) {
            String requestBody = new String(body, StandardCharsets.UTF_8);

            log.info("Request Header {}", request.getHeaders()); //(2)
            log.info("Request Body {}", requestBody);
        }

        ClientHttpResponse response = execution.execute(request, body); //(3)

        if (log.isInfoEnabled()) {
            log.info("Response Header {}", response.getHeaders()); // (4)
            log.info("Response Status Code {}", response.getStatusCode()); // (5)
        }

        return response; // (6)
    }

    private ClientHttpResponse traceResponse(ClientHttpResponse response) throws IOException {
        if (!log.isDebugEnabled()) {
            return response;
        }
        final ClientHttpResponse responseWrapper = new BufferingClientHttpResponseWrapper(response);
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(responseWrapper.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        log.debug(
                "==========================response begin=============================================");
        log.debug("Status code    : {}", responseWrapper.getStatusCode());
        log.debug("Status text    : {}", responseWrapper.getStatusText());
        log.debug("Headers            : {}", responseWrapper.getHeaders());
        log.debug("Response body: {}", inputStringBuilder.toString());
        log.debug(
                "==========================response end===============================================");
        return responseWrapper;
    }    
    
}

