# Arduino Smart Home
Smart home project using (for now) an
[Arduino MKR 1000 WiFi](https://docs.arduino.cc/hardware/mkr-1000-wifi) based IOT device with indoor and outdoor 
[DS18B20](https://www.kjell.com/globalassets/mediaassets/745057_87081_manual_en.pdf?ref=C2D11F00F2)
temperature sensors, and [Spring Boot](https://spring.io/projects/spring-boot) based REST API-server 
running on a [Raspberry Pi 4](https://www.raspberrypi.com/products/raspberry-pi-4-model-b/) 
using a PostgreSQL database.

### Progress
- [x] Inndor/Outdoor thermometer device
- [x] Temperature Database
- [x] REST API
- [ ] Easily accessible web portal
- [ ] Display cool statistics
- [ ] More devices/sensors?
- [ ] Update DB and API accordingly

### Temperature Reading example
```
{
"ID": 241,
"TIME_STAMP": "Aug 4, 2022, 5:31:44 PM",
"INDOORTEMP": 24.25,
"OUTDOORTEMP": 19.06
}
```
### Arduino schematic
*WIP*
