package com.led.app.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService
{
	private final MqttClient mqttClient;

	public MqttPublisherService(final MqttClient mqttClient)
	{
		this.mqttClient = mqttClient;
	}

	public void publish(String topic, String payload) throws Exception
	{
		try
		{
			MqttMessage message = new MqttMessage(payload.getBytes());
			message.setQos(1);
			mqttClient.publish(topic, message);
			System.out.println("Published message: " + payload + " to topic: " + topic);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

