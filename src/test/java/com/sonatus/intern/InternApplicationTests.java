package com.sonatus.intern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InternApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	@Test
	public void testIngestLog() throws Exception {
		String log = """
            {
              "service_name": "auth-service",
              "timestamp": "2025-03-17T10:00:00Z",
              "message": "User login successful"
            }
        """;
		mockMvc.perform(post("/logs").contentType(MediaType.APPLICATION_JSON).content(log)).andExpect(status().isOk()).andExpect(content().string("Done"));
	}

	@Test
	public void testPostAndGet() throws Exception {
		String log1_wrong = """
            {
              "service_name": "auth-service",
              "timestamp": "2025-03-17T12:00:00Z",
              "message": "User login successful"
            }
        """;

		String log2_correct = """
            {
              "service_name": "auth-service",
              "timestamp": "2025-03-17T10:05:00Z",
              "message": "User attempted login"
            }
        """; //should pass filtering

		String log3_wrong = """
            {
              "service_name": "logout",
              "timestamp": "2025-03-17T10:05:00Z",
              "message": "User attempted logout"
            }
        """;

		String log4_correct = """
            {
              "service_name": "auth-service",
              "timestamp": "2025-03-17T10:15:00Z",
              "message": "User login successful"
            }
        """; //should pass filtering
		
		//Ingest Logs
		mockMvc.perform(post("/logs").contentType(MediaType.APPLICATION_JSON).content(log1_wrong));
		mockMvc.perform(post("/logs").contentType(MediaType.APPLICATION_JSON).content(log2_correct));
		mockMvc.perform(post("/logs").contentType(MediaType.APPLICATION_JSON).content(log3_wrong));
		mockMvc.perform(post("/logs").contentType(MediaType.APPLICATION_JSON).content(log4_correct));

		//checks that the GET request returns 2 logs and writes the resulting list to `result`
		String result = mockMvc.perform(get("/logs").param("service", "auth-service").param("start", "2025-03-17T10:00:00Z").param("end", "2025-03-17T10:30:00Z"))
									.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.length()").value(2)).andReturn().getResponse().getContentAsString();
		
		System.out.println(result);
	}

}
