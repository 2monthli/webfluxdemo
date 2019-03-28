package me.test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		download();
	}
	
	//返回1一个
	public static void mon(){
		WebClient webClient = WebClient.create();
		Mono<String> mono = webClient.get()
							.uri("http://localhost:8081/user/1")
							//.uri("http://localhost:8081/user/{id}", 1)
							.retrieve()
							.bodyToMono(String.class);
		mono.subscribe(System.out::println);
	}
	
	//返回list
	public static void Flux(){
		WebClient webClient = WebClient.create("http://localhost:8081");
		Flux<String> userFlux = webClient.get()
								.uri("users")
								.retrieve()
								.bodyToFlux(String.class);
		userFlux.subscribe(System.out::println);
	}
	
    
	//form
	public static void form(){
		WebClient webClient = WebClient.create("http://localhost:8081");
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username", "u123");
		map.add("password", "p123");
		Mono<String> mono = webClient
				            .post()
				            .uri("/login")
				            .syncBody(map)
				            .retrieve()
				            .bodyToMono(String.class);
		mono.subscribe(System.out::println);
	}
	
	//json
	public static void json(){
		WebClient webClient = WebClient.create("http://localhost:8081");
		User user = new User();
		user.setName("张三");
		user.setUsername("zhangsan");
		Mono<Void> mono = webClient
						  .post()
						  .uri("/user/add")
						  .syncBody(user)
						  .retrieve()
						  .bodyToMono(Void.class);
		mono.block();
		//mono.subscribe(System.out::println);
	}
	
	//上传文件
	public static void upload(){
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_PNG);
	        HttpEntity<ClassPathResource> entity = new HttpEntity<>(new ClassPathResource("parallel.png"), headers);
	        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
	        parts.add("file", entity);
	        Mono<String> resp = WebClient.create().post()
	                .uri("http://localhost:8081/upload")
	                .contentType(MediaType.MULTIPART_FORM_DATA)
	                .body(BodyInserters.fromMultipartData(parts))
	                .retrieve().bodyToMono(String.class);
	        
	        resp.subscribe(System.out::println);
	}
	
	//下载文件
	public static void download(){
		try {
			 Mono<Resource> resp = WebClient.create().get()
		                .uri("http://www.toolip.gr/captcha?complexity=99&size=60&length=9")
		                .accept(MediaType.IMAGE_PNG)
		                .retrieve().bodyToMono(Resource.class);
		      Resource resource = resp.block();
		      BufferedImage bufferedImage = ImageIO.read(resource.getInputStream());
		      ImageIO.write(bufferedImage, "png", new File("captcha.png"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}

class User{
	private String name;
	private String username;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
