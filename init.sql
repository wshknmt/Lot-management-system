CREATE TABLE Flight (
    Flight_Number VARCHAR(6) PRIMARY KEY,
    Origin VARCHAR(50),
    Destination VARCHAR(50),
    Start_Timestamp TIMESTAMP,
    Seats_Available_Amount INTEGER
);

CREATE TABLE Passenger (
    Id SERIAL PRIMARY KEY,
    Name VARCHAR(50),
    Surname VARCHAR(50),
    Phone_Number BIGINT
);

CREATE TABLE Reservation (
    Id SERIAL PRIMARY KEY,
    Passenger_Id INTEGER REFERENCES Passenger(id),
    Flight_Id VARCHAR(6) REFERENCES Flight(Flight_Number)
);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('LH1615', 'Warsaw', 'Munich', '2024-04-22 17:00:00', 440);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('KL1316', 'Warsaw', 'Amsterdam', '2024-04-22 17:10:00', 189);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('LO225', 'Warsaw', 'Vienna', '2024-04-22 17:20:00', 162);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('W61307', 'Warsaw', 'London', '2024-04-22 17:30:00', 189);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('SN2556', 'Warsaw', 'Brussels', '2024-04-22 18:05:00', 440);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('TP223', 'Lisbon', 'Washington', '2024-04-22 17:30:00', 162);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('HV5244', 'Lisbon', 'Rotterdam', '2024-04-22 18:10:00', 189);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('AT983', 'Lisbon', 'Casablanca', '2024-04-22 18:40:00', 189);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('AF1195', 'Lisbon', 'Paris', '2024-04-22 18:50:00', 162);

INSERT INTO Flight(Flight_Number, Origin, Destination, Start_Timestamp, Seats_Available_Amount)
values('TP1038', 'Lisbon', 'Barcelona', '2024-04-22 19:00:00', 440);

INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Ava', 'Johnson', 5551234567);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Liam', 'Williams', 5559876543);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Sophia', 'Anderson', 5553217890);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Noah', 'Martinez', 5554561234);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Isabella', 'Taylor', 5557890123);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('James', 'Jackson', 5552345678);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Emma', 'Thompson', 5558765432);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Oliver', 'Harris', 5553456789);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Amelia', 'Walker', 5556789012);
INSERT INTO Passenger(Name, Surname, Phone_Number) VALUES('Ethan', 'Clark', 5550123456);

INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(2, 'AT983');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(4, 'W61307');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(4, 'SN2556');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(7, 'W61307');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(8, 'SN2556');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(3, 'LO225');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(6, 'W61307');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(1, 'LH1615');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(2, 'LH1615');
INSERT INTO Reservation(Passenger_Id, Flight_Id) VALUES(6, 'HV5244');
