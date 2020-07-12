package com.example.demo;

import com.example.demo.service.MailService;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class KtbackendApplication {

	private static ApplicationContext ctx;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

//	@Bean
//	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
//		DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
//		System.setProperty("javax.net.ssl.keyStore", "src/main/resources/auth.jks");
//		System.setProperty("javax.net.ssl.keyStorePassword", "passs123");
//		System.setProperty("javax.net.ssl.trustStore", "src/main/resources/auth.jks");
//		System.setProperty("javax.net.ssl.trustStorePassword", "passs123");
//		EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
//		builder.withClientName("account-client");
//		builder.withSystemSSLConfiguration();
//		builder.withMaxTotalConnections(10);
//		builder.withMaxConnectionsPerHost(10);
//		args.setEurekaJerseyClient(builder.build());
//		return args;
//	}

	public static void main(String[] args) {
		ctx = SpringApplication.run(KtbackendApplication.class, args);
	}

	public static MailService getCtx(){
		return (MailService) ctx.getBean("mailService");
	}
}