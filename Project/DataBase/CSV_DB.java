package Project.DataBase;

import Project.Users.Student;
import Project.Users.Driver;
import Project.Users.Passenger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV_DB {
    public static void saveUsersToCSV(List<Student> users) {
        try (FileWriter writer = new FileWriter("users.csv")) {
            // كتابة العناوين الجديدة
            writer.append("Role,ID,Name,Password,Email,College,Gender,Seats,CarModel,Location,Major,Year\n");

            for (Student user : users) {
                if (user instanceof Driver) {
                    Driver d = (Driver) user;
                    writer.append("Driver,");
                    writer.append(d.getId()).append(",");
                    writer.append(d.getName()).append(",");
                    writer.append(d.getPassword()).append(",");
                    writer.append(d.getEmail()).append(",");
                    writer.append(d.getCollege()).append(",");
                    writer.append(d.getGender()).append(",");
                    writer.append(String.valueOf(d.getSeats())).append(",");
                    writer.append(d.getCarModel()).append(",");
                    writer.append(d.getLocation()).append(",");
                    writer.append(d.getMajor()).append(",");
                    writer.append(d.getYear()).append("\n");

                } else if (user instanceof Passenger) {
                    Passenger p = (Passenger) user;
                    writer.append("Passenger,");
                    writer.append(p.getId()).append(",");
                    writer.append(p.getName()).append(",");
                    writer.append(p.getPassword()).append(",");
                    writer.append(p.getEmail()).append(",");
                    writer.append(p.getCollege()).append(",");
                    writer.append(p.getGender()).append(",");
                    writer.append("0,").append(","); // Seats فارغ أو صفر (للسائق)
                    writer.append(","); // CarModel فارغ
                    writer.append(p.getLocation()).append(",");
                    writer.append(p.getMajor()).append(",");
                    writer.append(p.getYears()).append("\n");

                } else {
                    // طالب عادي
                    writer.append("Student,");
                    writer.append(user.getId()).append(",");
                    writer.append(user.getName()).append(",");
                    writer.append(user.getPassword()).append(",");
                    writer.append(user.getEmail()).append(",");
                    writer.append(user.getCollege()).append(",");
                    writer.append(user.getGender()).append(",");
                    writer.append("0,,," + ",,").append("\n"); // باقي الحقول فارغة (Seats=0, CarModel, Location, Major, Year)
                }
            }

            System.out.println("✅ تم حفظ جميع المستخدمين في ملف users.csv بنجاح!");
        } catch (IOException e) {
            System.out.println("❌ حدث خطأ أثناء الحفظ: " + e.getMessage());
        }
    }

    public static List<Student> loadUsersFromCSV() {
        List<Student> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.csv"))) {
            String line = br.readLine(); // قراءة العنوان وتجاهله

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1); // -1 للحفاظ على العناصر الفارغة

                if (parts.length < 7) {
                    System.out.println("❌ السطر لا يحتوي على معلومات كافية: " + line);
                    continue;
                }

                String role = parts[0];
                String id = parts[1];
                String name = parts[2];
                String password = parts[3];
                String email = parts[4];
                String college = parts[5];
                String gender = parts[6];

                if (role.equals("Driver")) {
                    if (parts.length < 12) {
                        System.out.println("❌ بيانات ناقصة للسائق في السطر: " + line);
                        continue;
                    }
                    int seats = parts[7].isEmpty() ? 0 : Integer.parseInt(parts[7]);
                    String carModel = parts[8];
                    String location = parts[9];
                    String major = parts[10];
                    String year = parts[11];

                    Student baseStudent = new Student(id, name, password, email, college, gender);
                    Driver driver = new Driver(seats, carModel, location, baseStudent, major, year);
                    users.add(driver);

                } else if (role.equals("Passenger")) {
                    if (parts.length < 12) {
                        System.out.println("❌ بيانات ناقصة للراكب في السطر: " + line);
                        continue;
                    }
                    String location = parts[9];
                    String major = parts[10];
                    String year = parts[11];

                    Student baseStudent = new Student(id, name, password, email, college, gender);
                    Passenger passenger = new Passenger(location, major, year, baseStudent,college);
                    users.add(passenger);

                } else {
                    // طالب عادي
                    Student student = new Student(id, name, password, email, college, gender);
                    users.add(student);
                }
            }

        } catch (IOException e) {
            System.out.println("❌ حدث خطأ أثناء تحميل المستخدمين من ملف CSV: " + e.getMessage());
        }
        return users;
    }

}
