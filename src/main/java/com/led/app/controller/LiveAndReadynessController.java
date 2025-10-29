package com.led.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class LiveAndReadynessController
{
	@GetMapping("/liveness")
	public ResponseEntity<String> live()
	{
		return ResponseEntity.ok("OK");
	}

	@GetMapping("/readiness")
	public ResponseEntity<String> ready()
	{
		return ResponseEntity.ok("OK");
	}
}
