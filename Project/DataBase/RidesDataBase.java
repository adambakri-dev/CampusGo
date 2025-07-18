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

    // إضافة رحلة
    public void addRide(int seats, Driver driver, String location, String destination, String hour, String dateAndDay) {
        Ride ride = new Ride(seats, driver, location, destination, hour, dateAndDay);
        rides.add(ride);
        saveAllRidesToCSV();
        System.out.println("✅ Ride added successfully!");
    }

    // حذف رحلة حسب ID السائق والموقع
    public void deleteMyRide(String driverID, int indexToDelete) {
        int driverRideIndex = 0;
        List<Ride> updatedRides = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getDriver().getId().equals(driverID)) {
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

    public List<Ride> searchRides(String location, String destination, String hour, String dateAndDay) {
        List<Ride> matchedRides = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getLocation().equalsIgnoreCase(location) &&
                    ride.getDestination().equalsIgnoreCase(destination) &&
                    ride.getHour().equalsIgnoreCase(hour) &&
                    ride.getDateAndDay().equalsIgnoreCase(dateAndDay)) {
                matchedRides.add(ride);
            }
        }
        return matchedRides;
    }

    public List<Ride> getRegisteredRidesForPassenger(Passenger passenger) {
        List<Ride> registeredRides = new ArrayList<>();
        File file = new File(passengerRidesFile);
        if (!file.exists()) return registeredRides;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 7) continue;

                if (!parts[0].equals(passenger.getName())) continue;

                String driverID = parts[1];
                String driverName = parts[2];
                String location = parts[3];
                String destination = parts[4];
                String hour = parts[5];
                String date = parts[6];

                Student student = new Student(driverID, driverName, "", "", "", "", "");
                Driver tempDriver = new Driver(0, "", student, "", "");
                Ride ride = new Ride(0, tempDriver, location, destination, hour, date);
                registeredRides.add(ride);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading registered rides: " + e.getMessage());
        }
        return registeredRides;
    }

    public List<Ride> getRecommendedRides(Passenger passenger) {
        List<Ride> recommended = new ArrayList<>();
        Set<String> reservedKeys = new HashSet<>();

        File file = new File(passengerRidesFile);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    if (parts.length < 7) continue;
                    if (parts[0].equalsIgnoreCase(passenger.getName())) {
                        String key = parts[1] + parts[3] + parts[4] + parts[5] + parts[6];
                        reservedKeys.add(key);
                    }
                }
            } catch (IOException e) {
                System.out.println("❌ Error reading passenger rides: " + e.getMessage());
            }
        }

        for (Ride ride : rides) {
            String key = ride.getDriver().getId() + ride.getLocation() + ride.getDestination() +
                    ride.getHour() + ride.getDateAndDay();
            if (!reservedKeys.contains(key) &&
                    ride.getLocation().equalsIgnoreCase(passenger.getLocation()) &&
                    ride.getDestination().equalsIgnoreCase(passenger.getCollege())) {
                recommended.add(ride);
            }
        }
        return recommended;
    }

    public void removePassengerFromRide(Ride ride, Passenger passenger) {
        File inputFile = new File(passengerRidesFile);
        File tempFile = new File("passenger_rides_temp.csv");

        try (
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
                String[] parts = currentLine.split(",", -1);
                if (parts.length < 7) continue;

                if (parts[0].equals(passenger.getName()) &&
                        parts[1].equals(ride.getDriver().getId()) &&
                        parts[2].equals(ride.getDriverName()) &&
                        parts[3].equals(ride.getLocation()) &&
                        parts[4].equals(ride.getDestination()) &&
                        parts[5].equals(ride.getHour()) &&
                        parts[6].equals(ride.getDateAndDay())) {
                    continue;
                }

                writer.write(currentLine);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("❌ Error removing ride: " + e.getMessage());
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    public void reserveRideForPassenger(Ride ride, Passenger passenger) {
        File file = new File(passengerRidesFile);
        boolean alreadyReserved = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 7) continue;

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
            System.out.println("❌ Error while checking reservation: " + e.getMessage());
            return;
        }

        if (alreadyReserved) {
            System.out.println("⚠️ Ride already reserved.");
            return;
        }

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
            System.out.println("✅ Ride reserved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error writing reservation: " + e.getMessage());
        }
    }

    public List<Ride> getRidesByDriver(String driverID) {
        List<Ride> result = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getDriver().getId().equalsIgnoreCase(driverID)) {
                result.add(ride);
            }
        }
        return result;
    }

    private void saveAllRidesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("DriverID,DriverName,Location,Destination,Hour,Date&Time,seats");
            bw.newLine();
            for (Ride ride : rides) {
                String line = ride.getDriver().getId()+ "," +
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
            System.out.println("❌ Error saving rides: " + e.getMessage());
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
                if (parts.length < 7) continue;

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
            System.out.println("❌ Error loading rides: " + e.getMessage());
        }
    }
}
