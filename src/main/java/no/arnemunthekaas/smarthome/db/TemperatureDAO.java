package no.arnemunthekaas.smarthome.db;

import java.util.List;

import no.arnemunthekaas.smarthome.model.TemperatureReading;

public interface TemperatureDAO {

    List<TemperatureReading> findAll();

    void insertReading(TemperatureReading temperatureReading);

}