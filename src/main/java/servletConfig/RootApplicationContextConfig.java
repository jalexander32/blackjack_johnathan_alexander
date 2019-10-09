package servletConfig;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import domain.Player;

@Configuration
@ComponentScan(basePackages = "com.games.Blackjack.blackjackGame")
public class RootApplicationContextConfig {
	//Dependency Injection Configuration
	@Bean
	public Player getPlayer() throws IOException {
		return new Player("Default");
	}
}
