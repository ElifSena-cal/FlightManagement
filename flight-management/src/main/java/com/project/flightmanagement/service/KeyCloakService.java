package com.project.flightmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.flightmanagement.config.KeycloakConfig;
import com.project.flightmanagement.exception.KeycloakServiceException;
import com.project.flightmanagement.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class KeyCloakService {

    public final static String realm = "FlightManagementKeycloakk";


    public void addUser(UserRequest userDTO) {
        if (userExists(userDTO.getUserName())) {
            throw new DataIntegrityViolationException("User with the given username already exists.");
        }
        UserRepresentation user = getUserRepresentation(userDTO);
        UsersResource usersResource=getUsersResource();
        Response response=usersResource.create(user);
        if (!Objects.equals(201,response.getStatus())){
            throw new KeycloakServiceException("Error interacting with Keycloak");
        }
    }

    @NotNull
    private static UserRepresentation getUserRepresentation(UserRequest userDTO) {
        UserRepresentation user=new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getName());

        user.setLastName(userDTO.getSurname());
        user.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(userDTO.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list=new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);
        return user;
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = KeycloakConfig.getInstance().realm(realm);
        return realm1.users();
    }
    private boolean userExists(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> existingUsers = usersResource.search(username);
        return !existingUsers.isEmpty();
    }
    public String getToken(String clientId, String username, String password) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8180/realms/FlightManagementKeycloakk/protocol/openid-connect/token", request, String.class);
        String responseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }
    public void getUpdatedUserRepresentation(UserRequest updatedUserDTO) {
            UsersResource usersResource = getUsersResource();

            List<UserRepresentation> existingUsers = usersResource.search(updatedUserDTO.getUserName());

            if (existingUsers.isEmpty()) {
                throw new RuntimeException("User not found.");
            }
            UserRepresentation existingUser = existingUsers.get(0);

            UserRepresentation updatedUser = new UserRepresentation();
            updatedUser.setEnabled(existingUser.isEnabled());
            updatedUser.setUsername(existingUser.getUsername());
            updatedUser.setFirstName(updatedUserDTO.getName());  // Update first name
            updatedUser.setLastName(updatedUserDTO.getSurname());  // Update last name
            usersResource.get(existingUser.getId()).update(updatedUser);
    }
}

