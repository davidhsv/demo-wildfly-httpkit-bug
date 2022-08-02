package com.example.demo2;

import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.engines.ClientHttpEngineBuilder43;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class Controller {
    
    @GetMapping("/")
    public String index() throws IOException {
        int httpStatus = test("https://www.google.com", newClient());
        return "Http Status: " + httpStatus;
    }
    
    public ResteasyClient newClient() {
        ResteasyClientBuilderImpl resteasyClientBuilder = new ResteasyClientBuilderImpl();
        resteasyClientBuilder.sslContext(getSslContext());
        resteasyClientBuilder.httpEngine(new ClientHttpEngineBuilder43()
                .resteasyClientBuilder(resteasyClientBuilder).build());
        return resteasyClientBuilder.build();
    }
    
    public int test(String url, ResteasyClient resteasyClient) {
        return resteasyClient.target(URI.create(url)).request().get().getStatus();
    }
    
    private SSLContext getSslContext() {
        try {
            return SSLContext.getDefault();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
    
}