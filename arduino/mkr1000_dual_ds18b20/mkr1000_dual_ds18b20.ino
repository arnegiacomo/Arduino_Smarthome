#include "arduino_config.h"

#include <OneWire.h>
#include <DallasTemperature.h>
#include <WiFi101.h>
#include <WiFiClient.h>

// Wifi
char SSID[] = SECRET_SSID;    // Network SSID
char PASS[] = SECRET_PASS;      // Network Password
int status = WL_IDLE_STATUS;     // the Wifi radio's status

// API
IPAddress address;
WiFiClient client;

// Data wire for temperature sensors is plugged into digital pin 6 on the Arduino
#define ONE_WIRE_BUS 6

// Setup a oneWire instance to communicate with any OneWire device
OneWire oneWire(ONE_WIRE_BUS);

// Pass oneWire reference to DallasTemperature library
DallasTemperature sensors(&oneWire);

int deviceCount = 0;// Amount of temperature sensors

// LED pins
const int G = 0;
const int B = 1;
const int R = 2;

// Reset pin
const int RESETPIN = 7;
void setup() {

  // ------- Debug --------
//  Serial.begin(9600);
//  while (!Serial) {
//    ; // wait for serial port to connect. Disable this in "production"
//  }

  Serial.println("IOT - MKR1000 Indoor and Outdoor thermometer startup ...");
  Serial.println();

  // ------- Reset pin --------
  digitalWrite(RESETPIN, HIGH);
  delay(200);
  pinMode(RESETPIN, OUTPUT);

  // ------- RGB LED --------
  pinMode(G, OUTPUT);
  pinMode(R, OUTPUT);
  pinMode(B, OUTPUT);

  rgb(255, 0, 0);

  // ------- ds18b20 --------
  sensors.begin();  // Start up the library

  // locate devices on the bus
  Serial.println("Locating temperature sensors ...");
  Serial.print("Found ");
  deviceCount = sensors.getDeviceCount();
  Serial.print(deviceCount, DEC);
  Serial.println(" devices.");
  Serial.println();

  if (deviceCount == 2) {
    rgb(0, 0, 255);
  }

  // ------- WiFi --------
  wifiSetup();

  if (status != WL_IDLE_STATUS) {
    rgb(0, 255, 0);
    delay(250);
  } else {
    rgb(255, 0, 0);
    delay(250);
  }

  // ------- API --------
  address.fromString(ADDRESS);
  Serial.println(address);

  Serial.println();
  Serial.println("IOT - MKR1000 Indoor and Outdoor thermometer startup complete!");
}

float indoorTemp;
float outdoorTemp;
const int minutesperreading = 1;
const int DELAY = minutesperreading * 60 * 1000; // How often to wait between temperature readings in ms (default: every 60s)
int resetCounter = 0;
const int resetInterval = 360;  // How often to reset (reconnects to internet, rediscovers sensors) (every 360*5 readings ~ 6 hrs)
long time = -1;

void loop() {

  // Do readings
  doReadings();

  // Print readings
  printReadings();

  sendReadings();

  delay(500);
  rgb(0, 0, 0);

  delay(DELAY);

  resetCounter++;
  if (resetCounter >= resetInterval) {
    reset();
  }

}

void reset() {
  Serial.println();
  Serial.println("Reset imminent...");
  Serial.println("~~~~~~~~~~~~~~~~~~");
  Serial.println();
  digitalWrite(RESETPIN, LOW);
}

void doReadings() {
  time = WiFi.getTime();

  // Send command to all the sensors for temperature conversion
  sensors.requestTemperatures();
  // Update temperature readings
  indoorTemp = sensors.getTempCByIndex(1);
  outdoorTemp = sensors.getTempCByIndex(0);
}

void printReadings() {
  // Print temperatures
  Serial.println();
  Serial.println("----------------------");
  Serial.print("Unix time : ");
  Serial.print(time);
  Serial.println();
  Serial.print("Indoor : ");
  Serial.print(indoorTemp);
  Serial.print("°C");
  Serial.println();
  Serial.print("Outdoor : ");
  Serial.print(outdoorTemp);
  Serial.print("°C");
  Serial.println();
  Serial.println("----------------------");
}


void sendReadings() {
  Serial.println("\nStarting connection to server ...");
  // if you get a connection, report back via serial:
  rgb(0, 0, 50);
  if (client.connect(ADDRESS, PORT)) {
    Serial.println("Connected to server!");

    // Make a HTTP request:
    String str = "POST /temperature?UNIXTIMEINSECONDS=";
    str +=  String(time);
    str += "&INDOORTEMP=";
    str +=  String(indoorTemp);
    str += "&OUTDOORTEMP=";
    str += String(outdoorTemp);
    str += " HTTP/1.1";
    client.println(str);
    str = "Host: ";
    str += ADDRESS;
    str += ":";
    str += PORT;
    client.println(str);
    client.println("Connection: close");
    client.println();

    Serial.println("Data sent!");
    Serial.println("Connection closed!");
    rgb(0, 50, 0);

  } else {
    Serial.println("Unable to connect to server!");
    rgb(255, 0, 0);
  }

}

void rgb(int r, int g, int b) {
  analogWrite(R, r);
  analogWrite(G, g);
  analogWrite(B, b);
}


void wifiSetup() {
  // Check for the presence of the shield
  Serial.print("WiFi101 shield: ");
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("NOT PRESENT");
    return; // don't continue
  }
  Serial.println("DETECTED");
  // attempt to connect to Wifi network:
  while ( status != WL_CONNECTED) {
    Serial.print("Attempting to connect to Network named: ");
    Serial.print(SSID);                   // print the network name (SSID);
    Serial.println(" ...");
    // Connect to WPA/WPA2 network. Change this line if using open or WEP network:
    status = WiFi.begin(SSID, PASS);
    // wait 10 seconds for connection:
    delay(10000);
  }
  printWifiStatus();
}

void printWifiStatus() {
  // print the SSID of the network you're attached to:
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // print your WiFi shield's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
}




