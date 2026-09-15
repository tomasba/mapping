package com.learn.mapping;

import org.springframework.boot.SpringApplication;

public class TestMappingApplication {

    public static void main(String[] args) {
        SpringApplication.from(MappingApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
