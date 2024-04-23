package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DbFunctions db = new DbFunctions("Lot", "root", "root");
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
                    ManagementSystem.addFlight(db);
                    break;
                case "delete_flight":
                    ManagementSystem.deleteFlight(db);
                    break;
                case "add_passenger":
                    ManagementSystem.addPassenger(db);
                    break;
                case "delete_passenger":
                    ManagementSystem.deletePassenger(db);
                    break;
                case "search_flights":
                    ManagementSystem.searchFlights(db);
                    break;
                case "search_passengers":
                    ManagementSystem.searchPassengers(db);
                    break;
                case "search_reservations":
                    ManagementSystem.searchReservations(db);
                    break;
                case "book_flight":
                    ManagementSystem.bookFlight(db);
                    break;
                case "cancel_flight":
                    ManagementSystem.cancelFlight(db);
                    break;
                case "update_flight":
                    ManagementSystem.updateFlight(db);
                    break;
                case "update_passenger":
                    ManagementSystem.updatePassenger(db);
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
    }
}