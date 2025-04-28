package com.practo.proxy.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ServiceCallRequestDto {
  private String service;
  private String url;
  private Map<String, String> pathParams = new HashMap<>();
  private Map<String, String> queryParams = new HashMap<>();
}