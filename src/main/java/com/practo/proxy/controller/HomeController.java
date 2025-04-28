package com.practo.proxy.controller;

import com.practo.proxy.dto.LoginRequestDto;
import com.practo.proxy.dto.ResponseDto;
import com.practo.proxy.dto.SignupRequestDto;
import com.practo.proxy.entity.User;
import com.practo.proxy.enums.Services;
import com.practo.proxy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("home/v1/")
public class HomeController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Signup user
   * @param requestDto
   * @return
   */
  @PostMapping("/signup")
  public ResponseEntity<ResponseDto<String>> signup(@RequestBody SignupRequestDto requestDto) {
    if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
      return ResponseEntity.badRequest().body(ResponseDto.error("User already exists!"));
    }

    User user = new User();
    user.setName(requestDto.getName());
    user.setEmail(requestDto.getEmail());
    user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
    user.setCreatedAt(LocalDateTime.now());
    user.setModifiedAt(LocalDateTime.now());

    userRepository.save(user);
    return ResponseEntity.ok(ResponseDto.success("Signup successful!", user.getName()));
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<String>> login(@RequestBody LoginRequestDto requestDto) {
    Optional<User> userOptional = userRepository.findByEmail(requestDto.getEmail());

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
        return ResponseEntity.ok(ResponseDto.success("Login successful!", user.getName()));
      } else {
        return ResponseEntity.badRequest().body(ResponseDto.error("Invalid credentials!"));
      }
    } else {
      return ResponseEntity.badRequest().body(ResponseDto.error("User not found!"));
    }
  }

  @GetMapping("/services")
  public ResponseEntity<ResponseDto<List<String>>> getServices() {
    return ResponseEntity.ok(ResponseDto.success("Services fetched successfully!", Services.getAllServices()));
  }

}
