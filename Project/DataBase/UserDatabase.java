package Project.DataBase;

import Project.Users.Student;
import Project.Users.Driver;
import Project.Users.Passenger;

import java.io.*;
import java.util.*;

public class UserDatabase {
    private HashMap<String, Student> DB = new HashMap<>();

    public UserDatabase() {
        //get users from file.
        List<Student> loadedUsers = loadUsersFromCSV();
        for (Student s : loadedUsers) {
            DB.put(s.getId(), s);
        }
    }

    //this function will handle the register operation.
    public boolean register(Student student) {
        if (DB.containsKey(student.getId())) {
            return false; // this check if user already exist
        }
        DB.put(student.getId(), student);
        saveToFile();
        return true;
    }

    //This functions will handle a login operations.
    public Student login(String id, String password) {
        if (!DB.containsKey(id)) {
            System.out.println("ID not found in database.");
            return null;
        }
        Student student = DB.get(id);
        if (student == null) {
            System.out.println("Student object is null.");
            return null;
        }
        if (student.getPassword().equals(password)) {
            return student;
        }
        System.out.println("Incorrect password.");
        return null;
    }
    public List<Student> getAllStudents() {
        return new ArrayList<>(DB.values());
    }

    public boolean userExists(String id) {
        return DB.containsKey(id);//this will check if the id in database
    }

    public boolean updateUser(Student updatedUser) {
        if (!DB.containsKey(updatedUser.getId())) {//this will update the user according to his role.
            return false;
        }
        DB.put(updatedUser.getId(), updatedUser);
        saveToFile();
        return true;
    }

    public String[] getUserDataById(String id) {//we will get all user data by his id.
        String filePath = "Students.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[1].equals(id)) {
                    return parts;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
        return null;
    }

    private void saveToFile() {
        saveUsersToCSV(getAllStudents());
    }

    public Student getStudentById(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader("Students.csv"))) {
            String line = br.readLine(); // تخطي العنوان
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 7 && parts[0].equals(id)) {
                    // ترتيب: ID,Name,Password,Email,College,Gender,Location
                    return new Student(
                            parts[0], // ID
                            parts[1], // Name
                            parts[2], // Password
                            parts[3], // Email
                            parts[4], // College
                            parts[5], // Gender
                            parts[6]  // Location
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Students.csv: " + e.getMessage());
        }
        return null;
    }

    public Driver getDriverById(String id) {
        String DriverDataBaseFile="C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\DriverDataBase.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(DriverDataBaseFile))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 11 && parts[0].equals(id)) {
                    Student base = new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    int seats = Integer.parseInt(parts[7]);
                    String carModel = parts[8];
                    String major = parts[9];
                    String year = parts[10];
                    return new Driver(seats, carModel, base, major, year);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Drivers.csv: " + e.getMessage());
        }
        return null;
    }

    public Passenger getPassengerById(String id) {
        String PassengerDataBaseFile="C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\PassengerDataBase.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(PassengerDataBaseFile))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 9 && parts[0].equals(id)) {
                    Student base = new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    String major = parts[7];
                    String year = parts[8];
                    return new Passenger(major, year, base);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Passengers.csv: " + e.getMessage());
        }
        return null;
    }

    public boolean addPassenger(Passenger passenger) {
        // تحقق إذا كان موجودًا بالفعل في قاعدة بيانات الركّاب
        try (BufferedReader br = new BufferedReader(new FileReader("PassengerDataBase.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(passenger.getId() + ",")) {
                    return false; // موجود مسبقًا
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking existing passengers: " + e.getMessage());
        }

        // أضفه إلى قاعدة البيانات
        try (FileWriter writer = new FileWriter("PassengerDataBase.csv", true)) {
            writer.append(String.join(",", passenger.getId(), passenger.getName(), passenger.getPassword(),
                    passenger.getEmail(), passenger.getCollege(), passenger.getGender(), passenger.getLocation(),
                    passenger.getMajor(), passenger.getYears())).append("\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error writing passenger to file: " + e.getMessage());
        }
        return false;
    }

    public boolean addDriver(Driver driver) {
        // نفس المنطق المستخدم في addPassenger
        try (BufferedReader br = new BufferedReader(new FileReader("DriverDataBase.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(driver.getId() + ",")) {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking existing drivers: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter("DriverDataBase.csv", true)) {
            writer.append(String.join(",", driver.getId(), driver.getName(), driver.getPassword(),
                    driver.getEmail(), driver.getCollege(), driver.getGender(), driver.getLocation(),
                    String.valueOf(driver.getSeats()), driver.getCarModel(), driver.getMajor(), driver.getYear())).append("\n");
            return true;
        } catch (IOException e) {
            System.out.println("Error writing driver to file: " + e.getMessage());
        }
        return false;
    }


    // =======================  CSV =======================

    private void saveUsersToCSV(List<Student> users) {
        try (
                FileWriter studentWriter = new FileWriter("Students.csv");
                FileWriter driverWriter = new FileWriter("DriverDataBase.csv");
                FileWriter passengerWriter = new FileWriter("PassengerDataBase.csv")
        ) {
            // Headers
            studentWriter.append("ID,Name,Password,Email,College,Gender,Location\n");
            driverWriter.append("ID,Name,Password,Email,College,Gender,Location,Seats,CarModel,Major,Year\n");
            passengerWriter.append("ID,Name,Password,Email,College,Gender,Location,Major,Year\n");

            for (Student user : users) {
                if (user instanceof Driver d) {
                    driverWriter.append(String.join(",", d.getId(), d.getName(), d.getPassword(), d.getEmail(), d.getCollege(),
                                    d.getGender(), d.getLocation(), String.valueOf(d.getSeats()), d.getCarModel(), d.getMajor(), d.getYear()))
                            .append("\n");
                } else if (user instanceof Passenger p) {
                    passengerWriter.append(String.join(",", p.getId(), p.getName(), p.getPassword(), p.getEmail(), p.getCollege(),
                                    p.getGender(), p.getLocation(), p.getMajor(), p.getYears()))
                            .append("\n");
                } else {
                    studentWriter.append(String.join(",", user.getId(), user.getName(), user.getPassword(), user.getEmail(),
                                    user.getCollege(), user.getGender(), user.getLocation()))
                            .append("\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving to CSVs: " + e.getMessage());
        }
    }

    private List<Student> loadUsersFromCSV() {
        List<Student> users = new ArrayList<>();

        // Load students
        try (BufferedReader br = new BufferedReader(new FileReader("Students.csv"))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 7) {
                    users.add(new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Students.csv: " + e.getMessage());
        }

        // Load drivers
        String DriverDataBaseFile="C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\DriverDataBase.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(DriverDataBaseFile))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 11) {
                    Student base = new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    int seats = Integer.parseInt(parts[7]);
                    users.add(new Driver(seats, parts[8], base, parts[9], parts[10]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Drivers.csv: " + e.getMessage());
        }

        // Load passengers
        String PassengerDataBaseFile="C:\\Users\\watanimall\\IdeaProjects\\CollegeProject\\PassengerDataBase.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(PassengerDataBaseFile))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 9) {
                    Student base = new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    users.add(new Passenger(parts[7], parts[8], base));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Passengers.csv: " + e.getMessage());
        }

        return users;
    }
}
