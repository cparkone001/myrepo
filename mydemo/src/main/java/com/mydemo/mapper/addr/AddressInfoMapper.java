package com.mydemo.mapper.addr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mydemo.mapper.addr.model.AddressInfoDto;

@Mapper
public interface AddressInfoMapper {
	List<AddressInfoDto> selectGridXYInfo(AddressInfoDto p);
}
