package com.ai.apps.api;

import com.ai.apps.services.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ImageEndpoints {

	private final ImageService imageService;

	public ImageEndpoints(ImageService imageService) {
		this.imageService = imageService;
	}

	@GetMapping("generate-image")
	public void generateImage(HttpServletResponse response, @RequestParam String prompt) throws IOException {
		ImageResponse imageResponse = imageService.generateImage(prompt);
		String imageUrl = imageResponse.getResult().getOutput().getUrl();
		response.sendRedirect(imageUrl);
	}

	@GetMapping("generate-image-options")
	public List<String> generateImageOptions(HttpServletResponse response, @RequestParam String prompt) throws IOException {
		ImageResponse imageResponse = imageService.generateImageWithOptions(prompt);

		return imageResponse.getResults().stream()
			.map(result -> result.getOutput().getUrl())
			.toList();
	}
}
