package com.hy.restapi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RestApiMapper {
	void insertXmlData(Map<String, String> detailMap);
}
