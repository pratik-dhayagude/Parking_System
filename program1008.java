/*
    -> Parking Lot Automitation System
    1 :Create riquired enum
    2 :Vehical Hierarchy creation
    3 :Vehical Factory Creation
    4 :Parking Spot Hierarchy
    5 :Parking Observer
    6 :ParkingFloor Class 
    7 :Parking Display board (Observer)
    8 :ParkingStrategy Class (strategy Pattern)
    9 :pricingStrategy class (strategy Pattern)
    10:PaymentStrategy class
    11:ParkingTicket Class
    12:EntryGAte Class
    13:ExitGAte Class
    14:ParkingLot class (SingleTone Pattern)
    15:Main Classs(Controller)
*/


import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;

/*///////////////////////////////////////////////////////////////////
     1: Create Enum
            It is used to create fixed constants which are riquired through out the project
*/////////////////////////////////////////////////////

// Repreasents the differrnt tyoe of vehical 
enum VehicalType
{
    BIKE,
    CAR,
    TRUCK

}
// Repreasent different type of parking spot
enum SoptType
{
     BIKE,CAR,TRUCK
}
// Repreasent the current Step of Parking ticket
enum TicketStatus
{
    ACTIVE,
    CLOSED
}
/*///////////////////////////////////////////////////////////////////
     2: Create VehicalClass Hierarchy
     It is used to create multiple type of class which repreasent the type of vehical
     concept : Abstraction , Inheritance, Ploymorphism , encpsulation
*/////////////////////////////////////////////////////
// Class Which repreasent a generic vehical
abstract class Vehical
{
    // Abstracted(Hidden)
    private String vehicalNumber;
    private VehicalType vehicalType;

    // parametrised constructer
    public Vehical(String vehicalNumber,VehicalType vehicalType)
    {
        this.vehicalNumber = vehicalNumber;
        this.vehicalType = vehicalType;
        
    }

    // below 2 methods are concereate getr method
    public VehicalType getVehicalType()
    {
        return this.vehicalType;

    }
    public String getVehicalNumber()
    {
        return this.vehicalNumber;

    }
    // Every concreate class provide its own define
    public abstract void display();
}

// Class which repreasent the vehical type as Bike
class Bike extends Vehical
{
    public Bike(String vehicalNumber)
    {
        // Calls Vehical Class Constructer
        super(vehicalNumber,VehicalType.BIKE);  
    }

    // Method Overriding
    @Override 
    public void display()
    {
        System.out.println("Bike:"+getVehicalNumber());

    }


}

// Class which repreasent the vehical type as Car
class Car extends Vehical
{
    public Car(String vehicalNumber)
    {
        // Calls Vehical Class Constructer
        super(vehicalNumber,VehicalType.CAR);  
    }

    // Method Overriding
    @Override 
    public void display()
    {
        System.out.println("Car:"+getVehicalNumber());

    }


}

// Class which repreasent the vehical type as truck
class Truck extends Vehical
{
    public Truck(String vehicalNumber)
    {
        // Calls Vehical Class Constructer
        super(vehicalNumber,VehicalType.TRUCK);  
    }

    // Method Overriding
    @Override 
    public void display()
    {
        System.out.println("Truck:"+getVehicalNumber());

    }
}


/*///////////////////////////////////////////////////////////////////
     3 : Create VehicalFactory Class
     It is used to create centralize the creation of vehical objects
     concept:Factory Desgin Pattern
*///////////////////////////////////////////////////////////////////
class VehicalFactory
{
    // Create and return the desired class object
    public static Vehical creatVehical(VehicalType type , String number)
    {
        switch(type)
        {
            case BIKE:
                    return new Bike(number);
            
            case CAR:
                    return new Car(number);
            case TRUCK:
                    return new Truck(number);
            default:
                    throw new IllegalArgumentException("Invalid Vehical Type");
        }
    }

}
/*///////////////////////////////////////////////////////////////////
     4 : Create ParkingSpot  Hierarchy
     It is used to create Hierarchy of Parking Spot
     concept : Abstraction , Inheritance, Ploymorphism , encpsulation
*///////////////////////////////////////////////////////////////////

abstract class ParkingSpot
{
    // Unique Number for Parking Spot(Primary Key)
    private int spotNumber;

