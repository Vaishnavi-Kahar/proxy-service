package com.practo.proxy.exception;

public class ServiceCallException extends Exception {

  public ServiceCallException(String message) {
    super(message);
  }

  public ServiceCallException(String message, Throwable cause) {
    super(message, cause);
  }
}