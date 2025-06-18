package Project.DataBase;
import Project.Ride.Ride;
import Project.Users.Driver;
import java.io.*;
import java.util.*;
public class RidesDataBase {
    private Driver driver;
    private List<Ride> rides;
    private final String filePath = "rides.csv";

    public RidesDataBase() {
        this.rides = new ArrayList<>();
        loadRidesFromCSV();
    }
    public RidesDataBase(Driver driver){
        this.rides = new ArrayList<>();
        this.driver=driver;
        loadRidesFromCSV();
    }
    //Driver operations database.
    public void addRide(int seats,Driver driver, HashMap<String, ArrayList<String>> timeTravel, String location, String destination) {
        Ride ride = new Ride(seats, driver, timeTravel, location, destination);
        rides.add(ride);
        saveRideToCSV(ride);
        System.out.println("Ride added successfully!");
    }
    public void showDriverRides(String driverName) {
        int index = 1;
        for (Ride ride : rides) {
            if (ride.getDriverName().equals(driverName)) {
                System.out.println(+index+" -  " + ride);
            }
            index++;
        }
    }
    public void DeleteMyRide(String DriverName,int index){
        List<Ride> updatedRides = new ArrayList<>();
        int currentIndex = 0;
        // نحتفظ فقط بالرحلات التي لا نريد حذفها
        for (Ride ride : rides) {
            if (ride.getDriverName().equals(DriverName)) {
                if (currentIndex == index) {
                    currentIndex++;
                    continue; // ❌ تخطى الرحلة المطلوبة للحذف
                }
                currentIndex++;
            }
            updatedRides.add(ride); // ✅ أضف الرحلات الأخرى
        }

        // استبدال القائمة القديمة بالجديدة
        rides = updatedRides;
        saveAllRidesToCSV(); // 📝 إعادة كتابة الملف من الصفر
        System.out.println("✅ Ride deleted successfully from CSV.");
        }
    private void saveAllRidesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Ride ride : rides) {
                String line = ride.getDriverName() + "," +
                        ride.getLocation() + "," +
                        ride.getDestination() + "," +
                        serializeTimeTravel(ride.getTimeTravel()) + "," +
                        ride.getSeats();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving all rides to CSV: " + e.getMessage());
        }
    }
    //passenger operations database.
    public List<Ride> showrides(String location, String college, String desiredDay, String desiredTime) {
        List<Ride> suitableRides = new ArrayList<>();

        for (Ride ride : rides) {
            if (!ride.getLocation().equalsIgnoreCase(location)) {
                continue;
            }
            HashMap<String, ArrayList<String>> timeTravel = ride.getTimeTravel();
            if (timeTravel.containsKey(desiredDay)) {
                if (timeTravel.get(desiredDay).contains(desiredTime)) {
                    suitableRides.add(ride);
                }
            }
        }
        return suitableRides;
    }
    public void ShowRegisteredRides(){}
    public void getRidesByDriver(String driverName) {
        for (Ride ride : rides) {
            if (ride.getDriverName().equals(driverName)) {
                System.out.println(ride);
            }
        }
    }
    private void saveRideToCSV(Ride ride) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(ride.getDriverName() + "," + ride.getLocation() + "," + ride.getDestination() + "," + serializeTimeTravel(ride.getTimeTravel()) + "," + ride.getSeats());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("❌ Error saving ride to CSV: " + e.getMessage());
        }
    }

    private void loadRidesFromCSV() {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);
                String driverName = parts[0];
                String location = parts[1];
                String destination = parts[2];
                HashMap<String, ArrayList<String>> timeTravel = deserializeTimeTravel(parts[3]);
                int seats = Integer.parseInt(parts[4]);
                Ride ride = new Ride(seats, driver, timeTravel, location, destination);
                rides.add(ride);
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading rides: " + e.getMessage());
        }
    }

    private String serializeTimeTravel(HashMap<String, ArrayList<String>> timeTravel) {
        StringBuilder sb = new StringBuilder();
        for (String day : timeTravel.keySet()) {
            sb.append(day).append("=");
            sb.append(String.join("|", timeTravel.get(day)));
            sb.append(";");
        }
        return sb.toString();
    }

    private HashMap<String, ArrayList<String>> deserializeTimeTravel(String data) {
        HashMap<String, ArrayList<String>> timeMap = new HashMap<>();
        String[] entries = data.split(";");
        for (String entry : entries) {
            if (!entry.contains("=")) continue;
            String[] parts = entry.split("=");
            String day = parts[0];
            ArrayList<String> times = new ArrayList<>(Arrays.asList(parts[1].split("\\|")));
            timeMap.put(day, times);
        }
        return timeMap;
    }
}
