package com.led.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.led.app.model.LedConfigRequest;

@Service
public class LedService
{
	@Autowired
	private MqttPublisherService mqttPublisherService;

	//mosquitto_pub -h 192.168.1.220 -t command -m '{"cmd":"effect","mode":"rainbow","speed":20}'
	//mosquitto_pub -h 192.168.1.220 -t command -m '{"cmd":"solid","color":"#00FF00"}'
	//mosquitto_pub -h 192.168.1.220 -t command -m '{"cmd":"off"}'
	public void changeLedConfig(LedConfigRequest ledConfigRequest)
	{
		try
		{
			String payload = "";
			if (ledConfigRequest.getMode().equals("solid"))
			{
				payload = String.format("{\"cmd\":\"solid\",\"color\":\"%s\"}", ledConfigRequest.getColor());
			}
			else if (ledConfigRequest.getMode().equals("off"))
			{
				payload = "{\"cmd\":\"off\"}";
			}
			else
			{
				payload = String.format("{\"cmd\":\"effect\",\"mode\":\"%s\",\"speed\":%s}", ledConfigRequest.getMode(), ledConfigRequest.getSpeed());
			}
			mqttPublisherService.publish("led/command", payload);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
