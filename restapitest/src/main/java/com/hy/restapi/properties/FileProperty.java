package com.hy.restapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "file")
public class FileProperty {
	private String storePath;
	private String uploadIp;
	private String uploadUsername;
	private String uploadPassword;
	private String fileName;
	private String fileExt;
}
