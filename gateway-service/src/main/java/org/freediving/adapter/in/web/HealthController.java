package org.freediving.adapter.in.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/health")
	public ResponseEntity<String> healthCheck() {

		String dummy = "{\n"
			+ "  \"id\": 12447,\n"
			+ "  \"name\": \"Test User\",\n"
			+ "  \"isActive\": true,\n"
			+ "  \"registeredOn\": \"2024-01-15T12:00:00\",\n"
			+ "  \"role\": \"Administrator\",\n"
			+ "  \"contact\": {\n"
			+ "    \"email\": \"testuser@example.com\",\n"
			+ "    \"phone\": \"+1234567890\"\n"
			+ "  },\n"
			+ "  \"preferences\": {\n"
			+ "    \"language\": \"English\",\n"
			+ "    \"notifications\": [\"email\", \"SMS\"],\n"
			+ "    \"theme\": \"dark\"\n"
			+ "  },\n"
			+ "  \"lastLogin\": {\n"
			+ "    \"date\": \"2024-01-14T20:30:25\",\n"
			+ "    \"ipAddress\": \"192.168.1.10\"\n"
			+ "  },\n"
			+ "  \"scores\": [85, 92, 78, 88],\n"
			+ "  \"projects\": [\n"
			+ "    {\n"
			+ "      \"projectId\": 101,\n"
			+ "      \"title\": \"Project Alpha\",\n"
			+ "      \"status\": \"Completed\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"projectId\": 102,\n"
			+ "      \"title\": \"Project Beta\",\n"
			+ "      \"status\": \"In Progress\"\n"
			+ "    }\n"
			+ "  ],\n"
			+ "  \"additionalInfo\": null\n"
			+ "}\n";

		return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(dummy);
	}
}
