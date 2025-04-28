package com.practo.proxy.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProviderDto {

  private ProviderNameDto name;

  @JsonProperty("years_of_experience")
  private int yearsOfExperience;

  @JsonProperty("image_url")
  private String imageUrl;

  @JsonProperty("enhanced_image_url")
  private String enhancedImageUrl;

  @Data
  public static class ProviderNameDto {

    @JsonProperty("full_name")
    private String fullName;
  }
}

