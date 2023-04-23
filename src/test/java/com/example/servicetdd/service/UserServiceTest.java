package com.example.servicetdd.service;

import com.example.servicetdd.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsersHandle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasSize(0)));
    }

    @Test
    void givenNumbers_whenCreatedNumber_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .content(new ObjectMapper().writeValueAsString(new UserEntity(1L, "test1", "test1")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].username").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].password").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].username").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].password").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", hasItem(1L)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].username", hasItem("test1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].password", hasItem("test1")));
    }
}