package com.mydemo.service.addr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydemo.mapper.addr.AddressInfoMapper;
import com.mydemo.mapper.addr.model.AddressInfoDto;
import com.mydemo.service.addr.dto.AddrDocument;
import com.mydemo.service.addr.dto.AddressInfoSvcDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
	
	@Autowired
	AddressInfoMapper addressInfoMapper;
	
	/**
	 * 경도/위도 좌표로 카카오API를 통하여 주소 정보를 구한다. 
	 * @param 경도, 위도 
	 * @return 날씨 지역정보를 위한 격자형 X, Y 값
	 * @throws Exception
	 */
	public AddressInfoSvcDto getGridXyValue(AddressInfoSvcDto parm) throws Exception {
		
		AddressInfoSvcDto fromKakaoAddr = this.connectKakaoAddrServer(parm);
		
		if(fromKakaoAddr.getMeta().getTotalCount() == 0 || fromKakaoAddr == null) {
			throw new Exception("위치 정보가 없습니다. 재 시도하십시오.");
		}
		
		List<AddrDocument> addrDocList = fromKakaoAddr.getDocuments();
		AddrDocument pickDoc = null;
		for(AddrDocument aDoc : addrDocList ) {
			if("H".equals(aDoc.getRegionType()) ) { //지역타입이 H 인 행정동 
				pickDoc = aDoc;
			}
		}
		
		AddressInfoDto p = new AddressInfoDto();
		p.setFirstStage(pickDoc.getRegion1depthName()); //도/시
		p.setThirdStage(pickDoc.getRegion3depthName()); //동
		
		//region_1depth_name":"서울특별시","region_2depth_name":"송파구"
		
		//주소 정보로 DB로 부터, 날짜를 위한 격자 Grd X, Y 값을 가져온다.
		List<AddressInfoDto> addressList = addressInfoMapper.selectGridXYInfo(p);
			
		AddressInfoDto addrDto = addressList.get(0);
		addrDto.getGrdXVal();
		addrDto.getGrdYVal();
		
		AddressInfoSvcDto rt = new AddressInfoSvcDto();
		rt.setAddressName(pickDoc.getRegion1depthName() +" "+ pickDoc.getRegion2depthName());
		rt.setGrdXVal(addrDto.getGrdXVal());
		rt.setGrdYVal(addrDto.getGrdYVal());
		return rt;
	}
	
	
	/**
	 * 경도 위도 좌표를 보내면 카카오 Web service(API)로 부터 주소 정보를 가져온다.
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	private AddressInfoSvcDto connectKakaoAddrServer(AddressInfoSvcDto parm) throws Exception {
		
		String response = "";
        BufferedReader in = null;
        try {
        	
        	String sendUrl = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=" +parm.getLongitudeVal() + "&y="+ parm.getLatitudeVal();
            URL url = new URL(sendUrl);
            // url 연결
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");	//content-Type 설정
            conn.setDoOutput(true);	// 서버에서 온 데이터를 출력할 수 있는 상태인지
            conn.setRequestProperty("Authorization", "KakaoAK " + "20154adeb6d7177848847225901f694f");
            conn.setRequestMethod("GET");	// GET / POST

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String inputLine;
            StringBuffer sb = new StringBuffer();
            while((inputLine = in.readLine()) != null) { // response 출력
                System.out.println(inputLine);
                sb.append(inputLine);
            }
            response = sb.toString();
            
            log.debug("response==>{}",response);
            
	        ObjectMapper objectMapper = new ObjectMapper();
	        AddressInfoSvcDto result = objectMapper.readValue(response, AddressInfoSvcDto.class);            
           
	        return result;
	        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                in.close();
            }
        }	
        return null;
	}
	
	public static void main(String args[]) throws Exception{
		String response = "";
        BufferedReader in = null;
        try {
        	
        	
        	
        	
        	String sendUrl = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=126.98774913822886&y=37.565673167085976";
        	//String sendUrl = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=37.56567175993442&y=126.9877251159322";
        	
        	URL url = new URL(sendUrl);
            // url 연결
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestProperty("Content-Type", "application/json");	//content-Type 설정
            conn.setDoOutput(true);	// 서버에서 온 데이터를 출력할 수 있는 상태인지
            conn.setRequestProperty("Authorization", "KakaoAK " + "20154adeb6d7177848847225901f694f");
            conn.setRequestMethod("GET");	// GET / POST

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String inputLine;
            StringBuffer sb = new StringBuffer();
            while((inputLine = in.readLine()) != null) { // response 출력
                System.out.println(inputLine);
                sb.append(inputLine);
            }

            response = sb.toString();
            
            log.debug("JSON->",response);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null){
                in.close();
            }
        }
	}
}
