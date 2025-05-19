package com.ai.apps.api;

import com.ai.apps.services.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeEndpoints {

	private final RecipeService recipeService;

	public RecipeEndpoints(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("recipe-creator")
	public String receipeCreator(@RequestParam String ingredients,
	                             @RequestParam(defaultValue = "any") String cuisine,
	                             @RequestParam(defaultValue = "") String dietaryRestrictions) {
		return recipeService.createRecipe(ingredients, cuisine, dietaryRestrictions);
	}

}
