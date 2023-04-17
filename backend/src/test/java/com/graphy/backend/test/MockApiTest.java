package com.graphy.backend.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.graphy.backend.BackendApplication;
import com.graphy.backend.test.config.TestProfile;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplication.class)
@ActiveProfiles(TestProfile.TEST)
@Ignore
public class MockApiTest {

    protected MockMvc mvc;
    protected ObjectMapper objectMapper = buildObjectMapper();

    public MockMvc buildMockMvc(WebApplicationContext context) {
        return MockMvcBuilders.webAppContextSetup(context).build();
    }

    private ObjectMapper buildObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