    // type of ParkingSpot
    private SoptType spotType;

    // It Indicate Spot is Currently occupied or not (return true or false)
    private boolean occupied;

    // Store the Info about the vehical
    private Vehical vehical;

    // Parametrised Constructor
    public ParkingSpot(int spotNumber,SoptType soptType)
    {
        this.spotNumber = spotNumber;
        this.spotType = spotType;

        // Initilize with default value
        this.occupied = false;
        this.vehical = null;

    }

    public int getSpotNumber()
    {
        return this.spotNumber;
    }
    public SoptType getSoptType()
    {
        return this.spotType;
    }
    public boolean isOccupied()
    {
        return this.occupied;
    }
    public Vehical getVehical()
    {
        return this.vehical;
    }

    // It is used to park the vehical 
    public void parkVehical(Vehical vehical)
    {   
        if(this.occupied == true)
        {
            throw new RuntimeException("Parking Spot is already occupid");
        }
        else
        {
            this.vehical= vehical;
            this.occupied= true;

        }
    }
    public Vehical removeVehical()
    {
        if(this.occupied == true)
        {
            Vehical temp = vehical;
            this.vehical = null;
            this.occupied = false;

            return temp;

        }
        else
        {
         
            throw new RuntimeException("Parking Spot is alredy empty");
        }

    }

    // This method decide weather we can park it in the spot or not
    public abstract boolean canFitVehical(Vehical vehical);

    public void display()
    {
        System.out.println("Spot :"+spotNumber+"["+ this.spotType+"]");
        if(this.occupied == true)
        {
            System.out.println("Occupied by:"+vehical.getVehicalNumber());
        }
        else
        {
            System.out.println("Spot is avalible");
        }
    }
    

    

}// End of Parking Spot Class

class BikeSpot extends ParkingSpot
{
    public BikeSpot(int spotNumber)
    {
        super(spotNumber,SoptType.BIKE);
    }
    @Override 
    public boolean canFitVehical(Vehical vehical)
    {
        return vehical.getVehicalType() == VehicalType.BIKE;
    }
}
class CarSpot extends ParkingSpot
{
    public CarSpot(int spotNumber)
    {
        super(spotNumber,SoptType.CAR);
    }
    @Override 
    public boolean canFitVehical(Vehical vehical)
    {
        return vehical.getVehicalType() == VehicalType.CAR;
    }
}

class TruckSpot extends ParkingSpot
{
    public TruckSpot(int spotNumber)
    {
        super(spotNumber,SoptType.TRUCK);
    }
    @Override 
    public boolean canFitVehical(Vehical vehical)
    {
        return vehical.getVehicalType() == VehicalType.TRUCK;
    }
}

/*///////////////////////////////////////////////////////////////////
     5 : Parking Observer Class
     It is used to Automatically update Display board hwen the parking avalibility changes 
     concept : observer desgin Pattern
*///////////////////////////////////////////////////////////////////
/**
 * Inner
 */
interface ParkingObserver 
{
    void update();
}


/*///////////////////////////////////////////////////////////////////
     6 : Parking Floor Class

     It is used to manage Parking Floor

     concept : composition ,ArraryList,object management
*///////////////////////////////////////////////////////////////////

class ParkingFloor
{
    // unique floor number
    private int floorNumber;

    // Collection of all parking Spots

    //Upcasting (Bike/Car/Truck)
    private List<ParkingSpot> parkingSpots;

    // Collection of Observer registereg for the floor
    private List<ParkingObserver> observers;

