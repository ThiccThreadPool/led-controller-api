#include <WiFi.h>
#include <PubSubClient.h>
#include <Adafruit_NeoPixel.h>
#include <ArduinoJson.h>

#define LED_PIN 5
#define NUM_LEDS 300
#define BRIGHTNESS 150

const char* ssid = "xxx";
const char* password = "xxx";
const char* mqtt_server = "192.168.1.220";

WiFiClient espClient;
PubSubClient client(espClient);
Adafruit_NeoPixel strip(NUM_LEDS, LED_PIN, NEO_GRB + NEO_KHZ800);

String currentMode = "off";
uint32_t currentColor = 0;
int currentSpeed = 50;

// --- Funkcje efektów ---
void solidColor(uint32_t color) {
  for (int i = 0; i < NUM_LEDS; i++) {
    strip.setPixelColor(i, color);
  }
  strip.show();
}

void rainbow(int wait) {
  static uint16_t j = 0;
  for (int i = 0; i < strip.numPixels(); i++) {
    strip.setPixelColor(i, strip.ColorHSV((i * 256 + j) & 65535));
  }
  strip.show();
  j += wait;
}

// --- MQTT Callback ---
void callback(char* topic, byte* payload, unsigned int length) {
  String msg;
  for (int i = 0; i < length; i++) msg += (char)payload[i];

  Serial.print("MQTT message: ");
  Serial.println(msg);

  StaticJsonDocument<256> doc;
  DeserializationError error = deserializeJson(doc, msg);
  if (error) {
    Serial.println("JSON parse error!");
    return;
  }

  String cmd = doc["cmd"];
  if (cmd == "off") {
    currentMode = "off";
    solidColor(strip.Color(0, 0, 0));
  }
  else if (cmd == "solid") {
    currentMode = "solid";
    String colorStr = doc["color"];
    if (colorStr.length() == 7 && colorStr[0] == '#') {
      long number = strtol(colorStr.c_str() + 1, NULL, 16);
      currentColor = strip.Color((number >> 16) & 255, (number >> 8) & 255, number & 255);
      solidColor(currentColor);
    }
  }
  else if (cmd == "effect") {
    currentMode = doc["mode"] | "rainbow";
    currentSpeed = doc["speed"] | 50;
  }
}

// --- MQTT reconnect ---
void reconnect() {
  while (!client.connected()) {
    Serial.print("Connecting to MQTT...");
    if (client.connect("ESP32Client")) {
      Serial.println("connected");
      client.subscribe("led/command");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5s");
      delay(5000);
    }
  }
}

// --- Setup ---
void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("WiFi connected");

  strip.begin();
  strip.setBrightness(BRIGHTNESS);
  strip.show();

  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

// --- Loop ---
void loop() {
  if (!client.connected()) reconnect();
  client.loop();

  if (currentMode == "rainbow") {
    rainbow(currentSpeed);
  }
  delay(10);
}
