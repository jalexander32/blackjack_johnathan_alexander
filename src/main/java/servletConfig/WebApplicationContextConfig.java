package servletConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Deprecated
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.games.Blackjack.blackjackGame")
public class WebApplicationContextConfig implements WebMvcConfigurer {
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		//resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/blackjackGameUI");
		resolver.setSuffix(".html");
		
		return resolver;
	}
}
