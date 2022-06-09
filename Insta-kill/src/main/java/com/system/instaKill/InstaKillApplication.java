package com.system.instaKill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.system.instaKill.mapper")
public class InstaKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstaKillApplication.class, args);
    }

}
