package no.arnemunthekaas.smarthome.controller;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import no.arnemunthekaas.smarthome.db.TemperatureDAO;
import no.arnemunthekaas.smarthome.model.TemperatureReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@RestController()
@RequestMapping("temperature")
@Log4j2
public class TemperatureController {

    @Autowired
    private TemperatureDAO temperatureDAO;
    private final Gson gson = new Gson();

    @GetMapping(produces = "application/json")
    public String findAll() {
        List<TemperatureReading> readings = temperatureDAO.findAll();
        return gson.toJson(readings);
    }

    @PostMapping()
    public void insert(@RequestParam float INDOORTEMP, @RequestParam float OUTDOORTEMP) {
        TemperatureReading temperatureReading = new TemperatureReading();

        temperatureReading.setINDOORTEMP(INDOORTEMP);
        temperatureReading.setOUTDOORTEMP(OUTDOORTEMP);
        temperatureReading.setTIME_STAMP(new Timestamp(System.currentTimeMillis()));

        temperatureDAO.save(temperatureReading);
    }


}
