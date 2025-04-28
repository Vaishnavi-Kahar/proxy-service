package com.practo.proxy.dto;

import lombok.Data;

@Data
public class TitanResponseDto<T> {

  private T data;
}

