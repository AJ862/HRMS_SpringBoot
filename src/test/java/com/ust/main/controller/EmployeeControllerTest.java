package com.ust.main.controller;

import com.ust.main.model.Employee;
import com.ust.main.response.EmployeeResponse;
import com.ust.main.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    EmployeeController employeeController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void getAllEmployees() throws Exception {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        Employee employee = new Employee();
        employeeResponse.setEmployeeResponseList(Collections.singletonList(employee));
        ResponseEntity<EmployeeResponse> employeeResponseResponseEntity = ResponseEntity.ok(employeeResponse);
        //add setters
        when(employeeService.getAllEmployees()).thenReturn(employeeResponseResponseEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/getallemployees").header("key","value")).
                andExpect(status().isOk());
                //.andExpect(content().json());
    }
}