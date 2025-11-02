package com.led.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.led.app.model.LedConfigRequest;
import com.led.app.service.LedService;

@RestController
@RequestMapping("/led")
public class LedController
{
	private final LedService ledService;

	public LedController(final LedService ledService)
	{
		this.ledService = ledService;
	}

	@GetMapping("/getState")
	public String getLedState()
	{
		return "index";
	}

	@PutMapping("changeLedConfig")
	public String changeLedConfig(@RequestBody LedConfigRequest ledConfigRequest)
	{
		ledService.changeLedConfig(ledConfigRequest);
		return "LED config changed";
	}

}
