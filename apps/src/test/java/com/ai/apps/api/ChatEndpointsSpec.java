package com.ai.apps.api;

import com.ai.apps.services.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatEndpoints.class)
public class ChatEndpointsSpec {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ChatService chatService;

	@BeforeEach
	void setUp() {
		Mockito.when(chatService.getResponse("hello")).thenReturn("Hi there!");
		Mockito.when(chatService.getResponseOptions("hello")).thenReturn("Option1, Option2");
	}

	@Test
	void testGetResponse() throws Exception {
		mockMvc.perform(get("/ask-ai").param("prompt", "hello"))
			.andExpect(status().isOk())
			.andExpect(content().string("Hi there!"));
	}

	@Test
	void testGetResponseOptions() throws Exception {
		mockMvc.perform(get("/ask-ai-options").param("prompt", "hello"))
			.andExpect(status().isOk())
			.andExpect(content().string("Option1, Option2"));
	}
}