package com.mydemo.service.gpt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptService {
	
	@Autowired
	static WebClient.Builder builder;
		
	public static void main(String args[]) throws Exception{
		
		
//		WebClient webClient = builder.baseUrl("https://api.kakaobrain.com")
//				.build();
		
		WebClient webClient = WebClient.create("https://api.kakaobrain.com");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("prompt", "안녕");
		params.add("max_tokens", "10");
		
		String request = "{\"prompt\":\"안녕\",\"max_tokens\":10}";
		String mono = webClient.post()
				.uri("/v1/inference/kogpt/generation")
		        .headers(httpHeaders -> {
		            httpHeaders.add(HttpHeaders.AUTHORIZATION, "KakaoAK 20154adeb6d7177848847225901f694f");
		            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		        })
		.bodyValue(request)
		.retrieve()
		.bodyToMono(String.class)
		.block();
		
		System.out.println(mono);	
	}

	
	
//	public static void main(String args[]) throws Exception{
//		String response = "";
//        BufferedReader in = null;
//        try {
//        	
//        	String sendUrl = "https://api.kakaobrain.com/v1/inference/kogpt/generation";
//        	
//        	URL url = new URL(sendUrl);
//            // url 연결
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//
//            conn.setRequestProperty("Content-Type", "application/json");	//content-Type 설정
//            conn.setDoOutput(true);	// 서버에서 온 데이터를 출력할 수 있는 상태인지
//            conn.setRequestProperty("Authorization", "KakaoAK " + "20154adeb6d7177848847225901f694f");
//            conn.setRequestMethod("POST");	// GET / POST
//
//            ObjectMapper objectMapper = new ObjectMapper();
//    		TestParm newParm = new TestParm();
//    		//prompt, max_tokens = 1, temperature = 1.0, top_p = 1.0, n = 1
//            newParm.setPrompt("처음이야 이런기분");
//            newParm.setMax_tokens(1);
//            newParm.setTemperature(1.0f);
//            newParm.setTop_p(1.0f);
//            newParm.setN(1);           
//            
//            String jsonInputString = objectMapper.writeValueAsString(newParm);
//            
//            // 서버로 전송할 JSON 형식의 문자열을 정의
//            try(OutputStream os = conn.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes("utf-8"); // JSON 문자열을 바이트 배열로 변환
//                os.write(input, 0, input.length); // 변환된 바이트 배열을 출력 스트림을 통해 전송
//            }           
//            
//            int responseCode = conn.getResponseCode(); // 서버로부터 받은 HTTP 응답 코드를 가져옴
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//
//            String inputLine;
//            StringBuffer sb = new StringBuffer();
//            while((inputLine = in.readLine()) != null) { // response 출력
//                System.out.println(inputLine);
//                sb.append(inputLine);
//            }
//
//            response = sb.toString();
//            
//            log.debug("JSON->",response);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if(in != null){
//                in.close();
//            }
//        }
//	}	

//	public static void main(String args[]) throws Exception{
//		TestParm newParm = new TestParm();
//		//prompt, max_tokens = 1, temperature = 1.0, top_p = 1.0, n = 1
//        newParm.setPrompt("처음이야 이런기분");
//        newParm.setMax_tokens(1);
//        newParm.setTemperature(1.0f);
//        newParm.setTop_p(1.0f);
//        newParm.setN(1);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        headers.add("Authorization", "KakaoAK 20154adeb6d7177848847225901f694f");       
//        
//        WebClient webClient = WebClient.create();
//        webClient.post()
//        .uri("https://api.kakaobrain.com/v1/inference/kogpt/generation")
//        .headers(h -> h.addAll(headers))
//        .bodyValue(BodyInserters.fromValue(newParm))
//        .retrieve()
//        .toEntity(String.class)   //Change here
//        .subscribe(
//          responseEntity -> {
//            // Handle success response here
//            //HttpStatusCode status = responseEntity.getStatusCode();
//        	responseEntity.getStatusCode();
//            URI location = responseEntity.getHeaders().getLocation();
//            //Employee createdEmployee = responseEntity.getBody();    // Response body
//            // handle response as necessary
//            String string = responseEntity.getBody();
//          },
//          error -> {
//            // Handle the error here
//            if (error instanceof WebClientResponseException) {
//              WebClientResponseException ex = (WebClientResponseException) error;
//              //HttpStatusCode status = ex.getStatusCode();
//              
//              System.out.println("Error Status Code: " + ex.getStatusCode());
//              //...
//            } else {
//              // Handle other types of errors
//              System.err.println("An unexpected error occurred: " + error.getMessage());
//            }
//          }
//        );       
//		
////        // Create HttpHeaders object and set multiple headers
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Content-Type", "application/json");
////        headers.add("Authorization", "KakaoAK " + "20154adeb6d7177848847225901f694f");
////        webClient.post()
////                .uri("https://api.kakaobrain.com")
////                .headers(h -> h.addAll(headers))
////                .bodyValue(BodyInserters.fromValue(newParm))
////                .retrieve()
////                .bodyToMono(String.class)
////                .subscribe(response -> log.debug("Response: {}" , response))
////                //.block();
////                	
//	}	
//	
}

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
class TestParm {
    private String prompt; //: prompt,
    private int max_tokens; //'max_tokens': max_tokens,
    private float temperature; //'temperature': temperature,
    private float top_p; // 'top_p': top_p,
    private int n; //'n': n	
}
