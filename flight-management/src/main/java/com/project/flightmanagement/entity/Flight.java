package com.project.flightmanagement.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.flightmanagement.enums.ArrDepEnum;
import com.sun.istack.NotNull;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;

@Entity
@Table(name = "flight")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="airline_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Airline airline;
    @NotNull
    private   String flightNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="aircraft_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Aircraft aircraft;

    @NotNull
    private ArrDepEnum flightLeg;
    @NotNull
    private LocalDateTime flightDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="system_airport_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Station systemAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="origin_station_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Station originStation;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String updateUser;
}
