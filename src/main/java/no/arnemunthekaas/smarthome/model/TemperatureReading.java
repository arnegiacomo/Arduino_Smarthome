package no.arnemunthekaas.smarthome.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "TemperatureReading", schema = "temperaturedb")
public class TemperatureReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private Timestamp TIME_STAMP;
    private float INDOORTEMP;
    private float OUTDOORTEMP;
}
