package no.arnemunthekaas.smarthome.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "MKRENVrev2", schema = "environment")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EnvironmentReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int ID;
    @Column(name = "TIME_STAMP")
    private Timestamp TIMESTAMP;
    private float TEMPERATURE;
    private float HUMIDITY;
    private float PRESSURE;
    private float LUX;
    private float UVA;
    private float UVB;
    private float UVI;

}
