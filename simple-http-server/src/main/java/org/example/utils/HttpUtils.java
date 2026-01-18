package org.example.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.example.enums.HttpMethods;
import org.example.enums.MimeTypes;

public final class HttpUtils {

  private HttpUtils() {
  }

  public static String extractEndpoint(String query) {
    String endpoint = query.split("\\s")[1];
    endpoint = endpoint.equals("/") ? "index.html" : endpoint;
    return endpoint;
  }

  public static HttpMethods extractHttpMethod(String query) {
    String method = query.split("\\s")[0];
    return HttpMethods.getMethod(method);
  }

  public static MimeTypes defineMimeType(String endpoint) {
    String extension = extractFileExtension(endpoint);

    return MimeTypes.getType(extension);
  }

  private static String extractFileExtension(String endpoint) {
    List<String> splitEndpoint = Arrays.asList(endpoint.split("\\."));
    if (!splitEndpoint.isEmpty()) {
      return splitEndpoint.getLast();
    }
    return "";
  }

  public static String prepareHeader(String statusCode, MimeTypes contentType, int contentLength)
      throws IllegalArgumentException {
    Objects.requireNonNull(statusCode, "Status code can`t be null");
    Objects.requireNonNull(contentType, "Content type can`t be null");
    if (contentLength < 0) {
      throw new IllegalArgumentException("Content length must be greater then 0");
    }
    return """
        HTTP/1.1 %s
        Content-Type: %s
        Content-Length: %d
        
        """.formatted(statusCode, contentType.getValue(), contentLength);
  }

}
