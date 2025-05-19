package com.ai.apps.services;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

	private final ChatModel chatModel;

	public RecipeService(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	public String createRecipe(String ingredients,
	                           String cuisine,
	                           String dietaryRestrictions) {
		var template = getTemplate();

		PromptTemplate promptTemplate = new PromptTemplate(template);
		Map<String, Object> params = Map.of(
			"ingredients", ingredients,
			"cuisine", cuisine,
			"dietaryRestrictions", dietaryRestrictions
		);

		Prompt prompt = promptTemplate.create(params);
		return chatModel.call(prompt).getResult().getOutput().getText();

	}

	private String getTemplate() {
		return """
			I want to create a recipe with the following ingredients: {ingredients}.
			The recipe should be in the {cuisine} cuisine and should not include any of the following dietary restrictions: {dietaryRestrictions}.
			Please provide the recipe in a structured format with the following sections:
			1. Title
			2. Ingredients
			3. Instructions
			4. Cooking Time
			5. Serving Size
			6. Nutritional Information
			""";
	}


}
