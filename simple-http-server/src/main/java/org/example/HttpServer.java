package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;

public class HttpServer {

  private final String host;
  private final int port;

  public HttpServer() {
    this.host = "localhost";
    this.port = 8080;
  }

  public HttpServer(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public void run() throws IOException {
    try (var serverSocket = new ServerSocket(port, 0, InetAddress.getByName(host))) {
      System.out.printf("Server started at %s:%d",
          serverSocket.getInetAddress().getHostAddress(),
          serverSocket.getLocalPort());

      while (true) {
        try (var clientSocket = serverSocket.accept();
            var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            var out = clientSocket.getOutputStream()) {

          String firstLine;
          if ((firstLine = in.readLine()) != null && !firstLine.isBlank()) {
            var requestHandler = new HttpRequestHandler(out);
            requestHandler.handleRequest(firstLine);
          }
        }
      }
    }
  }

}
