package com.scai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author Suny
 * @date 2018-10-28
 */
@MapperScan(basePackages = "com.scai.**.mapper")
@SpringBootApplication
@Configuration
public class ScaisApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScaisApplication.class, args);
    }
}