import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable=true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays){
        return basePricePerDay*rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;

    }

    public void rent(){
        isAvailable=false;

    }

    public void returnCar(){
        isAvailable=true;
    }


}

class Customer{

    private String customerId;
    private String name;

    public Customer(String customerId,String name){
        this.customerId=customerId;
        this.name=name;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getName(){
        return name;
    }

}

class Rental{
 private Car car;
 private Customer customer;
 private int days;

 public Rental(Car car, Customer customer, int days) {
    this.car = car;
    this.customer = customer;
    this.days = days;
}

public Car getCar() {
    return car;
}

public Customer getCustomer() {
    return customer;
}

public int getDays() {
    return days;
}

}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();


    }

    public void addCar(Car car){
        cars.add(car);

    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car,Customer customer,int days) {
      if(car.isAvailable()){
        car.rent();
        rentals.add(new Rental(car, customer, days));
      }
      else{
        System.out.println("Car is not available for rent");
      }

        
    }

    public void viewRentedCars(){
        if(rentals.isEmpty()){
            System.out.println("No Cars are Currently rented");
        }else{
            System.out.println("\n==List of Rented Cars==\n");
            for(Rental rental:rentals){
                 Car rentedCar=rental.getCar();
                 Customer customer=rental.getCustomer();
                 System.out.println("Car ID: " + rentedCar.getCarId() + " | Car: " + rentedCar.getBrand() + " " + rentedCar.getModel());
                 System.out.println("Rented by: " + customer.getName() + " (Customer ID: " + customer.getCustomerId() + ")");
                 System.out.println("Rental Days: " + rental.getDays() + "\n");
            }
        }
    }


    public void returnCar(Car car){
    
        Rental rentalToRemove=null;

        for(Rental rental:rentals){
            if(rental.getCar()==car){
                rentalToRemove=rental;
                break;
            }
        }

        if(rentalToRemove!=null){
            rentals.remove(rentalToRemove);
        }
        else{
            System.out.println("Car was not Rented ...");
        }


        car.returnCar();
    }




    public void startingMenu(){
        Scanner sc=new Scanner(System.in);
        while (true) {

            System.out.println("######-----Welcome Dear Customer-----###### ");
            System.out.println("****----This is Car Rental Systems----***** ");
            System.out.println("1:Rent a Car");
            System.out.println("2:Return a Car");
            System.out.println("3:View Rented Cars");
            System.out.println("4:Exit");
            System.out.println("Enter your choice:");

            int choice=sc.nextInt();
            sc.nextLine(); 

            if(choice==1){
                System.out.println("\n==Rent a car==\n");
                System.out.print("Enter your name:");
                String customerName=sc.nextLine();

                System.out.println("\nAvailable Cars:");
                for(Car car:cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId()+" - "+car.getBrand()+" "+car.getModel());
                    }

                }

                System.out.println("\nEnter the car ID you want to be rent:");
                String carId=sc.nextLine();

                System.out.println("Enter the number of days for rental a car:");
                int rentalDays=sc.nextInt();

                sc.nextLine();

                Customer newCustomer=new Customer("CUS"+(customers.size()+1),customerName);
                addCustomer(newCustomer);


                Car selectedCar=null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId)&& car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }

                if (selectedCar!=null) {
                    double totalPrice=selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n==Rental Information==\n");
                    System.out.println("Customer ID:"+newCustomer.getCustomerId());
                    System.out.println("Customer Name:"+newCustomer.getName());
                    System.out.println("Car:"+selectedCar.getBrand()+" "+selectedCar.getModel());
                    System.out.println("Rental days:"+rentalDays);
                    System.out.printf("Totalprice:$%.2f%n",totalPrice);


                    System.out.println("\nConfirm rental (Y/N):");
                    String confrim=sc.nextLine();
                    if (confrim.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented sucessfully....");
                        
                    }else{
                        System.out.println( "\nRented Canceled");
                    }

                    
                }else{
                    System.out.println("\nInvalid car selection or car not available for rent..");

                }








            }else if(choice==2){
                System.out.println("\n==Return a Car==\n");
                System.out.println("Enter the Car ID that you want to return");
                String carId=sc.nextLine();


                Car CarToReturn=null;
                for(Car car:cars){
                   if(car.getCarId().equals(carId)&&!car.isAvailable()){
                    CarToReturn=car;
                    break;
                   }
                }
                if(CarToReturn!=null){
                    Customer customer=null;
                    for(Rental rental:rentals){
                        if(rental.getCar()==CarToReturn){
                            customer=rental.getCustomer();
                            break;
                        }
                    }



                    if(customer!=null){
                        returnCar(CarToReturn);
                        System.out.println("Car returned sucessfully by:"+customer.getName());
                    }
                    else{
                        System.out.println("car is not rented or rental information is missing..");
                    }
                }
                else{
                    System.out.println("Invalid car ID or car is not rented");
                }
            }else if(choice==3){
                viewRentedCars();

            }
            else if(choice==4){
                break;

            }
            else{
               System.out.println("Invlaid choice .Please enter the Valid option..");
            }



            
        }
        System.out.println("\nThank you for using the Car Rental System!");
        
        
        sc.close();
    }



}


public class Main {
public static void main(String[] args) {

    CarRentalSystem rentalSystem=new CarRentalSystem();
    Car car1=new Car("UP16A", "Bugati", "Crion", 500);
    Car car2=new Car("UP16B", "RollRocyles", "Phantom", 600);
    Car car3=new Car("UP16C", "Tata", "Defender", 400);
    Car car4=new Car("UP16D", "Mahindra", "Thar", 100);
    Car car5=new Car("UP16E", "Toyata", "fortunre", 250);


    rentalSystem.addCar(car1);
    rentalSystem.addCar(car2);
    rentalSystem.addCar(car3);
    rentalSystem.addCar(car4);
    rentalSystem.addCar(car5);

    rentalSystem.startingMenu();

    
    
}

    
}
