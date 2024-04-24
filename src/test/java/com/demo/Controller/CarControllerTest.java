package com.demo.Controller;

import com.code_intelligence.jazzer.junit.FuzzTest;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;
import com.code_intelligence.jazzer.mutation.annotation.UrlSegment;
import com.code_intelligence.jazzer.mutation.annotation.WithSize;
import com.demo.dto.CarDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static com.code_intelligence.jazzer.junit.SpringFuzzTestHelper.statusIsNot5xxServerError;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Fuzz test function that checks the {@link CarController} endpoints.
     * <p/>
     * Execute test with <code>cifuzz run com.demo.Controller.CarControllerTest::fuzzTestCarEndpoints</code> or
     * <code>cifuzz container run com.demo.Controller.CarControllerTest::fuzzTestCarEndpoints</code>.
     * Finds a state dependent issue in form of an uncaught CarIdGenerationException exception.
     * <p/>
     * @throws Exception uncaught exceptions for the fuzzer to detect issues.
     */
    @FuzzTest
    public void fuzzTestCarEndpoints(@NotNull @WithSize(min = 5, max = 15) List< @NotNull Integer> functionOrder,
                                     @NotNull @WithSize(min = 5, max = 15) List< @UrlSegment String> ids,
                                     @NotNull @WithSize(min = 5, max = 15) List< @NotNull CarDTO> dtos,
                                     @NotNull @WithSize(min = 5, max = 15) List< @NotNull Boolean> reuseReturnedIds) throws Exception {
        ObjectMapper om = new ObjectMapper();
        String nextId = ids.removeLast();
        String lastReturnedId = null;

        // Call the endpoints in a loop
        while (!functionOrder.isEmpty()) {
            try {
                // let the fuzzer decide the call order
                Integer last = functionOrder.removeLast();
                if (last == 0) {
                    mockMvc.perform(get("/car"))
                            .andExpect(statusIsNot5xxServerError());
                } else if (last == 1) {
                    mockMvc.perform(get("/car/{id}", nextId))
                            .andExpect(statusIsNot5xxServerError());
                } else if (last == 2) {
                    lastReturnedId = mockMvc.perform(delete("/car/{id}", nextId))
                            .andExpect(statusIsNot5xxServerError())
                            .andReturn().getResponse().getContentAsString();
                } else if (last == 3) {
                    lastReturnedId = mockMvc.perform(put("/car/{id}", nextId)
                                    .content(om.writeValueAsString(dtos.removeLast()))
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(statusIsNot5xxServerError())
                            .andReturn().getResponse().getContentAsString();
                } else {
                    lastReturnedId = mockMvc.perform(post("/car")
                                    .content(om.writeValueAsString(dtos.removeLast()))
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(statusIsNot5xxServerError())
                            .andReturn().getResponse().getContentAsString();
                }
                if (lastReturnedId != null) {
                    if (reuseReturnedIds.removeLast()) {
                        nextId = lastReturnedId;
                        lastReturnedId = null;
                    } else {
                        nextId = ids.removeLast();
                    }
                } else {
                    nextId = ids.removeLast();
                }
            } catch (NoSuchElementException ignored){
                // catching and suppressing if a list does not contain enough values to do the functions calls
                break;
            }
        }
    }

    @Test
    public void unitTestCarEndpoints() throws Exception {
        ObjectMapper om = new ObjectMapper();
        CarDTO dto = new CarDTO();
        mockMvc.perform(put("/car/{id}", "0")
                        .content(om.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(statusIsNot5xxServerError());
        mockMvc.perform(post("/car")
                        .content(om.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(statusIsNot5xxServerError());
    }
}
