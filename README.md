# Arduino Smart Home

> Arduino & Raspberry pi "IOT Smart-home" project, based on the Arduino MKR family.

The main java application is a Java 17 Spring Boot application that runs on a Raspberry 4 8gb connected to my home-network. 
The application provides a REST api for the IOT-devices to routinely post new readings to, and to get readings from. All readings 
are recorded into a local PostgreSQL 13 database running on the Raspberry pi. 

Per now the logged data is not used for much, except for some machine learning side-projects. However, the goal will eventually 
be for there to be a web portal/GUI where information from the system can be neatly displayed and retrieved, in addition to
 being able to manage the system and individual devices.

### Device list
- [Raspberry Pi 4](https://www.raspberrypi.com/products/raspberry-pi-4-model-b/) running Java 17 and PostgreSQL 13
- [Arduino MKR 1000 WiFi](https://docs.arduino.cc/hardware/mkr-1000-wifi) running two [ds18b20](https://www.kjell.com/globalassets/mediaassets/745057_87081_manual_en.pdf?ref=C2D11F00F2) 
temperature sensors (Indoor/Outdoor)
- [Arduino MKR 1010 WiFi](https://docs.arduino.cc/hardware/mkr-wifi-1010) running a [MKR ENV Shield Rev 2](https://docs.arduino.cc/hardware/mkr-env-shield) (Indoor)

### Progress
- [x] Indoor/Outdoor thermometers
- [x] Temperature Database
- [x] Indoor environmental reader
- [x] Environmental Database
- [x] REST API
- [ ] Cool web portal
- [ ] Display cool statistics
- [ ] More devices/sensors / update DB and API accordingly
- [ ] Update arduino software to be "smarter" (react to different http responses and act accordingly, offline storage, etc...)

### Temperature Reading example
```
{
    "ID": 241,
    "TIME_STAMP": "Aug 4, 2022, 5:31:44 PM",
    "INDOORTEMP": 24.25,
    "OUTDOORTEMP": 19.06
}
```

### Environmental Reading example
```
{
    "ID": 11,
    "TIMESTAMP": "Jan 14, 2023, 1:42:52 PM",
    "TEMPERATURE": 24.87,
    "HUMIDITY": 34.01,
    "PRESSURE": 99.03,
    "LUX": 15.48,
    "UVA": 0.0,
    "UVB": 0.0,
    "UVI": 0.0
}

note: Beware that all UV data is 0 since this reader is indoors
```

### Gallery
*WIP*