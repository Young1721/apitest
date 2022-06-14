package com.hy.restapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.restapi.mapper.RestApiMapper;


@Service
public class RestApiService {
	@Autowired
	private RestApiMapper restapiMapper;
	
	public void insertXmlData(Map<String, List<Map<String, String>>> resultMap) {
		List<Map<String, String>> headerList = resultMap.get("headerList");
  	  	List<Map<String, String>> detailList = resultMap.get("detailList");
  	  	
  	  	for(int i = 0; i < detailList.size(); i++) {
  	  		Map<String, String> detailMap = detailList.get(i);
  	  		for(int j = 0; j < headerList.size(); j++) {
  	  			Map<String, String> headerMap = headerList.get(j);
  	  			if(detailMap.get("ORDER_NUM").equals(headerMap.get("ORDER_NUM"))) {
	  	  			detailMap.put("ORDER_ID", headerMap.get("ORDER_ID"));
		  	  		detailMap.put("ORDER_DATE", headerMap.get("ORDER_DATE"));
		  	  		detailMap.put("ORDER_PRICE", headerMap.get("ORDER_PRICE"));
		  	  		detailMap.put("ORDER_QTY", headerMap.get("ORDER_QTY"));
		  	  		detailMap.put("RECEIVER_NAME", headerMap.get("RECEIVER_NAME"));
		  	  		detailMap.put("RECEIVER_NO", headerMap.get("RECEIVER_NO"));
		  	  		detailMap.put("ETA_DATE", headerMap.get("ETA_DATE"));
		  	  		detailMap.put("DESTINATION", headerMap.get("DESTINATION"));
		  	  		detailMap.put("DESCIPTION", headerMap.get("DESCIPTION"));
		  	  		break;
  	  			}
  	  		}
  	  		restapiMapper.insertXmlData(detailMap);
  	  	}
	}
	
}
