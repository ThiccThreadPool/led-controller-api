package com.led.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/led")
public class LedController
{
	@GetMapping("/getState")
	public String getLedState()
	{
		return "index";
	}

	@PutMapping("changeLedConfig")
	public String changeLedConfig()
	{
		return "LED config changed";
	}

}
