package com.example.config;

import io.micrometer.observation.ObservationRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;

@Configuration
public class ObservabilityConfig {

    @Autowired
    private ObservationRegistry observationRegistry;

    @PostConstruct
    public void init() {
        // Habilita propagación automática de contexto en Reactor
        Hooks.enableAutomaticContextPropagation();
    }
}