package com.ai.apps.api;

import com.ai.apps.services.TranscriptionService;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/transcribe")
public class TranscriptionEndpoints {

	private final TranscriptionService transcriptionService;

	public TranscriptionEndpoints(TranscriptionService transcription) {
		this.transcriptionService = transcription;
	}

	@PostMapping
	public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
		}
		AudioTranscriptionResponse response = transcriptionService.transcribeAudio(file);
		return new ResponseEntity<>(response.getResult().getOutput(), HttpStatus.OK);
	}
}
