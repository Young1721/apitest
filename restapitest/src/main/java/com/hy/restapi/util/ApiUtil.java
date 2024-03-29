package com.hy.restapi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hy.restapi.model.ApiDocument;

@Configuration
public class ApiUtil {
	public static Document convertStringToXMLDocument(String xmlString) {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {

			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

			return doc;
		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
	}

	public static Map<String, List<Map<String, String>>> parseXML(Node node) {
		Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> headerList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> detailList = new ArrayList<Map<String, String>>();

		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node currentNode = nodeList.item(i);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				if (currentNode.getNodeName().equals("PurchaseOrder")) {
					continue;
				} else if (currentNode.getNodeName().equals("HEADER")) {
					Map<String, String> headerMap = new HashMap<String, String>();
					NodeList childNodeList = currentNode.getChildNodes();

					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);

						try {
							headerMap.put(childNode.getNodeName(), childNode.getChildNodes().item(0).getNodeValue());
						} catch (Exception e) {
							headerMap.put(childNode.getNodeName(), null);
						}

					}

					headerList.add(headerMap);
				}

				else if (currentNode.getNodeName().equals("DETAIL")) {
					Map<String, String> detailMap = new HashMap<String, String>();
					NodeList childNodeList = currentNode.getChildNodes();

					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);

						try {
							detailMap.put(childNode.getNodeName(), childNode.getChildNodes().item(0).getNodeValue());
						} catch (Exception e) {
							detailMap.put(childNode.getNodeName(), null);
						}
					}

					detailList.add(detailMap);
				}
			}
		}

		resultMap.put("headerList", headerList);
		resultMap.put("detailList", detailList);

		return resultMap;
	}

	public FTPClient setFTPUploader(String host, String user, String pwd) throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setDefaultPort(20421);
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		int reply;
		ftpClient.connect(host);// 호스트 연결
		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			throw new Exception("Exception in connecting to FTP Server");
		}
		ftpClient.login(user, pwd);// 로그인
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();

		return ftpClient;
	}

	public void uploadFile(FTPClient ftpClient, String localFileFullName, String fileName, String hostDir, File file)
			throws Exception {
		try (InputStream input = new FileInputStream(file)) {
			ftpClient.storeFile(hostDir + fileName, input);
			// storeFile() 메소드가 전송하는 메소드
		}
	}

	public void disconnect(FTPClient ftpClient) {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException f) {
				f.printStackTrace();
			}
		}
	}

	public ApiDocument getApiDocument(String name, String phoneNumber, String email, String url) {
		JSONObject param = new JSONObject();
		param.put("NAME", name);
		param.put("PHONE_NUMBER", phoneNumber);
		param.put("E_MAIL", email);
		HttpEntity<?> requestEntity = apiClientHttpEntity(param.toString());

		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		restTemplate.getMessageConverters().add(converter);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				String.class);
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();

		return gson.fromJson(responseEntity.getBody(), ApiDocument.class);
	}

	private HttpEntity<?> apiClientHttpEntity(String params) {
		HttpHeaders reqHeaders = new HttpHeaders();
		reqHeaders.set("Content-type", "application/json");
		reqHeaders.set("Accept", "application/json");

		return new HttpEntity<Object>(params, reqHeaders);
	}
}
