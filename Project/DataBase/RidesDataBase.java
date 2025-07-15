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

    //=============Passenger================
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
        File file = new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\passenger_rides.csv");
        if (!file.exists()) {
            System.out.println("📂 passenger_rides.csv file not found.");
            return registeredRides;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\passenger_rides.csv"))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 6) continue;
                String passengerName = parts[0];
                String driverName = parts[1];
                String location = parts[2];
                String destination = parts[3];
                String hour = parts[4];
                String dateAndDay = parts[5];

                if (!passenger.getName().equals(passengerName)) continue;

                Student tempStudent = new Student("", driverName, "", "", "", "", "");
                Driver tempDriver = new Driver(1, "", tempStudent, "", "");

                Ride ride = new Ride(1, tempDriver, location, destination, hour, dateAndDay);
                registeredRides.add(ride);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading passenger_rides.csv: " + e.getMessage());
        }

        return registeredRides;
    }

    public List<Ride> getRecommendedRides(Passenger passenger) {
        List<Ride> recommendedRides = new ArrayList<>();

        Set<String> reservedKeys = new HashSet<>();
        File reservedFile = new File("passenger_rides.csv");
        if (reservedFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(reservedFile))) {
                reader.readLine();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    if (parts.length < 6) continue;
                    String passengerName = parts[0];
                    String driverName = parts[1];
                    String location = parts[2];
                    String destination = parts[3];
                    String hour = parts[4];
                    String dateAndDay = parts[5];

                    if (passengerName.equalsIgnoreCase(passenger.getName())) {
                        String key = driverName + location + destination + hour + dateAndDay;
                        reservedKeys.add(key);
                    }
                }
            } catch (IOException e) {
                System.out.println("❌ Error reading passenger_rides.csv: " + e.getMessage());
            }
        }

        File rideFile = new File("rides.csv");
        if (!rideFile.exists()) {
            System.out.println("📂 rides.csv not found.");
            return recommendedRides;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(rideFile))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 6) continue;

                String driverName = parts[0];
                String location = parts[1];
                String destination = parts[2];
                String hour = parts[3];
                String dateAndDay = parts[4];
                int seats;
                try {
                    seats = Integer.parseInt(parts[5]);
                } catch (NumberFormatException e) {
                    continue;
                }

                String key = driverName + location + destination + hour + dateAndDay;
                if (!reservedKeys.contains(key) &&
                        location.equalsIgnoreCase(passenger.getLocation()) &&
                        destination.equalsIgnoreCase(passenger.getCollege())) {

                    Student tempStudent = new Student("", driverName, "", "", "", "", "");
                    Driver tempDriver = new Driver(seats, "", tempStudent, "", "");
                    Ride ride = new Ride(seats, tempDriver, location, destination, hour, dateAndDay);
                    recommendedRides.add(ride);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading rides.csv: " + e.getMessage());
        }

        return recommendedRides;
    }

    public void removePassengerFromRide(Ride ride, Passenger passenger) {
        File inputFile = new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\passenger_rides.csv");
        File tempFile = new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\passenger_rides_temp.csv");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String currentLine;
            boolean removed = false;

            // انسخ الرأس أولاً
            String header = reader.readLine();
            if (header != null) {
                writer.write(header);
                writer.newLine();
            }

            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",", -1);
                if (parts.length < 6) continue;

                String passengerName = parts[0];
                String driverName = parts[1];
                String location = parts[2];
                String destination = parts[3];
                String hour = parts[4];
                String dateAndDay = parts[5];

                // تطابق تام لكل تفاصيل الرحلة والراكب
                if (passenger.getName().equals(passengerName) &&
                        ride.getDriverName().equals(driverName) &&
                        ride.getLocation().equals(location) &&
                        ride.getDestination().equals(destination) &&
                        ride.getHour().equals(hour) &&
                        ride.getDateAndDay().equals(dateAndDay)) {
                    removed = true; // لا تكتب هذا السطر
                    continue;
                }

                writer.write(currentLine);
                writer.newLine();
            }

            if (removed) {
                System.out.println("✅ Passenger ride removed from CSV.");
            } else {
                System.out.println("❌ Ride not found or already removed.");
            }

        } catch (IOException e) {
            System.out.println("❌ Error while removing passenger ride: " + e.getMessage());
            return;
        }

        // استبدال الملف الأصلي بالملف المؤقت
        if (inputFile.delete()) {
            if (tempFile.renameTo(inputFile)) {
                System.out.println("📁 CSV updated successfully.");
            } else {
                System.out.println("❌ Failed to rename temp file.");
            }
        } else {
            System.out.println("❌ Failed to delete original CSV file.");
        }
    }

    public void reserveRideForPassenger(Ride ride, Passenger passenger) {
        File file = new File("C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\passenger_rides.csv");
        boolean alreadyReserved = false;

        // تحقق من أن الرحلة لم يتم حجزها مسبقًا
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 6) continue;

                String passengerName = parts[0];
                String driverName = parts[1];
                String location = parts[2];
                String destination = parts[3];
                String hour = parts[4];
                String dateAndDay = parts[5];

                if (passenger.getName().equals(passengerName) &&
                        ride.getDriverName().equals(driverName) &&
                        ride.getLocation().equals(location) &&
                        ride.getDestination().equals(destination) &&
                        ride.getHour().equals(hour) &&
                        ride.getDateAndDay().equals(dateAndDay)) {
                    alreadyReserved = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error while checking reservations: " + e.getMessage());
            return;
        }

        if (alreadyReserved) {
            System.out.println("⚠️ Ride already reserved by this passenger.");
            return;
        }

        // أضف الحجز إلى CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String newLine = passenger.getName() + "," +
                    ride.getDriverName() + "," +
                    ride.getLocation() + "," +
                    ride.getDestination() + "," +
                    ride.getHour() + "," +
                    ride.getDateAndDay();
            writer.newLine(); // Start from a new line
            writer.write(newLine);
            System.out.println("✅ Ride reserved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error while writing to passenger_rides.csv: " + e.getMessage());
        }
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
            String line = br.readLine();

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
                    continue;
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