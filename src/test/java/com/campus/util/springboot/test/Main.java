package com.campus.util.springboot.test;

import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import io.seata.spring.boot.autoconfigure.SeataCoreAutoConfiguration;
import io.seata.spring.boot.autoconfigure.SeataDataSourceAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        SeataCoreAutoConfiguration.class,
        SeataAutoConfiguration.class,
        SeataDataSourceAutoConfiguration.class})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
