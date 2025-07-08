package Project.Users;

public class Driver extends Student{
    private int seats;
    protected String CarModel;
    private String Major;
    private String Year;

    // Constructors
    public Driver(int seats , String CarModel , Student student , String Major , String Year){
        // This for get all data from a parent Class (Student)
        super(student.getId(), student.getName(), student.getPassword(),
                student.getEmail(), student.getCollege(), student.getGender(), student.getLocation());
        this.seats=seats;
        this.CarModel=CarModel;
        this.Major=Major;
        this.Year=Year;
    }

    // Getters
    public int getSeats() {
        return seats;
    }
    public String getCarModel() {
        return CarModel;
    }
    public String getMajor() {
        return Major;
    }
    public String getYear() {
        return Year;
    }



    // to String
    @Override//=> to get a data from the parent Class (Student)
    public String toString() {
        return super.toString();
    }
}
