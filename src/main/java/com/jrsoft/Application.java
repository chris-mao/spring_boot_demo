package com.jrsoft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.jrsoft.app.helper.SpringHelper;

@SpringBootApplication
@MapperScan("com.jrsoft.*.dao")
public class Application {

	public static void main(String[] args) {
		SpringHelper.setApplicationContext(SpringApplication.run(Application.class, args));
	}
}
