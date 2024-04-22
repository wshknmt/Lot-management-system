package org.example;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void addFlight(Connection conn) {

    }

    public static void deleteFlight(Connection conn) {

    }

    public static void addPassenger(Connection conn) {

    }

    public static void deletePassenger(Connection conn) {

    }

    public static void searchFlights(Connection conn) {

    }

    public static void searchPassengers(Connection conn) {

    }

    public static void searchReservations(Connection conn) {

    }

    public static void bookFlight(Connection conn) {

    }

    public static void cancelFlight(Connection conn) {

    }

    public static void updateFlight(Connection conn) {

    }

    public static void updatePassenger(Connection conn) {

    }

    public static void printFlights(List<Flight> flights) {
        for (Flight flight : flights) {
            System.out.println("Flight Number: " + flight.flightNumber);
            System.out.println("Origin: " + flight.origin);
            System.out.println("Destination: " + flight.destination);
            System.out.println("Start Time: " + flight.startTimestamp);
            System.out.println("Seats Available: " + flight.seatsAvailableAmount);
            System.out.println();
        }
    }
    public static void printPassengers(List<Passenger> passengers) {
        for (Passenger passenger : passengers) {
            System.out.println("Id: " + passenger.id);
            System.out.println("Name: " + passenger.name);
            System.out.println("Surname: " + passenger.surname);
            System.out.println("Phone number " + passenger.phoneNumber);
            System.out.println();
        }
    }
    public static void printReservations(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            System.out.println("Id: " + reservation.id);
            System.out.println("Passenger Id: " + reservation.passengerId);
            System.out.println("Flight Number: " + reservation.flightId);
            System.out.println();
        }
    }
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect("Lot", "root", "root");
        Scanner scan = new Scanner(System.in);
        String command;
        do {
            command = scan.nextLine();
            switch (command) {
                case "add_flight":
                    addFlight(conn);
                    break;
                case "delete_flight":
                    deleteFlight(conn);
                    break;
                case "add_passenger":
                    addPassenger(conn);
                    break;
                case "delete_passenger":
                    deletePassenger(conn);
                    break;
                case "search_flights":
                    searchFlights(conn);
                    break;
                case "search_passengers":
                    searchPassengers(conn);
                    break;
                case "search_reservations":
                    searchReservations(conn);
                    break;
                case "book_flight":
                    bookFlight(conn);
                    break;
                case "cancel_flight":
                    cancelFlight(conn);
                    break;
                case "update_flight":
                    updateFlight(conn);
                    break;
                case "update_passenger":
                    updatePassenger(conn);
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Wrong command");
            }
        } while (!command.equals("exit"));

//        LocalDateTime timestamp = LocalDateTime.of(2024, 4, 22, 12, 30);
//        List<Flight> flights = db.searchFlights(conn, null, null, null, null, null);
//        if (!flights.isEmpty()) {
//            printFlights(flights);
//        } else {
//            System.out.println("No data found for the specified criteria");
//        }
//
//        db.updateFlight(conn, "AT983", null, "Warsaw", null, null);
        List<Passenger> passengers = db.searchPassengers(conn, null, null, null, null);
        if (!passengers.isEmpty()) {
            printPassengers(passengers);
        } else {
            System.out.println("No data found for the specified criteria");
        }

    }
}