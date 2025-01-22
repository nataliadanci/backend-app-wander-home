package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(scanBasePackages = "demo")
@EnableJpaRepositories("demo.repository")
@ComponentScan(basePackages= {"demo.controller", "demo.service", "demo.transformers"})
@EntityScan("demo.entity")
@CrossOrigin(origins = "",  allowedHeaders = "")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}