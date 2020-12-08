package com.ycr.tmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ycr
 * @date 2020/12/3
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ycr.common", "com.ycr.tmodel"})
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
