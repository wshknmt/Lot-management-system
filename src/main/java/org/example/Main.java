package org.example;

import java.sql.Connection;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        DbFunctions db = new DbFunctions();
        Connection conn = db.connect("Lot", "root", "root");
        System.out.println();
        System.out.println("            Lot Management System");
        System.out.println();
        System.out.println("Write command below. If you need help, type command help");
        Scanner scan = new Scanner(System.in);
        String command;
        do {
            command = scan.next();
            switch (command) {
                case "add_flight":
                    ManagementSystem.addFlight(conn);
                    break;
                case "delete_flight":
                    ManagementSystem.deleteFlight(conn);
                    break;
                case "add_passenger":
                    ManagementSystem.addPassenger(conn);
                    break;
                case "delete_passenger":
                    ManagementSystem.deletePassenger(conn);
                    break;
                case "search_flights":
                    ManagementSystem.searchFlights(conn);
                    break;
                case "search_passengers":
                    ManagementSystem.searchPassengers(conn);
                    break;
                case "search_reservations":
                    ManagementSystem.searchReservations(conn);
                    break;
                case "book_flight":
                    ManagementSystem.bookFlight(conn);
                    break;
                case "cancel_flight":
                    ManagementSystem.cancelFlight(conn);
                    break;
                case "update_flight":
                    ManagementSystem.updateFlight(conn);
                    break;
                case "update_passenger":
                    ManagementSystem.updatePassenger(conn);
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    break;
                case "help":
                    ManagementSystem.printHelp();
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
//        List<Passenger> passengers = db.searchPassengers(conn, null, null, null, null);
//        if (!passengers.isEmpty()) {
//            printPassengers(passengers);
//        } else {
//            System.out.println("No data found for the specified criteria");
//        }

    }
}