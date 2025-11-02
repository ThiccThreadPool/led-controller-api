package com.led.app.model;

public class LedConfigRequest
{
	private int brightness;
	private String color;
	private String mode;
	private String speed;

	public int getBrightness() { return brightness; }
	public void setBrightness(int brightness) { this.brightness = brightness; }

	public String getColor() { return color; }
	public void setColor(String color) { this.color = color; }

	public String getMode() { return mode; }
	public void setMode(String mode) { this.mode = mode; }
	public String getSpeed() { return speed; }
}
