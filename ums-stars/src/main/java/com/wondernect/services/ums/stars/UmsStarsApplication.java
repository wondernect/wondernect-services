package com.wondernect.services.ums.stars;

import com.wondernect.elements.boot.application.WondernectBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@EntityScan(basePackages = {
        "com.wondernect.*"
})
@EnableJpaRepositories(basePackages = {
        "com.wondernect.*"
})
@SpringBootApplication
public class UmsStarsApplication extends WondernectBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmsStarsApplication.class, args);
    }

    @Override
    public void initAfterBootFinished() {
        System.out.println("ums stars server start success !!!");
    }
}
