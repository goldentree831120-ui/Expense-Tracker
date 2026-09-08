package com.expensetracker.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-end smoke test: boots the full Spring context against the in-memory
 * H2 database (see application-h2.properties) and exercises the REST API,
 * so this doesn't require MySQL to be installed to run `mvn test`.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void createAndFetchExpense() throws Exception {
        Map<String, Object> payload = Map.of(
                "description", "Groceries",
                "amount", 42.50,
                "category", "FOOD",
                "expenseDate", "2026-08-01"
        );

        mockMvc.perform(post("/api/expenses")
                        .contentType("application/json")
                        .content(jsonMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Groceries"))
                .andExpect(jsonPath("$.category").value("FOOD"));

        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Groceries"));

        mockMvc.perform(get("/api/expenses/summary/by-category"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/expenses/summary/by-month"))
                .andExpect(status().isOk());
    }

    @Test
    void rejectsInvalidExpense() throws Exception {
        Map<String, Object> payload = Map.of(
                "description", "",
                "amount", -5,
                "category", "FOOD",
                "expenseDate", "2026-08-01"
        );

        mockMvc.perform(post("/api/expenses")
                        .contentType("application/json")
                        .content(jsonMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }
}
