package Project.DataBase;

import Project.Ride.Ride;
import Project.Users.Driver;
import Project.Users.Passenger;
import Project.Users.Student;

import java.io.*;
import java.util.*;

public class RidesDataBase {
    private List<Ride> rides;
    private final String filePath = "rides.csv";
    private final String passengerRidesFile = "passenger_rides.csv";
    private Driver driver;
    private Passenger passenger;

    // the object will enter the DataBase according to his role
    public RidesDataBase(Driver driver) {
        this.driver = driver;
        this.rides = new ArrayList<>();
        loadRidesFromCSV();
    }

    public RidesDataBase(Passenger passenger) {
        this.passenger = passenger;
        this.rides = new ArrayList<>();
        loadRidesFromCSV();
    }


    //========================Driver Operations =================
    //-----------Add Ride-----------------
    public void addRide(int seats, Driver driver, String location, String destination, String hour, String dateAndDay) {
        Ride ride = new Ride(seats, driver, location, destination, hour, dateAndDay);
        rides.add(ride);
        saveAllRidesToCSV();
        System.out.println("✅ Ride added successfully!");
    }

    //-----------Delete Ride-----------------
    public void deleteMyRide(String driverID, int indexToDelete) {
        // this is a index of driver rides in our list
        int driverRideIndex = 0;
        // this list will contain a new rides after delete
        List<Ride> updatedRides = new ArrayList<>();
        // this will go to every ride in List rides that contain every ride
        for (Ride ride : rides) {
            // this will check if this ride for the same driver
            if (ride.getDriver().getId().equals(driverID)) {
                // if the index we check is the same place of driver rides
                if (driverRideIndex == indexToDelete) {
                    //if yes will skip it and he will not add it to the list
                    driverRideIndex++;
                    continue;
                }
                // if not he will check the on after
                driverRideIndex++;
            }
            // every ride are not the ride we don't want will add it to the list
            updatedRides.add(ride);
        }
        // and this will make the rides are the version of list without the ride we deleted
        this.rides = updatedRides;
        // will save all those changes in CSV file
        saveAllRidesToCSV();
        System.out.println("✅ Ride deleted successfully from CSV.");
    }

