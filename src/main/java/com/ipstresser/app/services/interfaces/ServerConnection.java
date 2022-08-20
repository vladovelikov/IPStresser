package com.ipstresser.app.services.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ServerConnection {

    void sendRequest(String targetIp, String targetPort, String time, String method, int servers) throws URISyntaxException, IOException, InterruptedException;
}
