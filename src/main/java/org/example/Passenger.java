package org.example;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Passenger {
    private Integer id;
    private String name;
    private String surname;
    private BigInteger phoneNumber;
}
