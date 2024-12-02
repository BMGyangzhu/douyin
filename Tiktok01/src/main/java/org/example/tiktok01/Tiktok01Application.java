package org.example.tiktok01;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "org.example.tiktok01.mapper")
@EnableTransactionManagement
public class Tiktok01Application {

    public static void main(String[] args) {
        SpringApplication.run(Tiktok01Application.class, args);
    }
        
}
