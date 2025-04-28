package com.practo.proxy.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface ConsultHttpClient extends GenericHttpClient {
  /**
   * Generic method to call any endpoint
   *
   * @param path the endpoint path
   * @param queryParams optional query parameters
   * @return Generic response
   */
  @GET("{path}")
  Call<Object> callEndpoint(@Path(value = "path", encoded = true) String path, @QueryMap
  Map<String, String> queryParams);
}
