package org.example;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ManagementSystem {
    private static Boolean validateFlightNumber(String flightNumber) {
        try {
            String numericPart = flightNumber.substring(2);
            if(flightNumber.length() > 6 || !numericPart.matches("[0-9]+")) {
                System.out.println("Incorrect flight number");
                return false;
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect flight number");
            return false;
        }
    }
    private static LocalDateTime validateDateAndTime(String dateString, String timeString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalDate date = LocalDate.parse(dateString, dateFormatter);
            LocalTime time = LocalTime.parse(timeString, timeFormatter);
            LocalDateTime localDateTime = LocalDateTime.of(date, time);
            return localDateTime;
        } catch (DateTimeParseException e) {
            System.out.println("Incorrect format of date or time");
            return null;
        }
    }
    private static LocalDateTime validateDateAndTimeSearch(String dateString, String timeString) {
        if(dateString.isEmpty()) return null;
        if(timeString.isEmpty()) timeString = "00:00";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalDate date = LocalDate.parse(dateString, dateFormatter);
            LocalTime time = LocalTime.parse(timeString, timeFormatter);
            LocalDateTime localDateTime = LocalDateTime.of(date, time);
            return localDateTime;
        } catch (DateTimeParseException e) {
            System.out.println("Incorrect format of date or time");
            return null;
        }
    }
    private static Integer validateSeatsAmount(String seatsAmount) {
        try {
            int number = Integer.parseInt(seatsAmount);
            if(number < 0) {
                System.out.println("Seats amount cannot be smaller than 0 ");
                return null;
            }
            return number;
        } catch (NumberFormatException e) {
            System.out.println("Incorrect seats amount");
            return null;
        }
    }
    private static Integer validateSeatsAmountSearch(String seatsAmount) {
        if(seatsAmount.isEmpty()) return null;
        try {
            int number = Integer.parseInt(seatsAmount);
            if(number < 0) {
                System.out.println("Seats amount cannot be smaller than 0 ");
                return null;
            }
            return number;
        } catch (NumberFormatException e) {
            System.out.println("Incorrect seats amount");
            return null;
        }
    }
    private static Boolean validateNumber(String numberString) {
        if( !numberString.matches("[0-9]+")) {
            System.out.println("Incorrect phone number");
            return false;
        }
        return true;
    }
    private static Boolean validatePassengerId(String passengerIdString) {
        if( !passengerIdString.matches("[0-9]+")) {
            System.out.println("Incorrect passenger Id");
            return false;
        }
        return true;
    }
    private static Boolean validateReservationId(String reservationIdString) {
        if( !reservationIdString.matches("[0-9]+")) {
            System.out.println("Incorrect reservation Id");
            return false;
        }
        return true;
    }
    public static void addFlight(DbFunctions db ) {
        Scanner scan = new Scanner(System.in);
        String command;
        System.out.println("Enter valid flight number");
        command = scan.next();
        if(!validateFlightNumber(command)) return;
        String flightNumber = command.toUpperCase();
        System.out.println("Enter valid origin");
        String origin = scan.next();
        System.out.println("Enter valid destination");
        String destination = scan.next();
        System.out.println("Enter valid date in format YYYY:MM:DD");
        String dateString = scan.next();
        System.out.println("Enter valid time in format HH:MM");
        String timeString = scan.next();
        LocalDateTime localDateTime = validateDateAndTime(dateString, timeString);
        if(localDateTime == null) return;
        System.out.println("Enter available seats amount");
        command = scan.next();
        Integer seatsAmount = validateSeatsAmount(command);
        if(seatsAmount == null) return;
        db.addNewFlight(flightNumber, origin, destination, localDateTime, seatsAmount);
    }

    public static void deleteFlight(DbFunctions db ) {
        Scanner scan = new Scanner(System.in);
        String command;
        System.out.println("Enter valid flight number");
        command = scan.next();
        if(!validateFlightNumber(command)) return;
        String flightNumber = command.toUpperCase();
        List<Flight> flights = db.searchFlights(flightNumber, null, null, null, null);
        if(flights.isEmpty()) {
            System.out.println("Flight not found");
        } else {
            if(db.searchReservations(null, null, flights.getFirst().getFlightNumber()).isEmpty()) {
                db.deleteFlight(flightNumber);
                System.out.println("Flight " + flightNumber + " has ben deleted");
            } else {
                System.out.println("Cannot delete flight with scheduled reservations");
            }
        }
    }
    public static void addPassenger(DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter passenger name");
        String name = scan.next();
        System.out.println("Enter passenger surname");
        String surname = scan.next();
        System.out.println("Enter valid phone number");
        String phoneString = scan.next();
        if(!validateNumber(phoneString)) return;
        BigInteger phoneNumber = new BigInteger(phoneString);
        db.addNewPassenger(name, surname, phoneNumber);
    }

    public static void deletePassenger(DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter valid passenger Id");
        String passengerIdString = scan.next();
        if(!validatePassengerId(passengerIdString)) return;
        int id = Integer.parseInt(passengerIdString);
        List<Passenger> passengers = db.searchPassengers(id, null, null, null);
        if(passengers.isEmpty()) {
            System.out.println("Passenger not found");
        } else {
            if(db.searchReservations(null, passengers.getFirst().getId(), null).isEmpty()) {
                db.deletePassenger(id);
                System.out.println("Passenger " + passengers.getFirst().getName() + " " + passengers.getFirst().getSurname() + " has ben deleted");
            } else {
                System.out.println("Cannot delete passenger with scheduled reservations");
            }
        }
    }

    public static void searchFlights(DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        String command;
        System.out.println("Enter flight number or leave field empty");
        String flightNumber = scan.nextLine();
        System.out.println("Enter valid origin or leave field empty");
        String origin = scan.nextLine();
        System.out.println("Enter valid destination or leave field empty");
        String destination = scan.nextLine();
        System.out.println("Enter valid date in format YYYY:MM:DD or leave field empty");
        String dateString = scan.nextLine();
        LocalDateTime localDateTime = null;
        if(!dateString.isEmpty()) {
            System.out.println("Enter valid time in format HH:MM or leave field empty");
            String timeString = scan.nextLine();
            localDateTime = validateDateAndTimeSearch(dateString, timeString);
        }
        System.out.println("Enter available seats amount");
        command = scan.nextLine();
        Integer seatsAmount = validateSeatsAmountSearch(command);
        if(flightNumber.isEmpty()) flightNumber = null;
        if(origin.isEmpty()) origin = null;
        if(destination.isEmpty()) destination = null;
        List<Flight> flights = db.searchFlights(flightNumber, origin, destination, localDateTime, seatsAmount);
        if (!flights.isEmpty()) {
            printFlights(flights);
        } else {
            System.out.println("No data found for the specified criteria");
        }
    }

    public static void searchPassengers(DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter passenger id or leave field empty");
        String idString = scan.nextLine();
        Integer passengerId;
        if (!idString.isEmpty()) {
            if(!validatePassengerId(idString)) {
                System.out.println("Incorrect passenger id");
                return;
            }
            passengerId = Integer.parseInt(idString);
        } else {
            passengerId = null;
        }
        System.out.println("Enter passenger name or leave field empty");
        String name = scan.nextLine();
        System.out.println("Enter passenger surname or leave field empty");
        String surname = scan.nextLine();
        System.out.println("Enter valid phone number or leave field empty");
        String phoneString = scan.nextLine();
        BigInteger phoneNumber;
        if (!phoneString.isEmpty()) {
            if(!validateNumber(phoneString)) {
                System.out.println("Incorrect phone number");
                return;
            }
            phoneNumber = new BigInteger(phoneString);
        } else {
            phoneNumber = null;
        }
        if(name.isEmpty()) name = null;
        if(surname.isEmpty()) surname = null;

        List<Passenger> passengers = db.searchPassengers(passengerId, name, surname, phoneNumber);
        if (!passengers.isEmpty()) {
            printPassengers(passengers);
        } else {
            System.out.println("No data found for the specified criteria");
        }
    }

    public static void searchReservations(DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter reservation id or leave field empty");
        String idReservationString = scan.nextLine();
        Integer reservationId;
        if (!idReservationString.isEmpty()) {
            if(!validateReservationId(idReservationString)) {
                System.out.println("Incorrect reservation id");
                return;
            }
            reservationId = Integer.parseInt(idReservationString);
        } else {
            reservationId = null;
        }
        System.out.println("Enter passenger id or leave field empty");
        String idPassengerString = scan.nextLine();
        Integer passengerId;
        if (!idPassengerString.isEmpty()) {
            if(!validatePassengerId(idPassengerString)) {
                System.out.println("Incorrect passenger id");
                return;
            }
            passengerId = Integer.parseInt(idPassengerString);
        } else {
            passengerId = null;
        }
        System.out.println("Enter flight number or leave field empty");
        String flightNumber = scan.nextLine();
        List<ReservationDetails> reservations = db.searchReservations(reservationId, passengerId, flightNumber);
        if (!reservations.isEmpty()) {
            printReservations(reservations);
        } else {
            System.out.println("No data found for the specified criteria");
        }
    }

    public static void bookFlight(DbFunctions db ) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter passenger id");
        String idPassengerString = scan.next();
        Integer passengerId;
        if(!validatePassengerId(idPassengerString)) return;
        passengerId = Integer.parseInt(idPassengerString);
        System.out.println("Enter flight number");
        String flightNumber = scan.next();
        if(!validateFlightNumber(flightNumber)) return;
        Passenger passenger = db.searchPassengers(passengerId, null, null, null).getFirst();
        if(passenger == null) {
            System.out.println("Passenger not found");
            return;
        }
        Flight flight = db.searchFlights(flightNumber, null, null, null, null).getFirst();
        if(flight == null) {
            System.out.println("Flight not found");
            return;
        } else if(flight.getSeatsAvailableAmount() <= 0) {
            System.out.println("No available seats in this flight");
            return;
        }
        db.addNewReservation(passengerId, flightNumber);
        db.updateFlight(flightNumber, null, null, null, flight.getSeatsAvailableAmount() - 1);
    }

    public static void cancelFlight(DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter reservation id");
        String idReservationString = scan.next();
        Integer reservationId;
        if(!validateReservationId(idReservationString)) return;
        reservationId = Integer.parseInt(idReservationString);
        ReservationDetails reservation = db.searchReservations(reservationId,null, null).getFirst();
        if(reservation == null) {
            System.out.println("Reservation not found");
            return;
        }
        db.deleteReservation(reservationId);
        Flight flight = db.searchFlights(reservation.getFlight().getFlightNumber(), null, null, null, null).getFirst();
        db.updateFlight(flight.getFlightNumber(), null, null, null, flight.getSeatsAvailableAmount() + 1);
    }

    public static void updateFlight(DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        String command;
        System.out.println("Enter flight number");
        String flightNumber = scan.nextLine();
        if(!validateFlightNumber(flightNumber)) return;
        System.out.println("Enter valid origin or leave field empty");
        String origin = scan.nextLine();
        System.out.println("Enter valid destination or leave field empty");
        String destination = scan.nextLine();
        System.out.println("Enter valid date in format YYYY:MM:DD or leave field empty");
        String dateString = scan.nextLine();
        LocalDateTime localDateTime = null;
        if(!dateString.isEmpty()) {
            System.out.println("Enter valid time in format HH:MM or leave field empty");
            String timeString = scan.nextLine();
            localDateTime = validateDateAndTimeSearch(dateString, timeString);
        }
        System.out.println("Enter available seats amount");
        command = scan.nextLine();
        Integer seatsAmount = validateSeatsAmountSearch(command);
        if(flightNumber.isEmpty()) flightNumber = null;
        if(origin.isEmpty()) origin = null;
        if(destination.isEmpty()) destination = null;
        List<Flight> flights = db.searchFlights(flightNumber, null, null, null, null);
        if (!flights.isEmpty()) {
            db.updateFlight(flightNumber, origin, destination, localDateTime, seatsAmount);
        } else {
            System.out.println("No data found for provided flight number");
        }
    }

    public static void updatePassenger(DbFunctions db) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter passenger id");
        String idString = scan.nextLine();
        Integer passengerId;
        if(!validatePassengerId(idString)) return;
        passengerId = Integer.parseInt(idString);
        System.out.println("Enter passenger name or leave field empty");
        String name = scan.nextLine();
        System.out.println("Enter passenger surname or leave field empty");
        String surname = scan.nextLine();
        System.out.println("Enter valid phone number or leave field empty");
        String phoneString = scan.nextLine();
        BigInteger phoneNumber;
        if (!phoneString.isEmpty()) {
            if(!validateNumber(phoneString)) {
                System.out.println("Incorrect phone number");
                return;
            }
            phoneNumber = new BigInteger(phoneString);
        } else {
            phoneNumber = null;
        }
        if(name.isEmpty()) name = null;
        if(surname.isEmpty()) surname = null;

        List<Passenger> passengers = db.searchPassengers(passengerId, null, null, null);
        if (!passengers.isEmpty()) {
            db.updatePassenger(passengerId, name, surname, phoneNumber);
        } else {
            System.out.println("No data found for provided passenger id");
        }
    }

    public static void printFlights(List<Flight> flights) {
        int iter = 1;
        for (Flight flight : flights) {
            System.out.println(iter++ +". Flight Number: " + flight.getFlightNumber());
            System.out.println("Origin: " + flight.getOrigin());
            System.out.println("Destination: " + flight.getDestination());
            System.out.println("Start Time: " + flight.getStartTimestamp());
            System.out.println("Seats Available: " + flight.getSeatsAvailableAmount());
            System.out.println();
        }
    }
    public static void printPassengers(List<Passenger> passengers) {
        int iter = 1;
        for (Passenger passenger : passengers) {
            System.out.println(iter++ +". Id: " + passenger.getId());
            System.out.println("Name: " + passenger.getName());
            System.out.println("Surname: " + passenger.getSurname());
            System.out.println("Phone number " + passenger.getPhoneNumber());
            System.out.println();
        }
    }
    public static void printReservations(List<ReservationDetails> reservations) {
        int iter = 1;
        for (ReservationDetails reservation : reservations) {
            System.out.println(iter++ +". Id: " + reservation.getReservation().getId());
            System.out.println("Passenger Name: " + reservation.getPassenger().getName());
            System.out.println("Passenger Surname: " + reservation.getPassenger().getSurname());
            System.out.println("Flight Number: " + reservation.getFlight().getFlightNumber());
            System.out.println("From: " + reservation.getFlight().getOrigin());
            System.out.println("To: " + reservation.getFlight().getDestination());
            System.out.println("Date: " + reservation.getFlight().getStartTimestamp());
            System.out.println();
        }
    }
    public static void printHelp() {
        System.out.println("Choose one of the commands below");
        System.out.println("* add_flight");
        System.out.println("* delete_flight");
        System.out.println("* add_passenger");
        System.out.println("* delete_passenger");
        System.out.println("* search_flights");
        System.out.println("* search_passengers");
        System.out.println("* search_reservations");
        System.out.println("* book_flight");
        System.out.println("* cancel_flight");
        System.out.println("* update_flight");
        System.out.println("* update_passenger");
        System.out.println("* exit");
        System.out.println("* help");
    }
}