    public ParkingFloor(int floorNumber)
    {
        this.floorNumber = floorNumber;

        this.parkingSpots = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public int getFloorNumber()
    {
        return this.floorNumber;
    }
    
    public void addParkingSpot(ParkingSpot Spot)
    {
        parkingSpots.add(Spot);


    }
    public void Observer(ParkingObserver observer)
    {
        //Upcatsing 
        observers.add(observer);

    }
    private void notifyObservers()
    {
        for(ParkingObserver observer:observers)
        {
            observer.update();
        }

    }
    // Maethod is going to search Parking spot for apecific type of vehical 
    // Upcating VehicayType => (Bike/Car/Truck)
    public ParkingSpot findAvalibleSpot(Vehical vehical)
    {
     
        for(ParkingSpot spot:parkingSpots)
        {
            if(!spot.isOccupied() && spot.canFitVehical(vehical))
            {
                return spot;

            }
        }
        return null;
        
    }
    // Called when new vehical gets parked
    public void occupySpot(ParkingSpot spot,Vehical vehical)
    {

        //Allocated spot for the vehical
        spot.parkVehical(vehical);
      //   Notify All the observers about the avalibility 
        notifyObservers();
    }
    // Upcasting
    public void releseSpot(ParkingSpot spot)
    {
        // Relese the already allocated spot
        spot.removeVehical();
        //Notify All the observers about the avalibility 
        notifyObservers();
    }
    public int getAvalibleCount(SoptType type)
    {
        int Count=0;
        for(ParkingSpot spot:parkingSpots)
        {
            if(spot.getSoptType() == type && !spot.isOccupied())
            {
                Count++;

            }

        }
        return Count;

    }
    // Display All Parking Spot on all specific floor
    public void displayFloor()
    {
        System.out.println();
        System.out.println("Floor:"+floorNumber);

        for(ParkingSpot spot:parkingSpots)
        {
            spot.display();

        }
    }
}// End of Class ParkingFloor


/*///////////////////////////////////////////////////////////////////
     7 : Create a Parking Display Board class 
     It is used to createe a class which Display the Parking Status

     Subject ->  Parking Floor 
     observer->  Parking Disply Board

     concept : Observer Desgin Pattern
     
     Note :    Any Obserever is going to observ the subject
               There will be multiple observers for the one subject
*////////////////////////////////////////////////////////////////////
class ParkingDisplayBoard implements ParkingObserver
{
    // Floor whose availibility is displayed by this board

    private ParkingFloor floor;
     
    public ParkingDisplayBoard(ParkingFloor floor)
    {
        this.floor = floor;

    }

    // Automitically Called Whenever Floor avalibility changes 
    @Override 
    public void update()
    {   
        System.out.println();
        System.out.println("===================================");
        System.out.println("-----------Display Board-----------");
        System.out.println("FloorNumber:"+floor.getFloorNumber());
        System.out.println("BikeSpot In the Floor:"+floor.getAvalibleCount(SoptType.BIKE));
        System.out.println("CarSpot In the Floor:"+floor.getAvalibleCount(SoptType.CAR));
        System.out.println("BikeSpot In the Floor:"+floor.getAvalibleCount(SoptType.TRUCK));
        System.out.println("===================================");
        System.out.println();


    }
}
// We can create new observers for same subject
/*
    -> Class ParkingWeb Impliments ParkingObserver
    {
        public void Update()
        {

        }
    }
    -> Class ParkingApp impliments ParingObserver
    {
        public void Update
        {
            
        }
    }

*/

/*///////////////////////////////////////////////////////////////////
     8 : Create a Parking Stratergy Class 
     It is used to createe a class ParkingStrategy which is responsible 
     to decide the parking spot selection

     Subject ->  Parking Floor 
     observer->  Parking Disply Board

     concept : Strategy desgin Pattern
    
*////////////////////////////////////////////////////////////////////

// defines a comman concepts for spot selection selection algorithum
interface ParkingStrategy
{
    ParkingSpot findSpot(List<ParkingFloor> floors,Vehical vehical);
}
// selects the first avalible parkng spot
class FirstAvalibleParkingStrategy implements ParkingStrategy
{
    @Override 
    public ParkingSpot findSpot(List<ParkingFloor> floors,Vehical vehical)
    {
        for(ParkingFloor floor : floors)
        {
            ParkingSpot spot = floor.findAvalibleSpot(vehical);
            if(spot != null)
            {
                return spot;
            }

        }
        return null;

    }
}
/*///////////////////////////////////////////////////////////////////
     9 : Create a PricingStrategy class
     It is used to createe a class PricingStrategy 
     it keeps the pricing algorithum indipendrnt of exit logic 

     concept : Strategy desgin Pattern
    
*////////////////////////////////////////////////////////////////////
interface PricingStrategy
{
    double calculatePrice(Vehical vehical,long hours);

}
class NormalPriceStartegy implements PricingStrategy
{
    @Override 
    public double calculatePrice(Vehical vehical,long hours)
    {
        if(hours <= 0)
        {
            hours = 1;

        }
        switch (vehical.getVehicalType())
        {
            case BIKE:
                return hours*20;

            case CAR:
                
                return hours*50;
        
            case TRUCK:
                return hours*100;
            default:
                return 0;
        }
    }


}
class WeekendPriceStartegy implements PricingStrategy
{
    @Override 
    public double calculatePrice(Vehical vehical,long hours)
    {
        if(hours <= 0)
        {
            hours = 1;

        }
        switch (vehical.getVehicalType())
        {
            case BIKE:
                return hours*40;

            case CAR:
                
                return hours*100;
        
            case TRUCK:
                return hours*200;
            default:
                return 0;
        }
    }

}

/*///////////////////////////////////////////////////////////////////
     10 : Create a PaymentStrategy class
     It is used to createe a class PaymentStrategy  
     it supports different types of payment methods 

     concept : Strategy desgin Pattern
    
*////////////////////////////////////////////////////////////////////
// comman contract for all payment method
interface PaymentStrategy
{
    void Pay(double amount);
}
class UPIpayment implements PaymentStrategy
{
    @Override 
    public void Pay(double amount)
    {
        System.out.println("Amount will be paid by UPI:"+amount);
    }

}
class Cardpayment implements PaymentStrategy
{
    @Override 
    public void Pay(double amount)
    {
        System.out.println("Amount will be paid by Card:"+amount);
    }

}
class CashPayment implements PaymentStrategy
{
    @Override 
    public void Pay(double amount)
    {
        System.out.println("Amount will be recived thrugh cash:"+amount);
    }
}
/*///////////////////////////////////////////////////////////////////
     11 : Create a ParkingTicket
     It is used to repreasent one complect parking transaction 

     concept : Strategy desgin Pattern
    
*////////////////////////////////////////////////////////////////////
class ParkingTicket
{
    private static int counter = 1000;

