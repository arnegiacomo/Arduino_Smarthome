package no.arnemunthekaas.smarthome.controller.rest;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import no.arnemunthekaas.smarthome.db.EnvironmentDAO;
import no.arnemunthekaas.smarthome.model.EnvironmentReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/environment")
@Log4j2
public class EnvironmentController {

    private final Gson gson = new Gson();
    @Autowired
    private EnvironmentDAO environmentDAO;

    @GetMapping(produces = "application/json")
    public String findAll() {
        List<EnvironmentReading> readings = environmentDAO.findAll();
        log.info("Select all environment readings from database: " + readings.size() + " readings found");
        return gson.toJson(readings);
    }

    @GetMapping(value = "/{ID}", produces = "application/json")
    public String find(@PathVariable int ID) {
        Optional<EnvironmentReading> reading = environmentDAO.findById(ID);
        log.info("Select environment reading " + ID + " from database: " + reading);

        if(reading.isEmpty()) {
            log.warn("Temperature reading " + ID + " not found");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Temperature reading " + ID + " not found"
            );
        }

        EnvironmentReading environmentReading = reading.get();

        log.info("Environment reading " + ID + " :" + environmentReading);
        return gson.toJson(environmentReading);
    }

    @PostMapping()
    public void save(@RequestParam long UNIXTIMEINSECONDS, @RequestParam float TEMPERATURE, @RequestParam float HUMIDITY, @RequestParam float PRESSURE, @RequestParam float LUX, @RequestParam float UVA, @RequestParam float UVB, @RequestParam float UVI) {
        EnvironmentReading environmentReading = new EnvironmentReading();

        environmentReading.setTIMESTAMP(new Timestamp(UNIXTIMEINSECONDS * 1000));
        environmentReading.setTEMPERATURE(TEMPERATURE);
        environmentReading.setHUMIDITY(HUMIDITY);
        environmentReading.setPRESSURE(PRESSURE);
        environmentReading.setLUX(LUX);
        environmentReading.setUVA(UVA);
        environmentReading.setUVB(UVB);
        environmentReading.setUVI(UVI);
        log.info("Create new environment reading: " + environmentReading);

        environmentDAO.save(environmentReading);
        log.info("Insert environment reading to database: " + environmentReading);
    }

    @GetMapping(value = "/mostrecent", produces = "application/json")
    public String findMostRecent() {
        EnvironmentReading reading = environmentDAO.findFirstByOrderByTIMESTAMPDesc();
        log.info("Select most recent environment reading from database: " + reading);
        return gson.toJson(reading);
    }
}
