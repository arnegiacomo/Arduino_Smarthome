package no.arnemunthekaas.smarthome.controller;

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
@RequestMapping("environment")
@Log4j2
public class EnvironmentController {

    private final Gson gson = new Gson();
    @Autowired
    private EnvironmentDAO environmentDAO;

    @GetMapping(produces = "application/json")
    public String selectAll() {
        List<EnvironmentReading> readings = environmentDAO.findAll();
        log.info("Select all environment readings from database: " + readings.size() + " readings found");
        return gson.toJson(readings);
    }

    @GetMapping(value = "/{ID}", produces = "application/json")
    public String select(@PathVariable int ID) {
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
    public void insert(@RequestParam float TEMPERATURE, @RequestParam float HUMIDITY, @RequestParam float PRESSURE, @RequestParam float LUX, @RequestParam float UVA, @RequestParam float UVB, @RequestParam float UVI) {
        EnvironmentReading environmentReading = new EnvironmentReading();

        environmentReading.setTEMPERATURE(TEMPERATURE);
        environmentReading.setHUMIDITY(HUMIDITY);
        environmentReading.setPRESSURE(PRESSURE);
        environmentReading.setLUX(LUX);
        environmentReading.setUVA(UVA);
        environmentReading.setUVB(UVB);
        environmentReading.setUVI(UVI);
        environmentReading.setTIMESTAMP(new Timestamp(System.currentTimeMillis())); // TODO: Should this be sent by arduino itself (arduino recieves via api-call)?
        log.info("Create new environment reading: " + environmentReading);

        environmentDAO.save(environmentReading);
        log.info("Insert environment reading to database: " + environmentReading);
    }

    @GetMapping(value = "/mostrecent", produces = "application/json")
    public String select() {
        EnvironmentReading reading = environmentDAO.findFirstByOrderByTIMESTAMPDesc();
        log.info("Select most recent environment reading from database: " + reading);
        return gson.toJson(reading);
    }
}
