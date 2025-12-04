package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"app", "controller", "service", "dto", "api", "file", "searcher"})
public class Application {
  
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}

