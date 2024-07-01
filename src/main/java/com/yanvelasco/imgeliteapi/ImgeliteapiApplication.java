package com.yanvelasco.imgeliteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImgeliteapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImgeliteapiApplication.class, args);
    }

}
