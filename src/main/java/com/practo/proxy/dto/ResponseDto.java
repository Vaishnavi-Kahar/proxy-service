package com.practo.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class ResponseDto<T> {
  private boolean success;
  private String message;
  private T data;
  private Date timestamp;


  public ResponseDto(boolean success, String message, T data) {
    this.success = success;
    this.message = message;
    this.data = data;
    this.timestamp = new Date();
  }

  public static <T> ResponseDto<T> success(String message, T data) {
    return new ResponseDto<>(true, message, data);
  }

  public static <T> ResponseDto<T> error(String message) {
    return new ResponseDto<>(false, message, null);
  }
}
