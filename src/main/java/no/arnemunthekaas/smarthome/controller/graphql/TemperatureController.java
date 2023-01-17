package no.arnemunthekaas.smarthome.controller.graphql;

import lombok.extern.log4j.Log4j2;
import no.arnemunthekaas.smarthome.db.TemperatureDAO;
import no.arnemunthekaas.smarthome.model.TemperatureReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Log4j2
public class TemperatureController {

    @Autowired
    private TemperatureDAO temperatureDAO;

    @SchemaMapping(typeName = "Query", value = "allTemperatureReadings")
    public List<TemperatureReading> findAll() {
        return temperatureDAO.findAll();
    }
}
