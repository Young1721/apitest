package com.hy.restapi.model;

import lombok.Data;

@Data
public class ApiDocument {
	private String XML_DATA;
	private String JSON_DATA;
	private DbConnInfo DB_CONN_INFO;
	private FtpConnInfo FTP_CONN_INFO;
}
