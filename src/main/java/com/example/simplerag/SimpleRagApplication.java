package com.example.simplerag;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SimpleRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleRagApplication.class, args);
	}

	/**
	 * Customizes the RestClient by adding a request interceptor that logs the
	 * request details.
	 *
	 * @return a RestClientCustomizer that adds a logging interceptor to the
	 *         RestClient
	 */
	@Bean
	RestClientCustomizer restClientCustomizer() {
		return restClientBuilder -> restClientBuilder.requestInterceptor(new ClientHttpRequestInterceptor() {
			@Override
			public @NonNull ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body,
					@NonNull ClientHttpRequestExecution execution) throws IOException {
				log.info("Request: {} {}\n{}", request.getMethod(), request.getURI(), new String(body));
				return execution.execute(request, body);
			}
		});
	}

}
