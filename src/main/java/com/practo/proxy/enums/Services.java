package com.practo.proxy.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Services {
  consult,
  titan;

  public static List<String> getAllServices() {
    return Arrays.stream(Services.values())
        .map(Enum::name)
        .collect(Collectors.toList());
  }
}
