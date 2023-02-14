package com.management.usermanagement.controller.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/fixtures/db_before_test.sql")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/fixtures/db_after_test.sql")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static String expectedJson;

    @BeforeAll
    static void setup() {
        expectedJson = """
                {
                "email": "jose.renato@email.com",
                "firstName": "Jose",
                "lastName": "Renato",
                "role": "ADMINISTRATOR"
                }
                """;
    }

    @Test
    public void shouldSaveUser() throws Exception {
        URI uri = new URI("/user");
        String jsonToSend = """
                {
                "email": "joao.silva@email.com",
                "firstName": "Joao",
                "lastName": "Silva",
                "role": "MONITOR",
                "password": "aaaaaaaA1$"
                }""";

        expectedJson = """
                {
                "email": "joao.silva@email.com",
                "firstName": "Joao",
                "lastName": "Silva",
                "role": "MONITOR"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post(uri).content(jsonToSend).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnUserByEmail() throws Exception {
        URI uri = new URI("/user/jose.renato@email.com");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        URI uri = new URI("/user");

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().json("[" + expectedJson + "]"));
    }

    @Test
    public void shouldEditUserByEmail() throws Exception {
        URI uri = new URI("/user");
        String jsonToSend = """
                {
                "email": "jose.renato@email.com",
                "firstName": "Jose",
                "lastName": "Renato",
                "role": "MONITOR"
                }""";

        expectedJson = jsonToSend;

        mockMvc.perform(MockMvcRequestBuilders.put(uri).content(jsonToSend).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldDeleteUserByEmail() throws Exception {
        URI uri = new URI("/user/jose.renato@email.com");

        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }
}
