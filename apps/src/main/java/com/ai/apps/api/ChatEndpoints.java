package com.ai.apps.api;

import com.ai.apps.services.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatEndpoints {

	private final ChatService chatService;

	public ChatEndpoints(ChatService chatService) {
		this.chatService = chatService;
	}

	@GetMapping("ask-ai")
	public String getResponse(@RequestParam String prompt) {
		return chatService.getResponse(prompt);
	}

	@GetMapping("ask-ai-options")
	public String getResponseOptions(@RequestParam String prompt) {
		return chatService.getResponseOptions(prompt);
	}

}
