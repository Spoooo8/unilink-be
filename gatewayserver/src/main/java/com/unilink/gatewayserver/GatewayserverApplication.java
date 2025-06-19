package com.unilink.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}
	@Bean
	public RouteLocator unilinkRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/unilink/projects/**")
						.filters(f -> f
								.rewritePath("/unilink/projects/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://PROJECT-SERVICE"))
				.route(p -> p
						.path("/unilink/users/**")
						.filters(f -> f
								.rewritePath("/unilink/users/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://USER-SERVICE"))
				.route(p -> p
						.path("/unilink/auth/**")
						.filters(f -> f
								.rewritePath("/unilink/auth/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://AUTH-SERVER"))
				.build();
	}


}
