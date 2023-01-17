package no.arnemunthekaas.smarthome.controller.rest;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import no.arnemunthekaas.smarthome.db.TemperatureDAO;
import no.arnemunthekaas.smarthome.model.TemperatureReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/temperature")
@Log4j2
public class TemperatureController {

    private final Gson gson = new Gson();
    @Autowired
    private TemperatureDAO temperatureDAO;

    @GetMapping(produces = "application/json")
    public String findAll() {
        List<TemperatureReading> readings = temperatureDAO.findAll();
        log.info("Select all temperature readings from database: " + readings.size() + " readings found");
        return gson.toJson(readings);
    }

    @GetMapping(value = "/{ID}", produces = "application/json")
    public String find(@PathVariable int ID) {
        Optional<TemperatureReading> reading = temperatureDAO.findById(ID);
        log.info("Select temperature reading " + ID + " from database: " + reading);

        if(reading.isEmpty()) {
            log.warn("Temperature reading " + ID + " not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Temperature reading " + ID + " not found"
            );
        }

        TemperatureReading temperatureReading = reading.get();

        log.info("Temperature reading " + ID + " :" + temperatureReading);
        return gson.toJson(temperatureReading);
    }

    @PostMapping()
    public void save(@RequestParam long UNIXTIMEINSECONDS, @RequestParam float INDOORTEMP, @RequestParam float OUTDOORTEMP) {
        TemperatureReading temperatureReading = new TemperatureReading();

        temperatureReading.setTIMESTAMP(new Timestamp(UNIXTIMEINSECONDS * 1000));
        temperatureReading.setINDOORTEMP(INDOORTEMP);
        temperatureReading.setOUTDOORTEMP(OUTDOORTEMP);
        log.info("Create new temperature reading: " + temperatureReading);

        temperatureDAO.save(temperatureReading);
        log.info("Insert temperature reading to database: " + temperatureReading);
    }

    @GetMapping(value = "/mostrecent", produces = "application/json")
    public String findMostRecent() {
        TemperatureReading reading = temperatureDAO.findFirstByOrderByTIMESTAMPDesc();
        log.info("Select most recent temperature reading from database: " + reading);
        return gson.toJson(reading);
    }

    // TODO: Add query filtering

}
