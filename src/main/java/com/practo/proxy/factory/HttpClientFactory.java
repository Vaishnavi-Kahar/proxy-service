package com.practo.proxy.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practo.commons.security.config.SecureProperties;
import com.practo.commons.webutils.enums.Subdomain;
import com.practo.commons.webutils.generator.api.UrlGenerator;
import com.practo.proxy.enums.Services;
import com.practo.proxy.http.ConsultHttpClient;
import com.practo.proxy.http.GenericHttpClient;
import com.practo.proxy.http.TitanHttpClient;
import com.practo.proxy.util.HmacInterceptor;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class HttpClientFactory {

  private static final int DEFAULT_TIMEOUT_SECONDS = 10;

  private final SecureProperties secureProperties;
  private final UrlGenerator urlGenerator;
  private final ObjectMapper objectMapper;

  public GenericHttpClient getClient(Services service) {
    switch (service) {
      case consult:
        return createClient(ConsultHttpClient.class, Subdomain.consult, "consult");
      case titan:
        return createClient(TitanHttpClient.class, Subdomain.titan, "titan");
      default:
        throw new IllegalArgumentException("Unsupported service: " + service);
    }
  }

  private <T extends GenericHttpClient> T createClient(Class<T> clientClass, Subdomain subdomain, String credentialKey) {
    SecureProperties.ServiceCredential serviceCredential = secureProperties.getServiceCredential(credentialKey);
    String baseUrl = urlGenerator.getUrl(subdomain);

    Interceptor interceptor = new HmacInterceptor(serviceCredential);

    OkHttpClient httpClient = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .readTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS).toMillis(), TimeUnit.MILLISECONDS)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .client(httpClient)
        .build();

    return retrofit.create(clientClass);
  }
}
