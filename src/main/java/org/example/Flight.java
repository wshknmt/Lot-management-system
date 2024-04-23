package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Flight {
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime startTimestamp;
    private Integer seatsAvailableAmount;
}
