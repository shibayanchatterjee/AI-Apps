package com.ai.apps.services;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.model.ApiKey;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;

@Service
public class TranscriptionService {

	private final OpenAiAudioTranscriptionModel transcriptionModel;

	private String baseUrl;

	public TranscriptionService() {
		this.baseUrl = "";
		this.transcriptionModel = new OpenAiAudioTranscriptionModel(
			new OpenAiAudioApi(
				baseUrl,
				getApiKey(),
				null,
				getRestClientBuilder(),
				getWebClientBuilder(),
				null
			)
		);
	}

	public AudioTranscriptionResponse transcribeAudio(MultipartFile file) throws Exception {

		File tempFile = File.createTempFile("audio", ".wav");
		file.transferTo(tempFile);

		OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
			.responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
			.temperature(0f)
			.language("en")
			.build();

		FileSystemResource audioFile = new FileSystemResource(tempFile);

		AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
		AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);
		tempFile.deleteOnExit();
		return response;
	}

	private ApiKey getApiKey() {
		String envApiKey = System.getenv("${spring.ai.openai.api-key}");
		return new SimpleApiKey(envApiKey);
	}

	private RestClient.Builder getRestClientBuilder() {
		return RestClient.builder();
	}

	private WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

}
