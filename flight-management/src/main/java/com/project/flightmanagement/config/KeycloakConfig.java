package com.project.flightmanagement.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    static Keycloak keycloak = null;
    final static String serverUrl = "http://localhost:8180/";
    public final static String realm = "FlightManagementKeycloakk";
    final static String clientId = "admin-cli";

    @Bean
    public static Keycloak getInstance(){
        if(keycloak == null){
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .clientId(clientId)
                    .clientSecret("PTPT0zkukbz3D8RtGbOWYZfy2M31JJgA")
                    .build();
        }
        return keycloak;
    }

}