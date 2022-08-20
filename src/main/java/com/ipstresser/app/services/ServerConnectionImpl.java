package com.ipstresser.app.services;

import com.ipstresser.app.services.interfaces.ServerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ServerConnectionImpl implements ServerConnection {

    private final String MACHINE_IP = null;
    private final String PASSWORD_ONE = null;
    private final String PASSWORD_TWO = null;
    private final HttpClient httpClient;

    @Autowired
    public ServerConnectionImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void sendRequest(String targetIp, String targetPort, String time, String method, int servers) throws URISyntaxException, IOException, InterruptedException {

        String url = String.format("%s?ip=%s&port=%s&time=%s&method=%s&pass=%s&pass2=%s", MACHINE_IP, targetIp, targetPort, time, method,
                        PASSWORD_ONE, PASSWORD_TWO);

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url)).build();
        httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());
    }
}