    private int ticketNumber;
    private Vehical vehical;

    private ParkingFloor floor;
    private ParkingSpot spot;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    private TicketStatus status;

    public ParkingTicket(
                        Vehical vehical,
                        ParkingFloor floor,
                        ParkingSpot spot 
                       )
    {
        this.ticketNumber = ++counter;
        this.vehical = vehical;
        this.floor = floor;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;
    }
    public int getTicketNumber()
    {
        return this.ticketNumber;

    }
    public Vehical getVehical()
    {
        return this.vehical;

    }
    public ParkingFloor getFloor()
    {
        return this.floor;
    }
    public ParkingSpot getSpot()
    {
        return this.spot;
    }
    public LocalDateTime getEntry()
    {
        return this.entryTime;
    }
    public LocalDateTime getExitTime()
    {
        return this.exitTime;
    }
    public TicketStatus getStatus()
    {
        return this.status;
    }
    // method gets calledwhen vehical is go out
    public void closeTicket()
    {
        this.exitTime = LocalDateTime.now();
        this.status = TicketStatus.CLOSED;
    }

    // calculate the total number of hours 
    public long calculateHours()
    {
        LocalDateTime endtime;
        if(this.exitTime == null)
        {
            endtime = LocalDateTime.now();
        }
        else
        {
            endtime = exitTime;
        }
        // calculate the actual time converts minutes 
        long minutes = Duration.between(entryTime, endtime).toMinutes();


        // convert minutes to hours
        long hours = minutes/60;

        if(minutes % 60 != 0)
        {
            hours++;
        }
        if(hours == 0)
        {
            hours = 1;
        }
        return hours;
    }

    // it Will Display ticket on screen
    public void displayTicket()
    {
       System.out.println();
       System.out.println("Ticket Number:"+this.ticketNumber);
       System.out.println("Vehical:"+this.vehical.getVehicalNumber());
       System.out.println("Vhical type:"+this.vehical.getVehicalType());
       System.out.println("Floor Number:"+this.floor.getFloorNumber());
       System.out.println("SpotNumber:"+this.spot.getSpotNumber());
       System.out.println("Entry Time:"+this.entryTime);
       System.out.println("Status:"+this.status);
       System.out.println();
    }
}
class program1008
{
    public static void main(String A[])
    {




    }
}