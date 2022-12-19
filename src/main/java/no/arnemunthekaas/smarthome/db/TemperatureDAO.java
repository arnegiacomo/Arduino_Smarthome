package no.arnemunthekaas.smarthome.db;

import no.arnemunthekaas.smarthome.model.TemperatureReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureDAO extends JpaRepository<TemperatureReading, Integer> {

    TemperatureReading findFirstByOrderByTIMESTAMPDesc();
}