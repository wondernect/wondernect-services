package com.wondernect.services.ums.file;

import com.wondernect.elements.boot.application.WondernectBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = "com.wondernect.*"
)
@SpringBootApplication
public class UmsFileApplication extends WondernectBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmsFileApplication.class, args);
    }

    @Override
    public void initAfterBootFinished() {
        System.out.println("UMS File start success !!!");
    }
}
