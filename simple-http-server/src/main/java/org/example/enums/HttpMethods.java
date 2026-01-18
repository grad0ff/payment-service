package org.example.enums;

import java.util.Arrays;

public enum HttpMethods {

  GET;

  public static HttpMethods getMethod(String value) {
    return Arrays.stream(HttpMethods.values())
        .filter(e -> e.name().equalsIgnoreCase(value))
        .findFirst()
        .orElseThrow();
  }

}
