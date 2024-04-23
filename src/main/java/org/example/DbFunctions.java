package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;

public class DbFunctions {
    private Connection conn;
    public DbFunctions(String dbname, String user, String password) {
        this.conn = connect(dbname, user, password);
    }
    public Connection connect(String dbname, String user, String password){
        Connection conn=null;
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+dbname, user, password);
            if (conn!=null) {
                System.out.println("Connection Established");
            }
            else {
                System.out.println("Connection Failed");
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    private void executeQuery(String query) {
        Statement statement;
        try {
            statement=conn.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addNewFlight(String flightNumber, String origin, String destination, LocalDateTime startTimestamp, Integer seatsAvailableAmount) {
        String query=String.format("INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount) VALUES('%s','%s','%s','%s','%d');", flightNumber, origin, destination, startTimestamp, seatsAvailableAmount);
        executeQuery(query);
        System.out.println("Row Inserted");
    }

    public void addNewPassenger(String name, String surname, BigInteger phoneNumber) {
        String query=String.format("INSERT INTO  Passenger(Name, Surname, Phone_Number) VALUES('%s','%s','%d');",name,surname, phoneNumber);
        executeQuery(query);
        System.out.println("Row Inserted");
    }

    public void addNewReservation(Integer passengerId, String flightId) {
        String query=String.format("INSERT INTO  Reservation(passenger_Id, flight_Id) VALUES('%d','%s');",passengerId, flightId);
        executeQuery(query);
        System.out.println("Row Inserted");
    }

    public void deleteFlight(String flightNumber) {
        String query=String.format("DELETE FROM Flight WHERE Flight_Number = '%s';",flightNumber);
        executeQuery(query);
        System.out.println("Row deleted");
    }

    public void deletePassenger(Integer passengerId) {
        String query=String.format("DELETE FROM Passenger WHERE Id = '%d';",passengerId);
        executeQuery(query);
        System.out.println("Row deleted");
    }

    public void deleteReservation(Integer reservationId) {
        String query=String.format("DELETE FROM Reservation WHERE Id = '%d';",reservationId);
        executeQuery(query);
        System.out.println("Row deleted");
    }

    public List<Flight> searchFlights(String flightNumber, String origin, String destination, LocalDateTime startTime, Integer seatsAvailable) {
        List<Flight> flights = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            StringBuilder sqlQuery = new StringBuilder("SELECT * FROM Flight WHERE 1=1");

            if (flightNumber != null && !flightNumber.isEmpty()) {
                sqlQuery.append(" AND Flight_Number LIKE ?");
            }
            if (origin != null && !origin.isEmpty()) {
                sqlQuery.append(" AND Origin LIKE ?");
            }
            if (destination != null && !destination.isEmpty()) {
                sqlQuery.append(" AND Destination LIKE ?");
            }
            if (startTime != null) {
                sqlQuery.append(" AND Start_Timestamp >= ?");
            }
            if (seatsAvailable != null) {
                sqlQuery.append(" AND Seats_Available_Amount >= ?");
            }
            preparedStatement = conn.prepareStatement(sqlQuery.toString());
            int parameterIndex = 1;
            if (flightNumber != null && !flightNumber.isEmpty()) {
                preparedStatement.setString(parameterIndex++, flightNumber);
            }
            if (origin != null && !origin.isEmpty()) {
                preparedStatement.setString(parameterIndex++, origin);
            }
            if (destination != null && !destination.isEmpty()) {
                preparedStatement.setString(parameterIndex++, destination);
            }
            if (startTime != null) {
                preparedStatement.setTimestamp(parameterIndex++, Timestamp.valueOf(startTime));
            }
            if (seatsAvailable != null) {
                preparedStatement.setInt(parameterIndex++, seatsAvailable);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setFlightNumber(resultSet.getString("Flight_Number"));
                flight.setOrigin(resultSet.getString("Origin"));
                flight.setDestination(resultSet.getString("Destination"));
                flight.setStartTimestamp((resultSet.getTimestamp("Start_Timestamp")).toLocalDateTime());
                flight.setSeatsAvailableAmount(resultSet.getInt("Seats_Available_Amount"));
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flights;
    }

    public List<Passenger> searchPassengers(Integer id, String name, String surname, BigInteger phoneNumber) {
        List<Passenger> passengers = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            StringBuilder sqlQuery = new StringBuilder("SELECT * FROM Passenger WHERE 1=1");

            if (id != null ) {
                sqlQuery.append(" AND Id = ?");
            }
            if (name != null && !name.isEmpty()) {
                sqlQuery.append(" AND Name LIKE ?");
            }
            if (surname != null && !surname.isEmpty()) {
                sqlQuery.append(" AND Surname LIKE ?");
            }
            if (phoneNumber != null) {
                sqlQuery.append(" AND Phone_Number = ?");
            }
            preparedStatement = conn.prepareStatement(sqlQuery.toString());
            int parameterIndex = 1;
            if (id != null ) {
                preparedStatement.setInt(parameterIndex++, id);
            }
            if (name != null && !name.isEmpty()) {
                preparedStatement.setString(parameterIndex++, name);
            }
            if (surname != null && !surname.isEmpty()) {
                preparedStatement.setString(parameterIndex++, surname);
            }
            if (phoneNumber != null ) {
                preparedStatement.setLong(parameterIndex++, phoneNumber.longValue());
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Passenger passenger = new Passenger();
                passenger.setId(resultSet.getInt("Id"));
                passenger.setName(resultSet.getString("Name"));
                passenger.setSurname(resultSet.getString("Surname"));
                passenger.setPhoneNumber(BigInteger.valueOf(resultSet.getLong("Phone_Number")));
                passengers.add(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return passengers;
    }

    public List<ReservationDetails> searchReservations(Integer id, Integer passengerId, String flightId) {
        List<ReservationDetails> reservationsDetails = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            StringBuilder sqlQuery = new StringBuilder("SELECT r.*, f.*, p.* FROM Reservation r");
            sqlQuery.append(" INNER JOIN Flight f ON r.Flight_Id = f.Flight_Number");
            sqlQuery.append(" INNER JOIN Passenger p ON r.Passenger_Id = p.Id");
            sqlQuery.append(" WHERE 1=1");

            if (id != null) {
                sqlQuery.append(" AND Id = ?");
            }
            if (passengerId != null) {
                sqlQuery.append(" AND Passenger_Id = ?");
            }
            if (flightId != null && !flightId.isEmpty()) {
                sqlQuery.append(" AND Flight_Id LIKE ?");
            }
            preparedStatement = conn.prepareStatement(sqlQuery.toString());
            int parameterIndex = 1;
            if (id != null ) {
                preparedStatement.setInt(parameterIndex++, id);
            }
            if (passengerId != null ) {
                preparedStatement.setInt(parameterIndex++, passengerId);
            }
            if (flightId != null && !flightId.isEmpty()) {
                preparedStatement.setString(parameterIndex++, flightId);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ReservationDetails reservationDetails = new ReservationDetails();
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getInt("Id"));
                reservation.setPassengerId(resultSet.getInt("Passenger_Id"));
                reservation.setFlightId(resultSet.getString("Flight_Id"));

                Passenger passenger = new Passenger();
                passenger.setId(resultSet.getInt("Id"));
                passenger.setName(resultSet.getString("Name"));
                passenger.setSurname(resultSet.getString("Surname"));
                passenger.setPhoneNumber(BigInteger.valueOf(resultSet.getLong("Phone_Number")));

                Flight flight = new Flight();
                flight.setFlightNumber(resultSet.getString("Flight_Number"));
                flight.setOrigin(resultSet.getString("Origin"));
                flight.setDestination(resultSet.getString("Destination"));
                flight.setStartTimestamp((resultSet.getTimestamp("Start_Timestamp")).toLocalDateTime());
                flight.setSeatsAvailableAmount(resultSet.getInt("Seats_Available_Amount"));

                reservationDetails.setReservation(reservation);
                reservationDetails.setPassenger(passenger);
                reservationDetails.setFlight(flight);

                reservationsDetails.add(reservationDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reservationsDetails;
    }

    public void updateFlight(String flightNumber, String origin, String destination, LocalDateTime startTime, Integer seatsAvailable) {
        PreparedStatement preparedStatement = null;
        try {
            StringBuilder sqlQuery = new StringBuilder("UPDATE Flight SET");
            if (origin != null && !origin.isEmpty()) {
                sqlQuery.append(" Origin = ?,");
            }
            if (destination != null && !destination.isEmpty()) {
                sqlQuery.append(" Destination = ?,");
            }
            if (startTime != null) {
                sqlQuery.append(" Start_Timestamp = ?,");
            }
            if (seatsAvailable != null) {
                sqlQuery.append(" Seats_Available_Amount = ?,");
            }
            sqlQuery.deleteCharAt(sqlQuery.length() - 1);
            sqlQuery.append(" WHERE Flight_Number = ?");
            preparedStatement = conn.prepareStatement(sqlQuery.toString());
            int parameterIndex = 1;
            if (origin != null && !origin.isEmpty()) {
                preparedStatement.setString(parameterIndex++, origin);
            }
            if (destination != null && !destination.isEmpty()) {
                preparedStatement.setString(parameterIndex++, destination);
            }
            if (startTime != null) {
                preparedStatement.setTimestamp(parameterIndex++, Timestamp.valueOf(startTime));
            }
            if (seatsAvailable != null) {
                preparedStatement.setInt(parameterIndex++, seatsAvailable);
            }

            preparedStatement.setString(parameterIndex++, flightNumber);
            preparedStatement.executeUpdate();
            System.out.println("Flight with ID " + flightNumber + " updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePassenger(Integer id, String name, String surname, BigInteger phoneNumber) {
        PreparedStatement preparedStatement = null;
        try {
            StringBuilder sqlQuery = new StringBuilder("UPDATE Passenger SET");
            if (name != null && !name.isEmpty()) {
                sqlQuery.append(" Name = ?,");
            }
            if (surname != null && !surname.isEmpty()) {
                sqlQuery.append(" Surname = ?,");
            }
            if (phoneNumber != null) {
                sqlQuery.append(" Phone_Number = ?,");
            }
            sqlQuery.deleteCharAt(sqlQuery.length() - 1);
            sqlQuery.append(" WHERE Id = ?");
            preparedStatement = conn.prepareStatement(sqlQuery.toString());
            int parameterIndex = 1;
            if (name != null && !name.isEmpty()) {
                preparedStatement.setString(parameterIndex++, name);
            }
            if (surname != null && !surname.isEmpty()) {
                preparedStatement.setString(parameterIndex++, surname);
            }
            if (phoneNumber != null) {
                preparedStatement.setLong(parameterIndex++, phoneNumber.longValue());
            }
            preparedStatement.setInt(parameterIndex++, id);
            preparedStatement.executeUpdate();
            System.out.println("Passenger with ID " + id + " updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
