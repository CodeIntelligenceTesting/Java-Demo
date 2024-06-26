package com.demo.controller;

import com.code_intelligence.jazzer.junit.FuzzTest;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;
import com.code_intelligence.jazzer.mutation.annotation.UrlSegment;
import com.demo.dto.UserDTO;
import com.demo.helper.MockLdapContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.code_intelligence.jazzer.junit.SpringFuzzTestHelper.statusIsNot5xxServerError;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Fuzz test function that checks the {@link UserController#updateOrCreateUser(String, String, UserDTO)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.controller.UserControllerTest::fuzzTestUpdateOrCreateUser</code> or
     * <code>cifuzz container run com.demo.controller.UserControllerTest::fuzzTestUpdateOrCreateUser</code>.
     * Finds a security issue in form of an SQL Injection vulnerability.
     * <p/>
     * @param id parameter filled in by the fuzzer.
     * @param role parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestUpdateOrCreateUser(@UrlSegment String id,
                                           @NotNull String role,
                                           @NotNull UserDTO userDTO) throws Exception {
        ObjectMapper om = new ObjectMapper();
        mockMvc.perform(put("/user/{id}", id)
                        .param("role", role)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Example unit Test for user-controller put endpoint.
     * @throws Exception uncaught exception
     */
    @Test
    public void unitTestUpdateOrCreateUser() throws Exception {
        ObjectMapper om = new ObjectMapper();
        mockMvc.perform(put("/user/{id}", "id")
                        .param("role", "DEFAULT_ROLE")
                        .content(om.writeValueAsString(new UserDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    /**
     * Fuzz test function that checks the {@link UserController#getUsers(String)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.controller.UserControllerTest::fuzzTestGetUsers</code> or
     * <code>cifuzz container run com.demo.controller.UserControllerTest::fuzzTestGetUsers</code>.
     * Code contains currently no issues and testing will stop after the timeout specified in the cifuzz.yaml (Default 30m)
     * <p/>
     * @param role parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestGetUsers(@NotNull String role) throws Exception {
        mockMvc.perform(get("/user")
                        .param("role", role))
                .andExpect(statusIsNot5xxServerError());
    }
    /**
     * Unit test variant of {@link UserControllerTest#fuzzTestGetUsers(String)}
     * @throws Exception uncaught exceptions to signal failing test
     */
    @Test
    public void unitTestGetUsers() throws Exception {
        mockMvc.perform(get("/user")
                        .param("role", "DEFAULT"))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Fuzz test function that checks the {@link UserController#getUser(String, String)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.controller.UserControllerTest::fuzzTestGetUser</code> or
     * <code>cifuzz container run com.demo.controller.UserControllerTest::fuzzTestGetUser</code>.
     * Finds a security issue in form of a Remote-Code-Execution (RCE) vulnerability.
     * <p/>
     * @param id parameter filled in by the fuzzer.
     * @param role parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestGetUser(@UrlSegment String id, @NotNull String role) throws Exception {
        mockMvc.perform(get("/user/{id}", id)
                        .param("role", role))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Fuzz test function that checks the {@link UserController#deleteUser(String, String)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.controller.UserControllerTest::fuzzTestDeleteUser</code> or
     * <code>cifuzz container run com.demo.controller.UserControllerTest::fuzzTestDeleteUser</code>.
     * Code contains currently no issues and testing will stop after the timeout specified in the cifuzz.yaml (Default 30m)
     * <p/>
     * @param id parameter filled in by the fuzzer.
     * @param role parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestDeleteUser(@UrlSegment String id,
                                   @NotNull String role) throws Exception {
        mockMvc.perform(delete("/user/{id}", id)
                        .param("role", role))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Fuzz test function that checks the {@link UserController#createUser(String, UserDTO)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.controller.UserControllerTest::fuzzTestCreateUser</code> or
     * <code>cifuzz container run com.demo.controller.UserControllerTest::fuzzTestCreateUser</code>.
     * Finds a security issue in form of an LDAP Injection vulnerability.
     * <p/>
     * @param role parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestCreateUser(@NotNull String role, @NotNull UserDTO userDTO) throws Exception {
        UserController.setLdapContext(new MockLdapContext());
        ObjectMapper om = new ObjectMapper();
        mockMvc.perform(post("/user")
                        .param("role", role)
                        .content(om.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(statusIsNot5xxServerError());
    }
}
