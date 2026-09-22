package com.example.demo.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 엔드포인트를 만드는 역할이다 라고 명시.
public class HelloBootController {
	
	public HelloBootController() {
		System.out.println("스프링이 생성자를 호출했습니다.");
	}
	
	@GetMapping("/hello") // '/hello' 엔드포인트로 접근 시 브라우저에게 아래 정보를 주겠다.
	public ResponseEntity<String> hello() {
		return new ResponseEntity<>("Hello Boot Controller", HttpStatus.OK);
	}
	
	@GetMapping("/jsp")
	public String viewJsp() {
		return "hellospring";
	}
	
	@GetMapping("/jsp2")
	public String viewJsp2(Model model) {
		model.addAttribute("name", "이동욱");
		model.addAttribute("age", 27);
		model.addAttribute("isDeveloper", true);
		
		return "hellospring2";
	}
	
}
