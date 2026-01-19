package com.example.controller;

import com.example.dto.Orders;
import com.example.dto.User;
import com.example.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/bff")
public class ApiGatewayController {

    private final WebClient webClient;

    // ⭐ Usa el WebClient configurado, NO el builder
    public ApiGatewayController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/users/{id}")
    public Mono<UserResponse> getUser(@PathVariable String id) {
        log.info("Obteniendo usuario con id: {}", id);

        Mono<User> userMono = webClient.get()
                .uri("http://localhost:8080/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);

        Mono<Orders> ordersMono = webClient.get()
                .uri("http://localhost:8080/orders/user/{id}", id)
                .retrieve()
                .bodyToMono(Orders.class);

        return Mono.zip(userMono, ordersMono)
                .map(tuple -> new UserResponse(
                        tuple.getT1(),
                        tuple.getT2()
                ));
    }

    @GetMapping("/users")
    public Flux<User> getAllUsers() {
        log.info("Request recibida para obtener todos los usuarios");

        return webClient.get()
                .uri("http://localhost:8080/users")
                .retrieve()
                .bodyToFlux(User.class)
                .doOnNext(user -> log.info("Usuario recibido: {}", user.id()));
    }
}
