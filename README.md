# Arduino Smart Home
Smart home project using (for now) an
[Arduino MKR 1000 WiFi](https://docs.arduino.cc/hardware/mkr-1000-wifi) based IOT device with indoor and outdoor 
[DS18B20](https://www.kjell.com/globalassets/mediaassets/745057_87081_manual_en.pdf?ref=C2D11F00F2)
temperature sensors, and [Spring Boot](https://spring.io/projects/spring-boot) based REST API-server 
running on a [Raspberry Pi 4](https://www.raspberrypi.com/products/raspberry-pi-4-model-b/) 
using a PostgreSQL database.

### Device list
*WIP*

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
```

### Arduino schematics
*WIP*

### Gallery
*WIP*