package Project.DataBase;

import Project.Users.Student;
import java.io.*;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class UserDatabase {
    private HashMap<String, Student> DB = new HashMap<>();

    public UserDatabase() {
        // تحميل المستخدمين من ملف CSV عند بدء التشغيل
        List<Student> loadedUsers = CSV_DB.loadUsersFromCSV();
        for (Student s : loadedUsers) {
            DB.put(s.getId(), s);
        }
    }

    public boolean register(Student student) {
        if (DB.containsKey(student.getId())) {
            return false; // المستخدم موجود بالفعل
        }
        DB.put(student.getId(), student);
        saveToFile();
        return true;
    }

    public Student login(String id, String password) {
        Student student = DB.get(id);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(DB.values());
    }

    public boolean userExists(String id) {
        return DB.containsKey(id);
    }

    // تحديث بيانات مستخدم (مثلاً بعد اختيار دور جديد)
    public boolean updateUser(Student updatedUser) {
        if (!DB.containsKey(updatedUser.getId())) {
            return false;
        }
        DB.put(updatedUser.getId(), updatedUser);
        saveToFile();
        return true;
    }

    public String[] getUserDataById(String id) {
        String filePath = "users.csv";  // تأكد من مسار الملف الصحيح
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[1].equals(id)) {
                    return parts;  // إعادة بيانات السطر كسلسلة مصفوفة
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
        return null;  // لم يتم العثور على المستخدم
    }
    private void saveToFile() {
        CSV_DB.saveUsersToCSV(getAllStudents());
    }
}
