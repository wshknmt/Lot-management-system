# Lot - zadanie rekrutacyjne
### Autor: Paweł Martyniuk
Celem  jest opracowanie prostego systemu do zarządzania bazą danych lotów i pasażerów.
## Środowisko
Aplikacja została napisana w środowisku Intellij Idea z wykorzystaniem Java w wersji 21. Oprócz tego wykorzystuje bazę danych PostgreSQL w wersji 16. Baza danych została skonteneryzowana przy użyciu Dockera.
## Instrukcja uruchomienia
### Uruchomienie bazy danych
docker compose up
### Uruchomienie aplikacji
mvn install

java -jar target/Lot-zadanie-1.0-SNAPSHOT.jar

Po uruchomieniu programu interakcja z aplikacją jest możliwa przy użyciu consoli. Można wywołać jedną z przedstawionych poniżej komend:
* add_flight
* delete_flight
* add_passenger
* delete_passenger
* search_flights
* search_passengers
* search_reservations
* book_flight
* cancel_flight
* update_flight
* update_passenger
* exit
* help