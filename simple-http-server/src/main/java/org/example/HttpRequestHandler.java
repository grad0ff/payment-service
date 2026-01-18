package org.example;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.example.enums.HttpMethods;
import org.example.enums.MimeTypes;
import org.example.utils.HttpUtils;

public final class HttpRequestHandler {

  private static final String RESOURCE_FOLDER = "static";
  private final OutputStream out;

  public HttpRequestHandler(OutputStream out) {
    this.out = out;
  }

  public void handleRequest(String query) throws IOException {
    String endpoint = HttpUtils.extractEndpoint(query);
    HttpMethods httpMethod = HttpUtils.extractHttpMethod(query);
    switch (httpMethod) {
      case GET -> handleGetRequest(endpoint);
    }
  }

  private void handleGetRequest(String endpoint) throws IOException {
    Path path = Paths.get(RESOURCE_FOLDER, endpoint);
    validateResourcePath(path);
    if (path.toFile().exists()) {
      MimeTypes mimeType = HttpUtils.defineMimeType(endpoint);
      String header = HttpUtils.prepareHeader("200 OK", mimeType, (int) Files.size(path));
      out.write(header.getBytes());
      try (var bis = new BufferedInputStream(Files.newInputStream(path))) {
        bis.transferTo(out);
      }
      out.flush();
    } else {
      returnNotFound();
    }
  }

  private void validateResourcePath(Path path) throws IOException {
    Path absolurePath = path.toAbsolutePath();
    Path resourceFolderPath = Path.of(RESOURCE_FOLDER).toAbsolutePath();
    if (!absolurePath.startsWith(resourceFolderPath)) {
      returnNotFound();
    }
  }

  private void returnNotFound() throws IOException {
    Path path = Paths.get(RESOURCE_FOLDER, "/not_found.html");
    String header = HttpUtils.prepareHeader("404 Not Found", MimeTypes.HTML, (int) Files.size(path));
    out.write(header.getBytes());
    try (var bis = new BufferedInputStream(Files.newInputStream(path))) {
      bis.transferTo(out);
    }
    out.flush();
  }

}
