package com.projectmgmt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.projectmgmt.mapper")
public class ProjectMgmtApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectMgmtApplication.class, args);
    }
}
