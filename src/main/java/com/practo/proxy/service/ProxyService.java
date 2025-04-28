package com.practo.proxy.service;

import com.practo.proxy.dto.ProviderDto;
import com.practo.proxy.dto.TitanResponseDto;
import com.practo.proxy.enums.Services;
import com.practo.proxy.exception.ServiceCallException;
import com.practo.proxy.http.ConsultHttpClient;
import com.practo.proxy.http.GenericHttpClient;
import com.practo.proxy.http.TitanHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;

@Service
public class ProxyService {

  @Autowired
  private Map<Services,GenericHttpClient> serviceClientMap;

  /**
   * Calls a service endpoint
   *
   * @param serviceName name of the service (e.g., "titan")
   * @param url endpoint path (e.g., "content/v1/providers/{providerId}")
   * @param pathParams parameters to substitute in the path (e.g., providerId -> "123")
   * @param queryParams query parameters to add to the request
   * @return the response from the service
   * @throws ServiceCallException if there's an error calling the service
   */
  public Object callService(String serviceName, String url,
                            Map<String, String> pathParams,
                            Map<String, String> queryParams) throws ServiceCallException {

    // Replace path parameters in the URL
    String processedUrl = url;
    for (Map.Entry<String, String> entry : pathParams.entrySet()) {
      processedUrl = processedUrl.replace("{" + entry.getKey() + "}", entry.getValue());
    }

    System.out.println(serviceClientMap);
    try {
//      Call<TitanResponseDto<ProviderDto>> call = titanClient.getProvider(providerId);
//      Response<TitanResponseDto<ProviderDto>> response = call.execute();
//      Call<Object> call = null;
//      Object call = serviceClientMap.get(serviceName);
//      Call<Object> callRequest  = call.callEndpoint(processedUrl,queryParams);

//      switch (serviceName) {
//        case "titan":
//          call = titanClient.callEndpoint(processedUrl, queryParams);
//          break;
//
//        case "consult":
//          call = consultClient.callEndpoint(processedUrl, queryParams);
//          break;
//
//        default:
//          throw new IllegalArgumentException("Unsupported service: " + serviceName);
//      }
      GenericHttpClient client = serviceClientMap.get(Services.valueOf(serviceName));
      Call<Object> call = client.callEndpoint(processedUrl,queryParams);

      Response<Object> response = call.execute();


      if (!response.isSuccessful()) {
        String errorBody = response.errorBody() != null ? response.errorBody().string() : "No error body";
        System.out.println("Error calling " + serviceName + ": " + response.code() + " " + response.message());
        System.out.println("Error body: " + errorBody);
        throw new ServiceCallException("Error calling " + serviceName + ": " +
            response.code() + " " + response.message());
      }

      return response.body();
    } catch (IOException e) {
      System.out.println("Exception calling service: " + e.getMessage());
      e.printStackTrace();
      throw new ServiceCallException("Error calling " + serviceName + ": " + e.getMessage(), e);
    }
  }
}