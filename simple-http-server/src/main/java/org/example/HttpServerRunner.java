package org.example;

public class HttpServerRunner {

  public static void main(String[] args) throws Exception {
    final HttpServer server;
    if (args.length == 0) {
      server = new HttpServer();
    } else if (args.length == 2) {
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      server = new HttpServer(host, port);
    } else {
      throw new RuntimeException("Use <host> <port> args to run server");
    }
    server.run();
  }

}
