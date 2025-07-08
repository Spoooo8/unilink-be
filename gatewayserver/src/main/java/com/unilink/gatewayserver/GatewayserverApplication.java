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
					// Project Service
					.route(p -> p
							.path("/unilink/projects/**")
							.filters(f -> f
									.rewritePath("/unilink/projects/(?<segment>.*)", "/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb://PROJECT-SERVICE"))
					// User Service
					.route(p -> p
							.path("/unilink/users/**")
							.filters(f -> f
									.rewritePath("/unilink/users/(?<segment>.*)", "/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb://USER-SERVICE"))
					// Auth Server OAuth2 Endpoints
					.route(p -> p
							.path("/unilink/auth/oauth2/**")
							.filters(f -> f
									.rewritePath("/unilink/auth/oauth2/(?<segment>.*)", "/oauth2/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
							)
							.uri("lb://AUTH-SERVER"))
					// Auth Server General Endpoints
					.route(p -> p
							.path("/unilink/auth/**")
							.filters(f -> f
									.rewritePath("/unilink/auth/(?<segment>.*)", "/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb://AUTH-SERVER"))
					.route(p -> p
							.path("/unilink/chat/**")
							.filters(f -> f
									.rewritePath("/unilink/chat/(?<segment>.*)", "/chat/${segment}") // ✅ Fix here
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb:ws://CHAT-SERVICE"))

					.build();
		}

	}
