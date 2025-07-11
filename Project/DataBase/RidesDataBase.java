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
        this.rides = new ArrayList<>();
        this.driver = driver;
        loadRidesFromCSV();
    }

    public RidesDataBase(Passenger passenger) {
        this.rides = new ArrayList<>();
        this.passenger = passenger;
        loadRidesFromCSV();
    }

    // ======================= ADD RIDE ======================
    public void addRide(int seats, Driver driver, String location, String destination, String hour, String dateAndDay) {
        Ride ride = new Ride(seats, driver, location, destination, hour, dateAndDay);
        rides.add(ride);
        saveAllRidesToCSV();
        System.out.println("✅ Ride added successfully!");
    }

    // =================== SHOW DRIVER RIDES ============================
    public void showDriverRides(String driverName) {
        int index = 0;
        for (Ride ride : rides) {
            if (ride.getDriverName().equals(driverName)) {
                System.out.println(index + " - " + ride);
                index++;
            }
        }
        if (index == 0) {
            System.out.println("❌ No rides found for this driver.");
        }
    }

    // ================== DELETE A RIDE ===========================
    public void deleteMyRide(String driverName, int indexToDelete) {
        int driverRideIndex = 0;
        List<Ride> updatedRides = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getDriverName().equals(driverName)) {
                if (driverRideIndex == indexToDelete) {
                    driverRideIndex++;
                    continue; // حذف هذه الرحلة
                }
                driverRideIndex++;
            }
            updatedRides.add(ride);
        }
        this.rides = updatedRides;
        saveAllRidesToCSV();
        System.out.println("✅ Ride deleted successfully from CSV.");
    }

    // =============== REGISTER PASSENGER TO RIDE ================
    public void registerPassengerToRide(Passenger passenger, Ride ride) {
        // تحقق من التسجيل المسبق
        try (BufferedReader reader = new BufferedReader(new FileReader(passengerRidesFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String passengerId = parts[0];
                    String driverName = parts[2];
                    String location = parts[3];
                    String destination = parts[4];
                    String dateAndTime = parts[5];

                    if (passengerId.equals(passenger.getId()) &&
                            driverName.equals(ride.getDriverName()) &&
                            location.equals(ride.getLocation()) &&
                            destination.equals(ride.getDestination()) &&
                            dateAndTime.equals(ride.getDateAndDay())) {
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
                    ride.getDriverName() + "," + ride.getLocation() + "," +
                    ride.getDestination() + "," + ride.getDateAndDay());
            writer.newLine();
            System.out.println("✅ Passenger registered to ride successfully!");
            System.out.println("More info about driver:\n" + ride.getDriver().getMajor() + "\n" + ride.getDriver().getEmail());
        } catch (IOException e) {
            System.out.println("❌ Error saving passenger ride registration: " + e.getMessage());
        }
        ride.setSeats(ride.getSeats() - 1);
        saveAllRidesToCSV();
    }

    // =============== GET RIDES BY DRIVER ===================
    public List<Ride> getRidesByDriver(String driverName) {
        List<Ride> result = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getDriverName().equalsIgnoreCase(driverName)) {
                result.add(ride);
            }
        }
        return result;
    }

    // =============== حفظ جميع الرحلات إلى CSV =================
    private void saveAllRidesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("DriverName,Location,Destination,Hour,Date&Time,seats");
            bw.newLine();
            for (Ride ride : rides) {
                String line = ride.getDriverName() + "," +
                        ride.getLocation() + "," +
                        ride.getDestination() + "," +
                        ride.getHour() + "," +
                        ride.getDateAndDay() + "," +
                        ride.getSeats();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving all rides to CSV: " + e.getMessage());
        }
    }

    // =============== تحميل الرحلات من CSV =====================
    private void loadRidesFromCSV() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("📂 Ride file not found. Creating new file.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            rides.clear();
            String line = br.readLine(); // تخطي الرأس

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 6) {
                    System.out.println("❌ Skipping invalid line (not enough columns): " + line);
                    continue;
                }

                String driverName = parts[0];
                String location = parts[1];
                String destination = parts[2];
                String hour = parts[3];
                String dateAndDay = parts[4];
                int seats = 0;
                try {
                    seats = Integer.parseInt(parts[5].trim());
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid seats number format in line: " + line);
                    continue;  // تجاهل هذا السطر واستمر في قراءة باقي السطور
                }

                Student tempStudent = new Student("", driverName, "", "", "", "", "");
                Driver tempDriver = new Driver(1, "", tempStudent, "", "");

                Ride ride = new Ride(seats, tempDriver, location, destination, hour, dateAndDay);
                rides.add(ride);
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading rides: " + e.getMessage());
        }
    }
}
