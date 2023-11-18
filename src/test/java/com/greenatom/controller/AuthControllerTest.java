package com.greenatom.controller;

import com.greenatom.domain.dto.employee.CreateEmployeeRequestDTO;
import com.greenatom.domain.dto.security.AuthDTO;
import com.greenatom.domain.dto.security.RoleDTO;
import com.greenatom.domain.enums.JobPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    //Пока настраиваю (Важнов Павел)
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void testRegistration() throws Exception {
//        CreateEmployeeRequestDTO requestDTO = new CreateEmployeeRequestDTO();
//        requestDTO.setFirstname("test");
//        requestDTO.setSurname("test");
//        requestDTO.setPatronymic("test");
//        requestDTO.setJobPosition(JobPosition.MANAGER);
//        requestDTO.setSalary(100000L);
//        requestDTO.setEmail("test@mail.ru");
//        requestDTO.setPhoneNumber("test");
//        requestDTO.setPassword("test");
//
//        RoleDTO roleDTO = new RoleDTO();
//        roleDTO.setName("ROLE_MANAGER");
//        requestDTO.setRole(roleDTO);
//        requestDTO.setAddress("test");
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/auth/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(requestDTO)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty());
//    }


    @Test
    public void testLogin() throws Exception {

        AuthDTO authDto = new AuthDTO();
        authDto.setUsername("Дмитрий_П_Ю");
        authDto.setPassword("Qwer123as");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(authDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty()); // Проверка, что в ответе есть токен
    }




    // Вспомогательный метод для преобразования объекта в JSON строку
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
