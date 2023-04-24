package com.example.servicetdd.service;

import com.example.servicetdd.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].username", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].password", hasSize(greaterThanOrEqualTo(1))));
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
                        .content(new ObjectMapper().writeValueAsString(new UserEntity("test1", "test1")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists())

                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").isNotEmpty())

                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", equalTo("test1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", equalTo("test1")));
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
}