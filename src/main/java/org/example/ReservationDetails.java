package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDetails {
    private Reservation reservation;
    private Passenger passenger;
    private Flight flight;
}
