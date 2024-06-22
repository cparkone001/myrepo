package com.mydemo.service.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydemo.service.addr.AddressService;
import com.mydemo.service.addr.dto.AddressInfoSvcDto;
import com.mydemo.service.weather.dto.BodyDto;
import com.mydemo.service.weather.dto.ItemDto;
import com.mydemo.service.weather.dto.ItemsObjDto;
import com.mydemo.service.weather.dto.ResponseDto;
import com.mydemo.service.weather.dto.WeatherForecastSvcDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherForecastService {
	
	@Autowired
	AddressService addressService; 
	
	/**
	 * 지역별 단기 일기예보
	 * @param parm
	 * @return
	 * @throws Exception 
	 */
	public WeatherForecastSvcDto getVilageFcst(WeatherForecastSvcDto parm) throws Exception {

		AddressInfoSvcDto p = new AddressInfoSvcDto();
		p.setLatitudeVal(parm.getLatitude());
		p.setLongitudeVal(parm.getLongitude());
		
		AddressInfoSvcDto retAddrSvc = addressService.getGridXyValue(p);

		WeatherForecastSvcDto parm4Weather = new WeatherForecastSvcDto();
		parm4Weather.setNx(retAddrSvc.getGrdXVal());
		parm4Weather.setNy(retAddrSvc.getGrdYVal());	
		parm4Weather.setBaseDate(parm.getBaseDate());
		parm4Weather.setBaseTime(parm.getBaseTime());
		
		WeatherForecastSvcDto result = this.requestWeatherService(parm4Weather);
		result.setAddressName(retAddrSvc.getAddressName());
		
		log.debug("getAddressName=>",retAddrSvc.getAddressName());
		
		return result;
	}
	
	private WeatherForecastSvcDto requestWeatherService(WeatherForecastSvcDto parm) {
		
		log.debug("~~~~~~~~~~~~~~~~~~~~~~~{}",parm);
		
		try {
	       StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
	        
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=PDbcAt8dCcULK2WrhkKSkcCrhoOCsckB2NwxGWjpgeF54kb%2Bpv6xUP1NAFszL2y%2BWRuBUkB9cYXlKjjZeFshZA%3D%3D");

	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("264", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(parm.getBaseDate(), "UTF-8")); /*‘21년 6월 28일 발표*/
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(parm.getBaseTime(), "UTF-8")); /*06시 발표(정시단위) */
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(parm.getNx(), "UTF-8")); /*예보지점의 X 좌표값*/
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(parm.getNy(), "UTF-8")); /*예보지점의 Y 좌표값*/		
	        
	        log.debug("~~~~~=>{}",urlBuilder.toString());
	        
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        log.debug("Row JsonData=> {}",sb.toString());  	
	        
	        Map m = WeatherForecastService.getCodeMap();
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        WeatherForecastSvcDto result = objectMapper.readValue(sb.toString(), WeatherForecastSvcDto.class);
	        
	        /////////////////////////////////////////////////////////////////////////////////////////////////
	        // 카타고리 한글명을 넣기 위한 작업
	        ResponseDto response = new ResponseDto();
	        BodyDto body = new BodyDto();
	        ItemsObjDto items = new ItemsObjDto();
	        List<ItemDto> tempItem = new ArrayList<ItemDto>();
	        
	        //{"response":{"header":{"resultCode":"03","resultMsg":"NO_DATA"}}}
	        
	        result.setResultCode(result.getResponse().getHeader().getResultCode());
	        
	        if(!"00".equals(result.getResultCode())) {
	        	return result;
	        }
	        
	        for(ItemDto anItem : result.getResponse().getBody().getItems().getItem()) {
	        	//log.debug("{}",anItem);
	        	log.debug("예상일=>{}, 예상시간=>{}, 카타고리=>{}, 값=>{}",anItem.getFcstDate(),anItem.getFcstTime() ,m.get(anItem.getCategory()) ,anItem.getFcstValue() );
	        	anItem.setCategoryName((String) m.get(anItem.getCategory()));
	        	tempItem.add(anItem);
	        }
	        items.setItem(tempItem);
	        body.setItems(items);
	        response.setBody(body);
	        result.setResponse(response);
	        ////////////////////////////////////////////////////////////////////////////////////////////////////
	        
	        return result;
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		
		return null;
	}
	
	private static  Map getCodeMap() {

		StringBuffer b = new StringBuffer();
		
		b.append("{")
		.append("	   \"POP\" : \"강수확률[%]\"")
		.append("	  ,\"PTY\" : \"강수형태[코드값]\"")
		.append("	  ,\"PCP\" : \"1시간 강수량[범주 (1 mm)]\"")
		.append("	  ,\"REH\" : \"습도[%]\"")
		.append("	  ,\"SNO\" : \"1시간 신적설[범주(1 cm)]\"")
		.append("	  ,\"SKY\" : \"하늘상태[코드값]\"")
		.append("	  ,\"TMP\" : \"1시간 기온[℃]\"")
		.append("	  ,\"TMN\" : \"일 최저기온[℃]\"")
		.append("	  ,\"TMX\" : \"일 최고기온[℃]\"")
		.append("	  ,\"UUU\" : \"풍속(동서성분)[m/s]\"")
		.append("	  ,\"VVV\" : \"풍속(남북성분)[m/s]\"")
		.append("	  ,\"WAV\" : \"파고[M]\"")
		.append("	  ,\"VEC\" : \"풍향[deg]\"")
		.append("	  ,\"WSD\" : \"풍속[m/s]\"")
		.append("	  ,\"T1H\" : \"기온[℃]\"")
		.append("	  ,\"RN1\" : \"1시간 강수량[mm]\"")
		.append("	  ,\"UUU\" : \"동서바람성분[m/s]\"")
		.append("	  ,\"VVV\" : \"남북바람성분[m/s]\"")
		.append("	  ,\"REH\" : \"습도[%]\"")
		.append("	  ,\"PTY\" : \"강수형태[코드값]\"")
		.append("	  ,\"VEC\" : \"풍향[deg]\"")
		.append("	  ,\"WSD\" : \"풍속[m/s]\"")
		.append("	  ,\"T1H\" : \"기온[℃]\"")
		.append("	  ,\"RN1\" : \"1시간 강수량[범주 (1 mm)]\"")
		.append("	  ,\"SKY\" : \"하늘상태[코드값]\"")
		.append("	  ,\"UUU\" : \"동서바람성분[m/s]\"")
		.append("	  ,\"VVV\" : \"남북바람성분[m/s]\"")
		.append("	  ,\"REH\" : \"습도[%]\"")
		.append("	  ,\"PTY\" : \"강수형태[코드값]\"")
		.append("	  ,\"LGT\" : \"낙뢰[kA(킬로암페어)]\"")
		.append("	  ,\"VEC\" : \"풍향[deg]\"")
		.append("	  ,\"WSD\" : \"풍속[m/s]\"")
		.append("	} ");
		
		try {
			Map<String,Object> result =  new ObjectMapper().readValue(b.toString(), HashMap.class);
			return result;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
	
    public static void main(String[] args) throws IOException {
    	
    	WeatherForecastSvcDto parm = new WeatherForecastSvcDto();
    	parm.setBaseDate("20240517");
    	parm.setBaseTime("0500");
    	parm.setNumOfRows("1000");
    	
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=PDbcAt8dCcULK2WrhkKSkcCrhoOCsckB2NwxGWjpgeF54kb%2Bpv6xUP1NAFszL2y%2BWRuBUkB9cYXlKjjZeFshZA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(parm.getBaseDate(), "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(parm.getBaseTime(), "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
        
        //http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey=PDbcAt8dCcULK2WrhkKSkcCrhoOCsckB2NwxGWjpgeF54kb%2Bpv6xUP1NAFszL2y%2BWRuBUkB9cYXlKjjZeFshZA%3D%3D&pageNo=1&numOfRows=1000&dataType=XML&base_date=20240517&base_time=0600&nx=55&ny=127&dataType=JSON
        
        //https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=PDbcAt8dCcULK2WrhkKSkcCrhoOCsckB2NwxGWjpgeF54kb%2Bpv6xUP1NAFszL2y%2BWRuBUkB9cYXlKjjZeFshZA%3D%3D&numOfRows=10000&pageNo=1&base_date=20240515&base_time=0800&dataType=JSON&nx=55&ny=127
        
        //http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=PDbcAt8dCcULK2WrhkKSkcCrhoOCsckB2NwxGWjpgeF54kb%2Bpv6xUP1NAFszL2y%2BWRuBUkB9cYXlKjjZeFshZA%3D%3D&numOfRows=10&pageNo=1&base_date=20210628&base_time=1030&nx=55&ny=127&dataType=JSON       
        
        log.debug("=>{}",urlBuilder.toString());
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString()); 
        
        Map m = getCodeMap();
        
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherForecastSvcDto result = objectMapper.readValue(sb.toString(), WeatherForecastSvcDto.class);
        
        for(ItemDto anItem : result.getResponse().getBody().getItems().getItem()) {
        	//log.debug("{}",anItem);
        	log.debug("예상일=>{}, 예상시간=>{}, 카타고리=>{}, 값=>{}",anItem.getFcstDate(),anItem.getFcstTime() ,m.get(anItem.getCategory()) ,anItem.getFcstValue() );
        }
        
    }	
}
