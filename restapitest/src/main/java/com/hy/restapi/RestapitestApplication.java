package com.hy.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hy.restapi.properties.ApiUrlProperty;
import com.hy.restapi.properties.FileProperty;
import com.hy.restapi.properties.RequestParamProperty;

@RestController
@SpringBootApplication
@EnableConfigurationProperties({
	FileProperty.class,
	RequestParamProperty.class,
	ApiUrlProperty.class
})
public class RestapitestApplication {
	
	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(RestapitestApplication.class, args);
	}

}
