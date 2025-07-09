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
    Driver driver;
    Passenger passenger;
    // This constructor to enter a database like a driver to do a driver operations.
    public RidesDataBase(Driver driver) {
        this.rides = new ArrayList<>();
        this.driver=driver;
        loadRidesFromCSV();
    }
    // This constructor to enter a database like a passenger to do a passenger operations.
    public RidesDataBase(Passenger passenger){
        this.rides = new ArrayList<>();
        this.passenger=passenger;
        loadRidesFromCSV();
    }

    // Driver Operations :

    //======================= ADD RIDE ======================
    public void addRide(int seats, Driver driver, String location, String destination, String hour, String dateAndDay, List<String> passengers) {
        Ride ride = new Ride(seats, driver, location, destination, hour, dateAndDay, passengers);
        rides.add(ride);
        saveAllRidesToCSV();
        System.out.println("✅ Ride added successfully!");
    }

    //==================SHOW DRIVER RIDE============================
    public void showDriverRides(String driverName) {
        int index = 0;
        for (Ride ride : rides) {
            if (ride.getDriverName().equals(driverName)) { //this will get all the rides according to driver name.
                System.out.println(index + " - " + ride);
                index++;
            }
        }
        if (index == 0) {
            System.out.println("❌ No rides found for this driver.");
        }
    }

    //================DELETE A RIDE===========================
    public void deleteMyRide(String driverName, int indexToDelete) {
        int driverRideIndex = 0;
        List<Ride> updatedRides = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getDriverName().equals(driverName)) {
                if (driverRideIndex == indexToDelete) {
                    driverRideIndex++;
                    continue;
                }
                driverRideIndex++;
            }
            updatedRides.add(ride);
        }
        this.rides = updatedRides;
        saveAllRidesToCSV();
        System.out.println("✅ Ride deleted successfully from CSV.");
    }

    // Passenger operations :

    //===========SHOW SUITABLE RIDES==================
    public List<Ride> showRides(String location, String college, String desiredDay, String desiredTime) {
        List<Ride> suitableRides = new ArrayList<>();
        String targetDay = desiredDay.trim().toLowerCase();
        String targetTime = desiredTime.trim();
        for (Ride ride : rides) {
            if (!ride.getLocation().equalsIgnoreCase(location)) continue;
            HashMap<String, ArrayList<String>> timeTravel = ride.getTimeTravel();
            for (Map.Entry<String, ArrayList<String>> entry : timeTravel.entrySet()) {
                String rideDay = entry.getKey().toLowerCase();
                if (rideDay.equals(targetDay)) {
                    for (String time : entry.getValue()) {
                        if (time.equalsIgnoreCase(targetTime)) {
                            suitableRides.add(ride);
                            break;
                        }
                    }
                }
            }
        }
        return suitableRides;
    }

    public void registerPassengerToRide(Passenger passenger, Ride ride) {
        try (BufferedReader reader = new BufferedReader(new FileReader(passengerRidesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String passengerId = parts[0];
                    String driverName = parts[2];
                    String location = parts[3];
                    String destination = parts[4];

                    if (passengerId.equals(passenger.getId()) &&
                            driverName.equals(ride.getDriverName()) &&
                            location.equals(ride.getLocation()) &&
                            destination.equals(ride.getDestination())) {
                        System.out.println("❌ You have already registered for this ride.");
                        return;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading passenger ride file: " + e.getMessage());
            return;
        }
        if (ride.getSeats() <= 0) {
            System.out.println("❌ No available seats in this ride.");
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(passengerRidesFile, true))) {
            writer.write(passenger.getId() + "," + passenger.getName() + "," +
                    ride.getDriverName() + "," + ride.getLocation() + "," + ride.getDestination() +","+ride.getTimeTravel());
            writer.newLine();
            System.out.println("✅ Passenger registered to ride successfully!");
            System.out.println("this is more info about driver : \n "+ride.getDriver().getMajor() +"\n"+ride.getDriver().getEmail());
        } catch (IOException e) {
            System.out.println("❌ Error saving passenger ride registration: " + e.getMessage());
        }
        ride.setSeats(ride.getSeats()-1);
    }

    //===========SHOW A PASSENGER RIDES ACCORDING TO DRIVER NAME====================
    public List<Ride> getRidesByDriver(String driverName) {
        List<Ride> result = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getDriverName().equalsIgnoreCase(driverName)) {
                result.add(ride);
            }
        }
        return result;
    }
    //___________________________________________________________________________________________________________

    //CSV File :

    private void saveAllRidesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("PassengerName,DriverName,Location,Destination,Hour,Date&Time");
            bw.newLine();
            for (Ride ride : rides) {
                for (String passenger : ride.getPassengers()) {
                    String line = passenger + "," +
                            ride.getDriverName() + "," +
                            ride.getLocation() + "," +
                            ride.getDestination() + "," +
                            ride.getHour() + "," +
                            ride.getDateAndDay();
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving all rides to CSV: " + e.getMessage());
        }
    }



    private void loadRidesFromCSV() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("📂 Ride file not found. Creating new file.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            rides.clear();
            String line = br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 6) continue;

                String passengerName = parts[0];
                String driverName = parts[1];
                String location = parts[2];
                String destination = parts[3];
                String hour = parts[4];
                String dateAndDay = parts[5];

                Student tempStudent = new Student("", driverName, "", "", "", "", "");
                Driver tempDriver = new Driver(1, "", tempStudent, "", "");

                List<String> passengers = new ArrayList<>();
                passengers.add(passengerName);

                Ride ride = new Ride(1, tempDriver, location, destination, hour, dateAndDay, passengers);
                rides.add(ride);
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading rides: " + e.getMessage());
        }
    }
}
