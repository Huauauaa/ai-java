package com.huauauaa.server.pi;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.pi-stream.chunk-delay-ms=0")
class PiStreamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void streamPiReturnsPlainTextStartingWithThreeDotOneFour() throws Exception {
        mockMvc.perform(get("/api/pi/stream"))
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("3.14159")));
    }
}
