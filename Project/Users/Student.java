package Project.Users;

public class Student {
    protected String id;
    private String name;
    private String password;
    private String email;
    private String college;
    private String gender;

    // Constructors
    public Student(){}
    public Student(String id , String name , String password , String email , String college , String gender){
        this.id=id;
        this.name=name;
        this.password=password;
        this.email=email;
        this.college=college;
        this.gender=gender;
    }

    // Getters
    public String getId(){
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getCollege() {
        return college;
    }
    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }

    // ToString
    public String toString() {
        return " ID: " + id + "\n Name: " + name  +"\n college: " + college +"\n E-mail " + email +"\n gender: " + gender;
    }
}
