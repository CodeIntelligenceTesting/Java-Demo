package com.demo.Controller;

import com.code_intelligence.jazzer.junit.FuzzTest;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;
import com.code_intelligence.jazzer.mutation.annotation.UrlSegment;
import com.demo.dto.CarCategoryDTO;
import com.demo.helper.DatabaseMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.code_intelligence.jazzer.junit.SpringFuzzTestHelper.statusIsNot5xxServerError;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class CarCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Fuzz test function that checks the {@link CarCategoryController#getCategories(String)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.Controller.CarCategoryControllerTest::fuzzTestGetCategories</code> or
     * <code>cifuzz container run com.demo.Controller.CarCategoryControllerTest::fuzzTestGetCategories</code>.
     * Finds a robustness issue in form of an uncaught NullPointerException exception.
     * <p/>
     * @param role parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestGetCategories(@NotNull String role) throws Exception {
        mockMvc.perform(get("/category")
                        .param("role", role))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Unit test variant of {@link CarCategoryControllerTest#fuzzTestGetCategories(String)}
     * @throws Exception uncaught exceptions to signal failing test
     */
    @Test
    public void unitTestGetCategories() throws Exception {
        mockMvc.perform(get("/category")
                        .param("role", "DEFAULT"))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Fuzz test function that checks the {@link CarCategoryController#getCategory(String, String)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.Controller.CarCategoryControllerTest::fuzzTestGetCategory</code> or
     * <code>cifuzz container run com.demo.Controller.CarCategoryControllerTest::fuzzTestGetCategory</code>.
     * Finds a robustness issue in form of an uncaught NullPointerException exception.
     * <p/>
     * @param id parameter filled in by the fuzzer.
     * @param role parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestGetCategory(@UrlSegment String id, @NotNull String role) throws Exception {
        mockMvc.perform(get("/category/{id}", id)
                        .param("role", role))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Advanced Fuzz test function that checks the {@link CarCategoryController#deleteCategory(String, String)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.Controller.CarCategoryControllerTest::fuzzTestDeleteCategory</code> or
     * <code>cifuzz container run com.demo.Controller.CarCategoryControllerTest::fuzzTestDeleteCategory</code>.
     * Finds a robustness/timeout issue.
     * <p/>
     * @param id parameter filled in by the fuzzer.
     * @param role parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @Timeout(5)
    @FuzzTest
    public void fuzzTestDeleteCategory(@UrlSegment String id,
                                       @NotNull String role,
                                       long requestTime) throws Exception {
        DatabaseMock.getInstance().init();
        DatabaseMock.setDeleteRequestTime(requestTime);

        mockMvc.perform(delete("/category/{id}", id)
                        .param("role", role))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Fuzz test function that checks the {@link CarCategoryController#updateOrCreateCategory(String, String, CarCategoryDTO)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.Controller.CarCategoryControllerTest::fuzzTestUpdateOrCreateCategory</code> or
     * <code>cifuzz container run com.demo.Controller.CarCategoryControllerTest::fuzzTestUpdateOrCreateCategory</code>.
     * Code contains currently no issues and testing will stop after the timeout specified in the cifuzz.yaml (Default 30m)
     * <p/>
     * @param id parameter filled in by the fuzzer.
     * @param role parameter filled in by the fuzzer.
     * @param categoryDTO parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestUpdateOrCreateCategory(@UrlSegment String id,
                                               @NotNull String role,
                                               @NotNull CarCategoryDTO categoryDTO) throws Exception {
        ObjectMapper om = new ObjectMapper();
        mockMvc.perform(put("/category/{id}", id)
                        .param("role", role)
                        .content(om.writeValueAsString(categoryDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(statusIsNot5xxServerError());
    }

    /**
     * Fuzz test function that checks the {@link CarCategoryController#createCategory(String, CarCategoryDTO)} endpoint.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.Controller.CarCategoryControllerTest::fuzzTestCreateCategory</code> or
     * <code>cifuzz container run com.demo.Controller.CarCategoryControllerTest::fuzzTestCreateCategory</code>.
     * Finds a robustness issue in form of an uncaught DateTimeParseException exception.
     * <p/>
     * @param role parameter filled in by the fuzzer.
     * @param categoryDTO parameter filled in by the fuzzer.
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestCreateCategory(@NotNull String role, @NotNull CarCategoryDTO categoryDTO) throws Exception {
        ObjectMapper om = new ObjectMapper();
        mockMvc.perform(post("/category")
                        .param("role", role)
                        .content(om.writeValueAsString(categoryDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(statusIsNot5xxServerError());
    }
}
