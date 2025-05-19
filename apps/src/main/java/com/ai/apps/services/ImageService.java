package com.ai.apps.services;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

	public static final String GPT_4_O = "gpt-4o";
	public static final String HD = "hd";
	public static final int IMAGE_COUNT = 3;
	private final OpenAiImageModel openAiImageModel;


	public ImageService(OpenAiImageModel openAiImageModel) {
		this.openAiImageModel = openAiImageModel;

	}

	public ImageResponse generateImage(String prompt) {
		return openAiImageModel.call(
			new ImagePrompt(
				prompt));
	}

	public ImageResponse generateImageWithOptions(String prompt) {
		return openAiImageModel.call(
			new ImagePrompt(
				prompt,
				OpenAiImageOptions.builder()
					.model(GPT_4_O)
					.quality(HD)
					.N(IMAGE_COUNT)
					.height(512)
					.width(512)
					.build()
			)
		);
	}
}
