package com.lina.springmagazine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  //생성시간 바꼈을때 자동으로 업데이트
@SpringBootApplication
public class SpringMagazineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMagazineApplication.class, args);
    }

}
