package controllers;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import managers.GameManager;

@RestController
public class GameController extends GameManager{
	
	public GameController() throws IOException, ClassNotFoundException, SQLException{
	
	}
	@PostMapping("hit")
	public String hit(HttpRequest request) {
		System.out.println("THIS IS WORKING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//return new Object[]{"6", "hearts", 6, null};
		return "Hello, World";
	}
	@RequestMapping(value = "/hits", method = RequestMethod.POST)
	public String hit() {
		System.out.println("THIS IS WORKING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		//return new Object[]{"6", "hearts", 6, null};
		return "Hello, World";
	}
	@PostMapping("/")
	public String getString() {
		System.out.println("JOHNATHAN");
		return "Hello, World";
	}
}
