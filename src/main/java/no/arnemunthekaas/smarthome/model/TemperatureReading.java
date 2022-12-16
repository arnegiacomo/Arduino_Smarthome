package no.arnemunthekaas.smarthome.model;

import jakarta.persistence.*;
import lombok.*;

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
    @Setter(AccessLevel.NONE)
    private int ID;
    @Column(name = "TIME_STAMP")
    private Timestamp TIMESTAMP;
    private float INDOORTEMP;
    private float OUTDOORTEMP;

}
