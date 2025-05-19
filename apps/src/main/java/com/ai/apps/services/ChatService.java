package com.ai.apps.services;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	private static final String MODEL = "gpt-4o";
	private static final double TEMPERATURE = 0.7;
	private static final int MAX_TOKENS = 10;

	private final ChatModel chatModel;

	public ChatService(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	public String getResponse(String prompt) {
		return chatModel.call(prompt);
	}

	public String getResponseOptions(String prompt) {
		ChatResponse response = chatModel.call(
			new Prompt(
				prompt,
				OpenAiChatOptions.builder()
					.model(MODEL)
					.temperature(TEMPERATURE)
					.maxTokens(MAX_TOKENS)
					.build()
			)
		);
		return response.getResult().getOutput().getText();
	}

}
