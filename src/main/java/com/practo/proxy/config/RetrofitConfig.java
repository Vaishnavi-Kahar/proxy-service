package com.practo.proxy.config;

import com.practo.proxy.enums.Services;
import com.practo.proxy.factory.HttpClientFactory;
import com.practo.proxy.http.GenericHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RetrofitConfig {

  private final HttpClientFactory httpClientFactory;

  private final Map<Services, GenericHttpClient> serviceClientMap;

  @Bean
  public Map<Services, GenericHttpClient> createServiceClientMap() {
    for (Services service : Services.values()) {
      serviceClientMap.put(service, httpClientFactory.getClient(service));
    }

    System.out.println(serviceClientMap);
    return serviceClientMap;
  }
}
