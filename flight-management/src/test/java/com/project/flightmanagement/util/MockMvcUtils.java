package com.project.flightmanagement.util;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MockMvcUtils {
    public static MockMvc buildMockMvc(Object controller) {
        return MockMvcBuilders.standaloneSetup(controller).build();
    }
}
