package com.hy.restapi.model;

import lombok.Data;

@Data
public class DbConnInfo {
	
	private String HOST;
	private String PORT;
	private String SID;
	private String USER;
	private String PASSWORD;
	private String TABLENAME;


}
