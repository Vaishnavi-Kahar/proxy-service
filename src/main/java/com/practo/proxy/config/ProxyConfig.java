package com.practo.proxy.config;

import com.practo.commons.security.nonce.NoOpNonceStorage;
import com.practo.commons.security.nonce.NonceStorage;
import com.practo.proxy.enums.Services;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ProxyConfig {

  @Bean
  protected NonceStorage nonceStorage() {
    return new NoOpNonceStorage();
  }

}
