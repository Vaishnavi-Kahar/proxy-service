package com.practo.proxy.http;

import retrofit2.Call;
import java.util.Map;

public interface GenericHttpClient {
  Call<Object> callEndpoint(String path, Map<String, String> queryParams);
}
