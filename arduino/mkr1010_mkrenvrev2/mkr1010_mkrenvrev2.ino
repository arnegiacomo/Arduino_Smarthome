#include "arduino_config.h"

#include <Arduino_MKRENV.h>
#include <WiFiNINA.h>

// Wifi
char SSID[] = SECRET_SSID;    // Network SSID
char PASS[] = SECRET_PASS;      // Network Password
int status = WL_IDLE_STATUS;     // the Wifi radio's status

// API
IPAddress address;
WiFiClient client;

// LED pins
const int R = 25;
const int G = 26;
const int B = 27;

// Startup time
long startup;

void setup() {
  // ------- Debug --------

  Serial.begin(9600);
  while (!Serial) { } // wait for serial port to connect. Disable this in "production"


  Serial.println("----------------------");

  Serial.println("IOT - MKR1010 environmental startup ...");
  Serial.println();

  // ------- Env Shield --------

  Serial.println("MKR ENV Shield initialization ...");
  if (!ENV.begin()) {
    Serial.println("Failed to initialize MKR ENV shield!");
    while(1);
  }
  Serial.println("MKR ENV Shield initialized successfully!");

  // ------- Wifi --------

  wifiSetup();

  // ------- API --------

  address.fromString(ADDRESS);
  Serial.println(address);

  // ------- LED --------

  WiFiDrv::pinMode(R, OUTPUT);
  WiFiDrv::pinMode(G, OUTPUT);
  WiFiDrv::pinMode(B, OUTPUT);

  // ------- Startup --------
  startup = WiFi.getTime();

  Serial.println("IOT - MKR1010 environmental startup complete!");
}

float temperature;
float pressure;
float humidity;
float lux;
float uva;
float uvb;
float uvi;

const int DELAY = 60; // delay
const long reset_time = 60*60*6; // how many seconds between resets, here 6hrs
long time = -1;

void loop() {
  //reset();
  if (time != -1 && WiFi.getTime()-time < DELAY) {
    return; // Return if time since last reading is less than 5 min
  }

  if ((WiFi.getTime() - startup) >= reset_time) {
    reset();
  }
  if (time != -1 && WiFi.getTime()-time < DELAY) {
    return; // Return if time since last reading is less than 60s
  }

  // Do readings
  doReadings();

  // Print readings
  printReadings();

  // Send readings
  sendReadings();
}

void reset() {
  rgb(255, 0, 0);
  delay(100);
  rgb(0, 0, 0);
  delay(100);
  rgb(255, 0, 0);
  delay(100);
  rgb(0, 0, 0);
  delay(100);
  rgb(255, 0, 0);
  delay(100);
  Serial.println();
  Serial.println("----------------------");
  Serial.println("Reset imminent...");
  Serial.println("----------------------");
  Serial.println();
  NVIC_SystemReset();
}

void doReadings() {
  time = WiFi.getTime();
  temperature = ENV.readTemperature();
  humidity = ENV.readHumidity();
  lux = ENV.readIlluminance();
  pressure = ENV.readPressure();
  uva = ENV.readUVA();
  uvb = ENV.readUVB();
  uvi = ENV.readUVIndex();
}

void printReadings() {
  Serial.println();
  Serial.println("----------------------");
  Serial.print("Temperature : ");
  Serial.print(temperature);
  Serial.print("°C");
  Serial.println();
  Serial.print("Humidity : ");
  Serial.print(humidity);
  Serial.print("%");
  Serial.println();
  Serial.print("Pressure : ");
  Serial.print(pressure);
  Serial.print(" kPa");
  Serial.println();
  Serial.print("Illuminance : ");
  Serial.print(lux);
  Serial.print(" lx");
  Serial.println();
  Serial.print("UVA : ");
  Serial.print(uva);
  Serial.print(" μW/cm2.");
  Serial.println();
  Serial.print("UVB : ");
  Serial.print(uvb);
  Serial.print(" μW/cm2.");
  Serial.println();
  Serial.print("UV Index : ");
  Serial.print(uvi);
  Serial.println();
  Serial.println("----------------------");
}

void sendReadings() {
  Serial.println("\nStarting connection to server ...");
  // if you get a connection, report back via serial:
  if (client.connect(ADDRESS, PORT)) {
    Serial.println("Connected to server!");

    // Make a HTTP request:
    String str = "POST /api/environment?UNIXTIMEINSECONDS=";
    str +=  String(time);
    str += "&TEMPERATURE=";
    str +=  String(temperature);
    str += "&HUMIDITY=";
    str += String(humidity);
    str += "&PRESSURE=";
    str += String(pressure);
    str += "&LUX=";
    str += String(lux);
    str += "&UVA=";
    str += String(uva);
    str += "&UVB=";
    str += String(uvb);
    str += "&UVI=";
    str += String(uvi);
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
  } else {
    Serial.println("Unable to connect to server!");
  }

}

void wifiSetup() {
  // Check for the presence of the shield
  Serial.print("u-blox NINA-W102: ");
  if (WiFi.status() == WL_NO_MODULE) {
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

void rgb(int r, int g, int b) {
  WiFiDrv::analogWrite(R, r);     //RED
  WiFiDrv::analogWrite(G, g);   //GREEN
  WiFiDrv::analogWrite(B, b);     //BLUE
}