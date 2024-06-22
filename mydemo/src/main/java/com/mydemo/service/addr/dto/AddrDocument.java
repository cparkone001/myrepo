package com.mydemo.service.addr.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AddrDocument {
	
	@JsonAlias("region_type")
	private String regionType;
	
	@JsonProperty("address_name")
	private String addressName;
	
	private String code;
	
	@JsonProperty("region_1depth_name")
	private String region1depthName;
	
	@JsonProperty("region_2depth_name")
	private String region2depthName;
	
	@JsonProperty("region_3depth_name")
	private String region3depthName;

	@JsonProperty("region_4depth_name")
	private String region4depthName;
	
	private BigDecimal x;
	private BigDecimal y;
}
