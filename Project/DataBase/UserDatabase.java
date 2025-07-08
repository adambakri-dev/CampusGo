package Project.DataBase;

import Project.Controllers.LoginController;
import Project.Controllers.ProfileCotroller;
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
        String filePath = "users.csv";
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
    public Driver getDriverById(String id) {
        Student user = DB.get(id);
        if (user instanceof Driver) {
            return (Driver) user;
        }
        return null;
    }

    public Passenger getPassengerById(String id) {
        Student user = DB.get(id);
        if (user instanceof Passenger) {
            return (Passenger) user;
        }
        return null;
    }

    // =======================  CSV =======================

    private void saveUsersToCSV(List<Student> users) {
        try (FileWriter writer = new FileWriter("users.csv")) {
            writer.append("Role,ID,Name,Password,Email,College,Gender,Seats,CarModel,Location,Major,Year\n");

            for (Student user : users) {
                if (user instanceof Driver d) {
                    writer.append("Driver,")
                            .append(d.getId()).append(",")
                            .append(d.getName()).append(",")
                            .append(d.getPassword()).append(",")
                            .append(d.getEmail()).append(",")
                            .append(d.getCollege()).append(",")
                            .append(d.getGender()).append(",")
                            .append(String.valueOf(d.getSeats())).append(",")
                            .append(d.getCarModel()).append(",")
                            .append(d.getLocation()).append(",")
                            .append(d.getMajor()).append(",")
                            .append(d.getYear()).append("\n");
                } else if (user instanceof Passenger p) {
                    writer.append("Passenger,")
                            .append(p.getId()).append(",")
                            .append(p.getName()).append(",")
                            .append(p.getPassword()).append(",")
                            .append(p.getEmail()).append(",")
                            .append(p.getCollege()).append(",")
                            .append(p.getGender()).append(",")
                            .append("0,,")
                            .append(p.getLocation()).append(",")
                            .append(p.getMajor()).append(",")
                            .append(p.getYears()).append("\n");
                } else {
                    writer.append("Student,")
                            .append(user.getId()).append(",")
                            .append(user.getName()).append(",")
                            .append(user.getPassword()).append(",")
                            .append(user.getEmail()).append(",")
                            .append(user.getCollege()).append(",")
                            .append(user.getGender()).append(",")
                            .append("0,,,").append(",,\n"); // باقي الحقول فارغة
                }
            }
        } catch (IOException e) {
            System.out.println( "some thing went wrong :" + e.getMessage());
        }
    }

    private List<Student> loadUsersFromCSV() {
        List<Student> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line = br.readLine(); // تجاهل العنوان

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);

                if (parts.length < 12) {
                    System.out.println("خطأ في السطر: " + line);
                    continue;
                }

                String role = parts[0];
                String id = parts[1];
                String name = parts[2];
                String password = parts[3];
                String email = parts[4];
                String college = parts[5];
                String gender = parts[6];
                String location = parts[9];

                Student base = new Student(id, name, password, email, college, gender, location);

                if (role.equals("Driver")) {
                    int seats = parts[7].isEmpty() ? 0 : Integer.parseInt(parts[7]);
                    String carModel = parts[8];
                    String major = parts[10];
                    String year = parts[11];
                    users.add(new Driver(seats, carModel, base, major, year));
                } else if (role.equals("Passenger")) {
                    String major = parts[10];
                    String year = parts[11];
                    users.add(new Passenger(major, year, base, college));
                } else {
                    users.add(base);
                }
            }

        } catch (IOException e) {
            System.out.println("خطأ أثناء قراءة الملف: " + e.getMessage());
        }
        return users;
    }
}
