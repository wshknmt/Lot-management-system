package org.example;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;



public class DbFunctions {
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

    private static void executeQuery(Connection conn, String query) {
        Statement statement;
        try {
            statement=conn.createStatement();
            statement.executeUpdate(query);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static void addNewFlight(Connection conn, String flightNumber, String origin, String destination, LocalDateTime startTimestamp, Integer seatsAvailableAmount) {
        String query=String.format("INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount) VALUES('%s','%s','%s','%s','%d');", flightNumber, origin, destination, startTimestamp, seatsAvailableAmount);
        executeQuery(conn, query);
        System.out.println("Row Inserted");
    }

    public static void addNewPassenger(Connection conn, String name, String surname, BigInteger phoneNumber) {
        String query=String.format("INSERT INTO  Passenger(Name, Surname, Phone_Number) VALUES('%s','%s','%d');",name,surname, phoneNumber);
        executeQuery(conn, query);
        System.out.println("Row Inserted");
    }

    public static void addNewReservation(Connection conn, Integer passengerId, String flightId) {
        String query=String.format("INSERT INTO  Reservation(passenger_Id, flight_Id) VALUES('%d','%s');",passengerId, flightId);
        executeQuery(conn, query);
        System.out.println("Row Inserted");
    }

    public static void deleteFlight(Connection conn, String flightNumber) {
        String query=String.format("DELETE FROM Flight WHERE Flight_Number = '%s';",flightNumber);
        executeQuery(conn, query);
        System.out.println("Row deleted");
    }

    public static void deletePassenger(Connection conn, Integer passengerId) {
        String query=String.format("DELETE FROM Passenger WHERE Id = '%d';",passengerId);
        executeQuery(conn, query);
        System.out.println("Row deleted");
    }

    public static void deleteReservation(Connection conn, Integer reservationId) {
        String query=String.format("DELETE FROM Reservation WHERE Id = '%d';",reservationId);
        executeQuery(conn, query);
        System.out.println("Row deleted");
    }

    public static List<Flight> searchFlights(Connection conn, String flightNumber, String origin, String destination, LocalDateTime startTime, Integer seatsAvailable) {
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
                flight.flightNumber = (resultSet.getString("Flight_Number"));
                flight.origin = (resultSet.getString("Origin"));
                flight.destination = (resultSet.getString("Destination"));
                flight.startTimestamp = (resultSet.getTimestamp("Start_Timestamp")).toLocalDateTime();
                flight.seatsAvailableAmount = (resultSet.getInt("Seats_Available_Amount"));
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

    public static List<Passenger> searchPassengers(Connection conn, Integer id, String name, String surname, BigInteger phoneNumber) {
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
                passenger.id = (resultSet.getInt("Id"));
                passenger.name = (resultSet.getString("Name"));
                passenger.surname = (resultSet.getString("Surname"));
                passenger.phoneNumber = BigInteger.valueOf(resultSet.getLong("Phone_Number"));
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

    public static List<ReservationDetails> searchReservations(Connection conn, Integer id, Integer passengerId, String flightId) {
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
                reservation.id = (resultSet.getInt("Id"));
                reservation.passengerId = (resultSet.getInt("Passenger_Id"));
                reservation.flightId= (resultSet.getString("Flight_Id"));

                Passenger passenger = new Passenger();
                passenger.id = resultSet.getInt("Id");
                passenger.name = resultSet.getString("Name");
                passenger.surname = resultSet.getString("Surname");
                passenger.phoneNumber = BigInteger.valueOf(resultSet.getLong("Phone_Number"));

                Flight flight = new Flight();
                flight.flightNumber = (resultSet.getString("Flight_Number"));
                flight.origin = (resultSet.getString("Origin"));
                flight.destination = (resultSet.getString("Destination"));
                flight.startTimestamp = (resultSet.getTimestamp("Start_Timestamp")).toLocalDateTime();
                flight.seatsAvailableAmount = (resultSet.getInt("Seats_Available_Amount"));

                reservationDetails.reservation = reservation;
                reservationDetails.passenger = passenger;
                reservationDetails.flight = flight;

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

    public static void updateFlight(Connection conn, String flightNumber, String origin, String destination, LocalDateTime startTime, Integer seatsAvailable) {
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

    public static void updatePassenger(Connection conn, Integer id, String name, String surname, BigInteger phoneNumber) {
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