    //=====================Passenger Operations================
    //------search ride----------
    public List<Ride> searchRides(String location, String destination, String hour, String dateAndDay) {
        // this list will contain every ride that accepted according to location ......
        List<Ride> matchedRides = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getLocation().equalsIgnoreCase(location) &&
                    ride.getDestination().equalsIgnoreCase(destination) &&
                    ride.getHour().equalsIgnoreCase(hour) &&
                    ride.getDateAndDay().equalsIgnoreCase(dateAndDay)) {
                // if the all conditions are true will add it to list
                matchedRides.add(ride);
            }
        }
        // will do to it return to can show it in ListView
        return matchedRides;
    }

    //------get registered rides for passenger-----
    public List<Ride> getRegisteredRidesForPassenger(Passenger passenger) {
        // this list will contain every ride that passenger register to it
        List<Ride> registeredRides = new ArrayList<>();
        // will check in the file of passenger_Rides,csv
        File file = new File(passengerRidesFile);
        if (!file.exists()) return registeredRides;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                // will rea every line and split it by "," and will count a empty column
                String[] parts = line.split(",", -1);
                // this will check if we take all parts from the line
                if (parts.length < 7) continue;
                // if the passenger in this ride is not the same passenger will go to the next line
                if (!parts[0].equals(passenger.getName())) continue;
                // if he the same will take a info to make the ride and add it to list
                String driverID = parts[1];
                String driverName = parts[2];
                String location = parts[3];
                String destination = parts[4];
                String hour = parts[5];
                String date = parts[6];
                UserDatabase DB=new UserDatabase();
                Driver driver1= DB.getDriverById(driverID);
                Ride ride = new Ride(driver1.getSeats()-1, driver1, location, destination, hour, date);
                registeredRides.add(ride);
            }
        } catch (IOException e) {
            System.out.println("Error reading registered rides: " + e.getMessage());
        }
        return registeredRides;
    }

    //------------get recommended rides -----------------
    public List<Ride> getRecommendedRides(Passenger passenger) {
        // this list will contain all recommended rides
        List <Ride> recommended = new ArrayList<>();
        //this will contain all ride that the passenger registered on them to not add them on recommended list
        Set<String> RegisteredRides = new HashSet<>();
        File file = new File(passengerRidesFile);
        if (file.exists()) {
            // this will take all rides that passenger are register on them from Passenger_Rides.csv
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    if (parts.length < 7) continue;
                    if (parts[0].equalsIgnoreCase(passenger.getName())) {
                        // add a rides to set
                        String key = parts[1] + parts[3] + parts[4] + parts[5] + parts[6];
                        RegisteredRides.add(key);
                    }
                }
            } catch (IOException e) {
                System.out.println(" Error reading passenger rides: " + e.getMessage());
            }
        }

        for (Ride ride : rides) {

            String key = ride.getDriver().getId() + ride.getLocation() + ride.getDestination() +
                    ride.getHour() + ride.getDateAndDay();
            // this will check every ride and if ride in registered rides will not add it and check if the ride (location / destination) are the same of passenger (location/  college)
            if (!RegisteredRides.contains(key) && ride.getLocation().equalsIgnoreCase(passenger.getLocation()) && ride.getDestination().equalsIgnoreCase(passenger.getCollege()) ||  ride.getLocation().equalsIgnoreCase(passenger.getCollege()) && ride.getDestination().equalsIgnoreCase(passenger.getLocation())) {
                recommended.add(ride);
            }
        }
        return recommended;
    }

    //----------remove passenger from ride-------------
    public void removePassengerFromRide(Ride ride, Passenger passenger) {
        File inputFile = new File(passengerRidesFile);
        File tempFile = new File("passenger_rides_temp.csv");
        try (
                // this will read from the original file to delete the ride we want and use the temp file to add the other rides to the file
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String header = reader.readLine();
            if (header != null) {
                writer.write(header);
                writer.newLine();
            }

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // this will read all line and split them by "," and will count the empty space
                String[] parts = currentLine.split(",", -1);
                if (parts.length < 7) continue;
                // if this the ride that we want will do it skip and not adding it to the file
                if (parts[0].equals(passenger.getName()) &&
                        parts[1].equals(ride.getDriver().getId()) &&
                        parts[2].equals(ride.getDriverName()) &&
                        parts[3].equals(ride.getLocation()) &&
                        parts[4].equals(ride.getDestination()) &&
                        parts[5].equals(ride.getHour()) &&
                        parts[6].equals(ride.getDateAndDay())) {
                    continue;
                }
                // all the other ride will add successfully
                writer.write(currentLine);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println(" Error removing ride: " + e.getMessage());
        }
        // and here will do the transfer operation
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    //----reserved ride for passenger---------
    public void reserveRideForPassenger(Ride ride, Passenger passenger) {
        File file = new File(passengerRidesFile);
        boolean alreadyReserved = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            String line;
            // this will read every line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 7) continue;
                // if the passenger in this ride will give is that he already reserved to this ride
                if (parts[0].equals(passenger.getName()) &&
                        parts[1].equals(ride.getDriver().getId()) &&
                        parts[3].equals(ride.getLocation()) &&
                        parts[4].equals(ride.getDestination()) &&
                        parts[5].equals(ride.getHour()) &&
                        parts[6].equals(ride.getDateAndDay())) {
                    alreadyReserved = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error while checking reservation: " + e.getMessage());
            return;
        }
        if (alreadyReserved) {
            System.out.println("Ride already reserved.");
            return;
        }

        boolean rideFound = false;
        for (Ride r : rides) {
            // this will check if this ride are in the list of all rides
            if (r.getDriver().getId().equals(ride.getDriver().getId()) &&
                    r.getLocation().equals(ride.getLocation()) &&
                    r.getDestination().equals(ride.getDestination()) &&
                    r.getHour().equals(ride.getHour()) &&
                    r.getDateAndDay().equals(ride.getDateAndDay())) {

                if (r.getSeats() > 0) {
                    r.setSeats(r.getSeats() - 1);
                    // and here will show us that ride is found
                    rideFound = true;
                } else {
                    System.out.println("No available seats.");
                    return;
                }
                break;
            }
        }

        if (!rideFound) {
            System.out.println("Ride not found in current list.");
            return;
        }

        saveAllRidesToCSV();
        // if the ride found ride is true will add this ride to the passenger in Passenger_Rides.csv
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String line = passenger.getName() + "," +
                    ride.getDriver().getId() + "," +
                    ride.getDriverName() + "," +
                    ride.getLocation() + "," +
                    ride.getDestination() + "," +
                    ride.getHour() + "," +
                    ride.getDateAndDay();
            writer.newLine();
            writer.write(line);
            System.out.println("Ride reserved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing reservation: " + e.getMessage());
        }
    }

    public List<Ride> getRidesByDriver(String driverID) {
        // this list will contain are ride for the driver with this ID
        List<Ride> result = new ArrayList<>();
        for (Ride ride : rides) {
            // every ride that a driver ID of his Driver are the same of The ID we use will add it to the result list
            if (ride.getDriver().getId().equalsIgnoreCase(driverID)) {
                result.add(ride);
            }
        }
        return result;
    }
    // ======================CSV File===============================
    private void saveAllRidesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("DriverID,DriverName,Location,Destination,Hour,Date&Time,seats");
            bw.newLine();
            for (Ride ride : rides) {
                String line = ride.getDriver().getId() + "," +
                        ride.getDriverName() + "," +
                        ride.getLocation() + "," +
                        ride.getDestination() + "," +
                        ride.getHour() + "," +
                        ride.getDateAndDay() + "," +
                        ride.getSeats();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(" Error saving rides: " + e.getMessage());
        }
    }

    public void loadRidesFromCSV() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            rides.clear();
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 7)
                    continue;
                String driverID = parts[0];
                String driverName = parts[1];
                String location = parts[2];
                String destination = parts[3];
                String hour = parts[4];
                String dateAndDay = parts[5];
                int seats = Integer.parseInt(parts[6]);

                Student student = new Student(driverID, driverName, "", "", "", "", "");
                Driver tempDriver = new Driver(seats, "", student, "", "");
                Ride ride = new Ride(seats, tempDriver, location, destination, hour, dateAndDay);
                rides.add(ride);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(" Error loading rides: " + e.getMessage());
        }
    }
}
