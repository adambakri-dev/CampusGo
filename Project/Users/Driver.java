package Project.Users;

public class Driver extends Student{
    private int seats;
    private int income;
    private int CountRides;
    private String CarModel;
    private String location;
    private String Major;
    private String Year;

    // Constructors
    public Driver(int seats , String CarModel , String Location , Student student , String Major , String Year){
        // This for get all data from a parent Class (Student)
        super(student.getId(), student.getName(), student.getPassword(),
                student.getEmail(), student.getCollege(), student.getGender());
        this.seats=seats;
        this.income=0;
        this.CountRides=0;
        this.CarModel=CarModel;
        this.location=Location;
        this.Major=Major;
        this.Year=Year;
    }

    // Getters
    public int getSeats() {
        return seats;
    }
    public int getIncome() {
        return income;
    }
    public int getCountRides() {
        return CountRides;
    }
    public String getCarModel() {
        return CarModel;
    }
    public String getLocation() {
        return location;
    }

    // to String
    @Override//=> to get a data from the parent Class (Student)
    public String toString() {
        return super.toString();
    }
}
