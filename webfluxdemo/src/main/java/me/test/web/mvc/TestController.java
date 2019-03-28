package me.test.web.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mvc")
public class TestController {

	@GetMapping("/test")
	public String test(){
		return "my name is leap";
	}
	
	@GetMapping("/test2")
	public String test2(String id,String age){
		return id+" "+age;
	}
}
