package com.board.api.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"file:web/WEB-INF/dispatcher-servlet.xml", "file:web/WEB-INF/securityContext.xml"})
@WebAppConfiguration
@RequiredArgsConstructor
class MemberControllerTest {

    private static final Logger log = LogManager.getLogger();

    private final WebApplicationContext ctx;

    @InjectMocks
    @Autowired
    private UserAPIController controller;

    @Mock
    private UserDetailsService service;

    private MockMvc mockMvc;

//    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(controller).apply(springSecurity()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).apply(springSecurity()).build();

//        userDetails = service.loadUserByUsername("admin");

        assertNotNull(controller);
//        assertNotNull(service);
    }

    @Test
    void info() {
    }

    @Test
    void listAuthAdmin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/member/list")
                        .with(user("admin").password("4444").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void listAuthNormalUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/member/list")
                        .with(user("user0").password("user0").roles("USER")))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andReturn();
    }

    @Test
    void createUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/member/create")
                        .param("userId", "new")
                        .param("password", "1234")
                        .param("userName", "new")
                        .param("auth", "ROLE_USER"))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andReturn();
    }

    @Test
    void adminDeleteMember() {
        
    }

    @Test
    void userIdDuplicatesChecking() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/check/userid/duplicates")
                        .param("userId", "admin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }
}