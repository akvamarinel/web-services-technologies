package org.example;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/lab1")
public class Main extends ResourceConfig {
    public Main() {

        packages("org.example.service");
        register(JacksonFeature.class);
    }
}
