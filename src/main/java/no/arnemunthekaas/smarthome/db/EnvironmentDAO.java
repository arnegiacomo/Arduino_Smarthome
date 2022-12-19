package no.arnemunthekaas.smarthome.db;

import no.arnemunthekaas.smarthome.model.EnvironmentReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvironmentDAO extends JpaRepository<EnvironmentReading, Integer> {

}
