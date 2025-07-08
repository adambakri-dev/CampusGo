package Project.Users;

public class Passenger extends Student {
    private String major;
    private String years;
    private String College;
    public Passenger( String major , String years , Student student , String college){
        // This for get all data from a parent Class (Student)
        super(student.getId(), student.getName(), student.getPassword(),
                student.getEmail(), student.getCollege(), student.getGender() , student.getLocation());
        this.major=major;
        this.years=years;
        this.College=college;

    }

    //Getters
    public String getMajor() {
        return major;
    }
    public String getYears() {
        return years;
    }

    @Override//=> to get a data from the parent Class (Student)
    public String toString() {
        return super.toString();
    }
}
