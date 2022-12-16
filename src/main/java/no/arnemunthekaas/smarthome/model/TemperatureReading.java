package no.arnemunthekaas.smarthome.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "temperaturereading", schema = "temperaturedb")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TemperatureReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private Timestamp TIME_STAMP;
    private float INDOORTEMP;
    private float OUTDOORTEMP;

}
