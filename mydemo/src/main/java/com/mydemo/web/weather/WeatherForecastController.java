package com.mydemo.web.weather;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mydemo.service.addr.AddressService;
import com.mydemo.service.addr.dto.AddressInfoSvcDto;
import com.mydemo.service.weather.WeatherForecastService;
import com.mydemo.service.weather.dto.ItemDto;
import com.mydemo.service.weather.dto.WeatherForecastSvcDto;
import com.mydemo.web.weather.dto.WeatherForecastWebDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/weather")
public class WeatherForecastController {
	
	@Autowired
	WeatherForecastService weatherForecastService;

	@Autowired
	AddressService addressService;
	
    @GetMapping("/weatherPage")
    public String studentPage() {
    	return "/pages/weather/weatherList";
    }
    
    @GetMapping("/weatherPage2")
    public String studentPage2() {
    	return "/pages/weather/weatherList2";
    }

    @GetMapping("/addrPage")
    public String viewAddrPage () {
    	return "pages/weather/AddressView";
    }

    @GetMapping("/test")
    public String test() {
    	return "/pages/weather/weatherSampleMain";
    }
   
    
    
    /**
     * 단기 기상정보 조회
     * @param parm
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/getAddrName") 
    @ResponseBody
    public WeatherForecastWebDto getAddrName(@RequestBody WeatherForecastWebDto parm) throws Exception { 	
    	log.debug("U get getAddrName");
    	
    	AddressInfoSvcDto p = new AddressInfoSvcDto();
    	p.setLatitudeVal(parm.getLatitude());
    	p.setLongitudeVal(parm.getLongitude());
    	AddressInfoSvcDto returnSvc = addressService.getGridXyValue(p);
    	
    	WeatherForecastWebDto rt = new WeatherForecastWebDto();
    	rt.setAddressName(returnSvc.getAddressName());
//    	rt.setNx(null);
//    	rt.setNy(null);
    	
    	return rt;
    }       
    /**
     * 단기 기상정보 조회
     * @param parm
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/getWeatherForecast") 
    @ResponseBody
    public WeatherForecastWebDto getWeatherForecast(@RequestBody WeatherForecastWebDto parm) throws Exception { 	
    	log.debug("U get getWeatherForecast");
    	
    	WeatherForecastSvcDto p = new WeatherForecastSvcDto();
    	
    	p.setBaseDate(parm.getBaseDate());
    	p.setBaseTime(parm.getBaseTime());
    	
    	p.setLatitude(parm.getLatitude());
    	p.setLongitude(parm.getLongitude());
    	
    	WeatherForecastSvcDto svcDto = weatherForecastService.getVilageFcst(p);
    	
    	List<ItemDto> list = null;
    	if("00".equals(svcDto.getResultCode())) {
    		list = svcDto.getResponse().getBody().getItems().getItem();
    	}
    	
    	WeatherForecastWebDto rt = new WeatherForecastWebDto();
    	rt.setItemList(list);
    	
    	return rt;
    }       
    
}
