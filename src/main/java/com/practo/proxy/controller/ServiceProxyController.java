package com.practo.proxy.controller;

import com.practo.proxy.dto.ResponseDto;
import com.practo.proxy.dto.ServiceCallRequestDto;
import com.practo.proxy.entity.User;
import com.practo.proxy.repository.UserRepository;
import com.practo.proxy.service.ProxyService;
import com.practo.proxy.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ServiceProxyController {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private ProxyService proxyService;

  @Autowired
  private UserRepository userRepository;


  @PostMapping("/call-service")
  public ResponseEntity<?> callService(
      @RequestHeader("X-JWT-TOKEN") String token,
      @RequestBody ServiceCallRequestDto requestDto) {

    try {
      // Validate token and extract claims
      Claims claims = jwtUtil.validateTokenAndGetClaims(token);
      String userEmail = claims.getSubject();

      // Verify user
      if(userRepository.findByEmail(userEmail).isEmpty()){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ResponseDto.error("User does not exist"));
      }

      // Forward the request to the service
      Object response = proxyService.callService(
          requestDto.getService(),
          requestDto.getUrl(),
          requestDto.getPathParams(),
          requestDto.getQueryParams()
      );

      return ResponseEntity.ok(ResponseDto.success("Service call successful", response));

    } catch (ExpiredJwtException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(ResponseDto.error("Token has expired"));
    } catch (JwtException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(ResponseDto.error("Invalid token"));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(ResponseDto.error("Error calling service: " + e.getMessage()));
    }
  }
}
