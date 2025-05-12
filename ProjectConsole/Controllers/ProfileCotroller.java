package ProjectConsole.Controllers;

import ProjectConsole.DataBase.UsersDataBase;
import ProjectConsole.Ride.Ride;
import ProjectConsole.Users.Driver;
import ProjectConsole.Users.Passenger;
import ProjectConsole.Users.Student;

import java.util.*;

public class ProfileCotroller {
    private UsersDataBase db;
    private Student student;
    private Driver driver;
    private Passenger passenger;
    public ProfileCotroller(UsersDataBase db , Student student){
        this.db=db;
        this.student=student;
    }
    public void DriverProfile(){
        Scanner scanner=new Scanner(System.in);
        Ride ride=new Ride(db);
        System.out.println("Enter a number of seat : ");
        int seats= scanner.nextInt();
        System.out.println("Enter your Car (with year) : ");
        scanner.nextLine();
        String CarModel= scanner.nextLine();
        System.out.println("Enter your location : ");
        String Location= scanner.nextLine();
        Driver driver=new Driver(seats,CarModel,Location,student);
        this.driver=driver;
        System.out.println(driver.getId());
        System.out.println(student.getIsDriver());
        System.out.println("What you want as a driver : \n 1- Add Ride \n 2-Show Rides \n 3- show info \n 4- exit");
        int DriverChoice= scanner.nextInt();
        ArrayList <String> AllDays=new ArrayList<>(Arrays.asList("Sunday","Monday","Tuesday","Wednesday","Thursday"));
        LinkedHashSet <String> TakenDays=new LinkedHashSet<>();
        switch (DriverChoice){
            case 1 :
                System.out.println("how much days per week want to make a ride : ");
                int CountRide= scanner.nextInt();
                if (CountRide>=1 && CountRide<=5){
                    while (TakenDays.size()<=5){
                        List<String> availableDays = new ArrayList<>(AllDays);
                        System.out.println("--- Choose a day from available days ---");
                        for (int i=0;i<availableDays.size();i++){
                            System.out.println((i+1)+"-"+availableDays.get(i));
                        }
                        System.out.println("--- Chosen Days ---");
                        if (TakenDays.isEmpty()){
                            System.out.println("you didn't choose day yet. \n");
                        }else {
                            for (String days:TakenDays){
                                System.out.println("-"+days);
                            }
                        }
                        System.out.println("your choice (By Number) : ");
                        int ChosenDay= scanner.nextInt();
                        if (ChosenDay<0 || ChosenDay > availableDays.size()){
                            System.out.println("you cant choose this day ");
                        }else {
                            TakenDays.add(availableDays.get(ChosenDay-1));
                            AllDays.remove(ChosenDay-1);
                        }
                        System.out.println("Now you choose the time or ride in each day");
                        for (String day:TakenDays){
                            HashMap <String , String> TimeTravel=new HashMap<>();
                            System.out.println("/!\\ Format => (example 8:00)  /!\\");
                            System.out.println("Choose time for this travel in "+day);
                            String time=scanner.nextLine();
                            TimeTravel.put(day,time);
                        }

                    }
                }else {
                    System.out.println("Wrong choice choose between (1-5)");
                }
            case 2 :

            case 3 :

            case 4 :

                return;

        }
    }
    public void PassengerProfile(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("enter a location : ");
        String Location= scanner.nextLine();
        System.out.println("Enter your major : ");
        String major= scanner.nextLine();
        System.out.println("Enter your year : ");
        String year= scanner.nextLine();
        Passenger passenger=new Passenger(Location,major,year,student);
        System.out.println(passenger);
    }
}
