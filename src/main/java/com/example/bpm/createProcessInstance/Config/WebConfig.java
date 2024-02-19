package com.example.bpm.createProcessInstance.Config;

import javax.net.ssl.SSLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

@Configuration
public class WebConfig {
	
	String sslInstance = "SSL";

	@Bean
	public WebClient webClient() throws SSLException{
		SslContext context = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		
		reactor.netty.http.client.HttpClient httpClient = reactor.netty.http.client.HttpClient.create().secure(t -> t.sslContext(context));
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}

	
}
