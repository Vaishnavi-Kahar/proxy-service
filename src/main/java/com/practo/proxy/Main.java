package com.practo.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication(scanBasePackages = {
    "com.practo.proxy",
    "com.practo.commons",
    "com.practo.commons.webutils",
    "com.practo.commons.utils",
    "com.practo.commons.security",
})
public class Main {
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}