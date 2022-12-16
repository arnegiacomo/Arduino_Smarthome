package no.arnemunthekaas.smarthome.controller;

import no.arnemunthekaas.smarthome.db.TemperatureDAO;
import no.arnemunthekaas.smarthome.model.TemperatureReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("temperature")
public class TemperatureController {

    @Autowired
    private TemperatureDAO repo;

    @GetMapping(produces = "application/json")
    public String getAll() {
        List<TemperatureReading> readings = repo.findAll();

        return readings.get(0).toString();
    }


}
