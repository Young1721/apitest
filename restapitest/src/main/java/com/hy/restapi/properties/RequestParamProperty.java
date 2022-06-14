package com.hy.restapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "request-prm")
public class RequestParamProperty {
	private String name;
	private String phoneNumber;
	private String email;
}
