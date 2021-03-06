package com.hy.restapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "api-url")
public class ApiUrlProperty {
	private String url;
}
