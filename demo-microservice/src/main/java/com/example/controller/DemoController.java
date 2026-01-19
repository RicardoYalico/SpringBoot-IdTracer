package com.example.controller;

import com.example.dbo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/users")
public class DemoController {

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return Mono.just(
                new User(id, "Juan Pérez")
        );
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        log.info("Procesando request");
        return Flux.just(
                new User("1", "Juan"),
                new User("2", "María"),
                new User("3", "Carlos")
        );
    }
}
