// package com.freediving.communityservice.config;
//
// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.ChannelRegistration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
// import lombok.RequiredArgsConstructor;
//
// @Configuration
// @EnableWebSocketMessageBroker
// @RequiredArgsConstructor
// public class StompConfig implements WebSocketMessageBrokerConfigurer {
//
// 	private final StompHandler stompHandler;
//
// 	@Override
// 	public void registerStompEndpoints(StompEndpointRegistry registry) {
// 		registry.addEndpoint("/ws")
// 			.setAllowedOriginPatterns("*");
// 		// .withSockJS();
// 	}
//
// 	@Override
// 	public void configureMessageBroker(MessageBrokerRegistry registry) {
// 		registry.enableSimpleBroker("/topic");
// 		registry.setApplicationDestinationPrefixes("/chat");
// 	}
//
// 	@Override
// 	public void configureClientInboundChannel(ChannelRegistration registration) {
// 		registration.interceptors(stompHandler);
// 	}
// }
