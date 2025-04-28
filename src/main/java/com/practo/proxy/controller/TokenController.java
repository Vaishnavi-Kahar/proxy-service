package com.practo.proxy.controller;

import com.practo.proxy.dto.ResponseDto;
import com.practo.proxy.dto.TokenRequestDto;
import com.practo.proxy.dto.TokenResponseDto;
import com.practo.proxy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("token/v1")
public class TokenController {

  @Autowired
  private JwtUtil jwtUtil;

  // dont add service to jwt token

  @PostMapping("/generate")
  public ResponseEntity<ResponseDto<TokenResponseDto>> generateToken(
      @RequestBody TokenRequestDto requestDto) {
    String token = jwtUtil.generateToken(requestDto.getEmail());
    TokenResponseDto responseDto = TokenResponseDto.builder().token(token).build();
    return ResponseEntity.ok(ResponseDto.success("Token generated successfully",
        responseDto));
  }
}
