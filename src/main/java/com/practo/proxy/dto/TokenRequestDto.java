package com.practo.proxy.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequestDto {
  private String email;
  private String service;
}
