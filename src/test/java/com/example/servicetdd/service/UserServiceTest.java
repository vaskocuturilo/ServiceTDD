package com.example.servicetdd.service;

import com.example.servicetdd.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsersHandle_whenGetUsers_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].username", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].password", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @Disabled
    void getUsersHandle_whenGetUserById_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users", "1L")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].username", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].password", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].id", equalTo(1L)))
                .andExpect(jsonPath("$[*].username", equalTo("test1")))
                .andExpect(jsonPath("$[*].password", equalTo("test1")));
    }

    @Test
    void getUsersHandle_whenGetUsers_thenStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsersHandle_whenGetUsers_thenStatus405() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenUser_whenPostUser_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(new UserEntity("test1@test.com", "test1")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.password").exists())

                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.username").isNotEmpty())
                .andExpect(jsonPath("$.password").isNotEmpty())

                .andExpect(jsonPath("$[*]", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.username", equalTo("test1@test.com")))
                .andExpect(jsonPath("$.password", equalTo("test1")));
    }

    @Test
    void givenUser_whenPostUserWithInCorrectEmail_thenStatus400() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(new UserEntity("test1", "test1")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("Email is not valid"));
    }

    @Test
    void givenUser_whenPostUserWithDataNull_thenStatus400() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(new UserEntity(null, null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("User data should not be null"));
    }

    @Test
    void givenUser_whenPostUserWithDataEmpty_thenStatus400() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(new UserEntity("", "")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("User data should not be empty"));
    }

    @Test
    void givenUser_whenPostUserWithBlankData_thenStatus400() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(new UserEntity()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertTrue(content.contains("User data should not be blank"));
    }

    @Test
     void givenUser_whenDeleteUser_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("A user was delete"));
    }

    @Test
    void givenUser_whenUpdateUser_thenStatus400() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserEntity user = new UserEntity();
        user.setUsername("");
        user.setPassword("");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("User with id  = 10 not found"));
    }

    @Test
    void givenUser_whenUpdateUser_thenStatus200() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        UserEntity user = new UserEntity();
        user.setUsername("afterupdate@qa.team");
        user.setPassword("afterupdate@qa.team");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", equalTo("afterupdate@qa.team")))
                .andExpect(jsonPath("$.password", equalTo("afterupdate@qa.team")));
    }
}