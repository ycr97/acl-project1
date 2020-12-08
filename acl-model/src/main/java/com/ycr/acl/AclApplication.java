package com.ycr.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ycr
 * @date 2020/11/27
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.ycr.acl", "com.ycr.common", "com.ycr.security"})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.ycr.acl.mapper"})
@EnableFeignClients
public class AclApplication {

    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }
}
