package com.ai.apps.api;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.model.ApiKey;
import org.springframework.ai.model.SimpleApiKey;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;

@RestController
@RequestMapping("/transcribe")
public class TranscriptionEndpoints {

	private final OpenAiAudioTranscriptionModel transcriptionModel;
	private final String baseUrl;

	public TranscriptionEndpoints(@Value("${spring.ai.openai.api-key}") String apiKey) {
		this.baseUrl = "";
		this.transcriptionModel = new OpenAiAudioTranscriptionModel(
			new OpenAiAudioApi(
				baseUrl,
				getApiKey(apiKey),
				null,
				getRestClientBuilder(),
				getWebClientBuilder(),
				null
			)
		);
	}


	@PostMapping
	public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile file) throws Exception {


		File tempFile = File.createTempFile("audio", ".wav");
		file.transferTo(tempFile);

		OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
			.responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
			.temperature(0f)
			.language("en")
			.build();

		var audioFile = new FileSystemResource("path/to/audio/file.wav");

		AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, transcriptionOptions);
		AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);
		tempFile.deleteOnExit();

		return new ResponseEntity<>(response.getResult().getOutput(), HttpStatus.OK);
	}

	private ApiKey getApiKey(String apiKey) {
		return new SimpleApiKey(apiKey);
	}

	private RestClient.Builder getRestClientBuilder() {
		return RestClient.builder();
	}

	private WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}

}
