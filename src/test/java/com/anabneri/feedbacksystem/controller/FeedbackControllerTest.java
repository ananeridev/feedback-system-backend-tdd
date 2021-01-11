package com.anabneri.feedbacksystem.controller;

import com.anabneri.feedbacksystem.model.EmployeeEntry;
import com.anabneri.feedbacksystem.model.FeedbackEmployee;
import com.anabneri.feedbacksystem.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackControllerTest {

    @MockBean
    private FeedbackService service;

    @Autowired
    private MockMvc mockMvc;

    // create the DateFormat to set a Date and Hour that I can use to compare SpringMvc returned the dates to expected values
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @BeforeAll
    static void beforeAll() {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Test
    @DisplayName("GET /feedback/feedbackId - THE FEEDBACK WAS FOUND")
    void testFeedbackByIdFound() throws Exception {

        FeedbackEmployee mockEmployee = new FeedbackEmployee("feedbackId","user_test",1);
        Date now = new Date();
        mockEmployee.getEntries().add(new EmployeeEntry("user_test",now,
                "This is a feedback :)"));
        doReturn(Optional.of(mockEmployee)).when(service).findById("feedbackId");

        mockMvc.perform(get("/feedback/{id}","feedbackId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION,"/feedback/feedbackId"))

                // validating the returned files
                .andExpect(jsonPath("$.id",is("feedbackId")))
                .andExpect(jsonPath("$.employeeName",is("user_test")))
                .andExpect(jsonPath("$.entries.length()",is(1)))
                .andExpect(jsonPath("$.entries[0].username", is("user_test")))
                .andExpect(jsonPath("$.entries[0].feedback", is("This is a feedback :)")))
                .andExpect(jsonPath("$.entries[0].date", is(df.format(now))));

    }

    @Test
    @DisplayName("GET /feedback/feedbackId - THE FEEDBACK WAS NOT FOUND")
    void testFeedbackByIdNotFound() throws Exception {

        doReturn(Optional.empty()).when(service).findById("feedbackId");
        mockMvc.perform(get("/feedback/{id}","feedbackId"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /feedback - SUCCESS")
    void testCreateFeedback() throws Exception {

        Date now = new Date();
        FeedbackEmployee postEmployee = new FeedbackEmployee(1);
        postEmployee.getEntries().add( new EmployeeEntry("user_test",now,"This is a feedback :)"));

        FeedbackEmployee mockEmployee = new FeedbackEmployee("1","user_test",1);
        mockEmployee.getEntries().add(new EmployeeEntry("user_test",now,
                "This is a feedback :)"));

        doReturn(Optional.of(mockEmployee)).when(service).save(any());

        mockMvc.perform(get("/feedback/{id}","feedbackId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION,"/feedback/feedbackId"))

                .andExpect(jsonPath("$.id",is("feedbackId")))
                .andExpect(jsonPath("$.employeeName",is("user_test")))
                .andExpect(jsonPath("$.entries.length()",is(1)))
                .andExpect(jsonPath("$.entries[0].username", is("user_test")))
                .andExpect(jsonPath("$.entries[0].feedback", is("This is a feedback :)")))
                .andExpect(jsonPath("$.entries[0].date", is(df.format(now))));
    }

    @Test
    @DisplayName("POST /feedback/{feedbackId}/entry")
    void testAddEntryToFeedback() throws Exception {

        Date now = new Date();
        EmployeeEntry employeeEntry = new EmployeeEntry("user_test",now,"This is a feedback :");
        FeedbackEmployee mockEmployee = new FeedbackEmployee("1","user_test",1);
        FeedbackEmployee returnedEmployee = new FeedbackEmployee("1","user_test",2);
        returnedEmployee.getEntries().add(employeeEntry);

        doReturn(Optional.of(mockEmployee)).when(spy(service).findByFeedbackId(1));
       // doReturn(Optional.of(mockEmployee)).when(service).findByFeedbackId(1);

        doReturn(Optional.of(returnedEmployee)).when(service).save(any());

        mockMvc.perform(post("/feedback/{feedbackId}/entry",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeeEntry)))

                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ETAG, "\"2\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/feedback/1"))


                .andExpect(jsonPath("$.id",is("feedbackId")))
                .andExpect(jsonPath("$.employeeName",is("user_test")))
                .andExpect(jsonPath("$.entries.length()",is(1)))
                .andExpect(jsonPath("$.entries[0].username", is("user_test")))
                .andExpect(jsonPath("$.entries[0].feedback", is("This is a feedback :)")))
                .andExpect(jsonPath("$.entries[0].date", is(df.format(now))));

    }



    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
