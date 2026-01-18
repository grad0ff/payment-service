package org.example.enums;

import java.util.Arrays;

public enum MimeTypes {

  HTML("text/html; charset=UTF-8"),
  PLAIN("text/plain; charset=UTF-8"),
  PNG("image/png");

  private final String value;

  MimeTypes(String value) {
    this.value = value;
  }

  public static MimeTypes getType(String value) {
    return Arrays.stream(MimeTypes.values())
        .filter(e -> e.name().equalsIgnoreCase(value))
        .findFirst()
        .orElse(MimeTypes.PLAIN);
  }

  public String getValue() {
    return value;
  }

}
