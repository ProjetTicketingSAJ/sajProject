package fr.formation.afpa;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//Class qui scane les packages

@Configuration
@ComponentScan(basePackages = { "fr.formation.afpa" })
public class ApplicationConfigurerAdapter  {

	

	@Bean(name = "messageSource" )
	public MessageSource getMessageResource() {
		ReloadableResourceBundleMessageSource messageResource =  new ReloadableResourceBundleMessageSource();
		messageResource.setDefaultEncoding("UTF-8");
		messageResource.setBasename("classpath:i18n/messages");
	return messageResource;
	
	}
	
	@Bean(name = "localeResolver")
	public LocaleResolver getLocaleResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		
		return resolver;
	}

}