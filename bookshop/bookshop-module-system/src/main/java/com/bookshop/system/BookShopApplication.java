package com.bookshop.system;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.bookshop.system.**.mapper*"})
public class BookShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class,args);
    }
}
