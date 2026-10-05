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

import javax.management.RuntimeErrorException;

import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

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

    // Returns all spots on this floor.
    // The GUI uses this method to show the exact FREE/OCCUPIED state.
    public List<ParkingSpot> getParkingSpots()
    {
        return parkingSpots;
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
        System.out.println("TruckSpot In the Floor:"+floor.getAvalibleCount(SoptType.TRUCK));
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
interface pricingStrategy
{
    double calculatePrice(Vehical vehical,long hours);

}
class NormalPriceStartegy implements pricingStrategy
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
class WeekendPriceStartegy implements pricingStrategy
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
/*///////////////////////////////////////////////////////////////////
     12 : Create a Entry gate class 
       -> It is use to handel entry of a vehical and its ticket generation

       
*////////////////////////////////////////////////////////////////////

class EntryGate
{
    private int getNumber;

    public EntryGate(int gateNumber)
    {
        this.getNumber = gateNumber;
    }
    public int getNumber()
    {
        return this.getNumber;
    }
    // It generates the new parking ticket when the vehical enters
    public ParkingTicket generateTicket(Vehical vehical,ParkingFloor floor,ParkingSpot spot)
    {
        System.out.println("Vehical Entring from gate:"+this.getNumber);

        // New parking ticket gets generated fro the vehical 
        return new ParkingTicket(vehical,floor,spot);

    }

}
/*///////////////////////////////////////////////////////////////////
     10 : Create a ExitGate class 
       -> It is use to handel Billing and payment during the exit 

       
*////////////////////////////////////////////////////////////////////

class ExitGate 
{
    private int getNumber;
    public ExitGate(int gateNumber)
    {
        this.getNumber = gateNumber;
    }
    public int getGateNumber()
    {
        return this.getNumber;
    }

    // THis performs complect exit operation
    public double processExit(
                                ParkingTicket ticket,
                                pricingStrategy strategy,
                                PaymentStrategy paymentstrategy
                            )
    {
        // Step 1: Close the ticket and record the exit time
        ticket.closeTicket();

        //Step 2:  Calculate the Parking duration
        long hours = ticket.calculateHours();

        //Step 3: Calculate Parking Charges
        double amount = strategy.calculatePrice(ticket.getVehical(),hours);

        System.out.println();

        System.out.println("Vehical exicting from gate:"+getNumber);
        System.out.println("Parking Duration:"+hours);
        System.out.println("Parking Charges:"+amount);

        // Step 4: Process the payment using the selected strategy.
        paymentstrategy.Pay(amount);

        return amount;
    }
}

/*///////////////////////////////////////////////////////////////////
     14 : Create a Parking Lot Class
    -> PAttern : SingleTOnePattern
    -> This class is the main controller of complect Parking System
       
*////////////////////////////////////////////////////////////////////
class ParkingLot
{
    private static ParkingLot instance;
    // Store The parking lot name

    private String parkingLotName;

    // Store All floors the parking lot
    private List<ParkingFloor>floors;

    // Maps the ticket number with the active Parking slot

    private Map<Integer,ParkingTicket>activeTicket;

    // Maps Vehical Number with the Active Ticket
    // Use for searching vehical 
    /*
        -> It preavent duplicate parking
    
    */
    private Map<String,ParkingTicket>vehicalTicketMap;

    // Algorithum used for selecting ParkingSpot
    private ParkingStrategy parkingStrategy;

    // Algorithum used for Calculating ParkingCharges
    private pricingStrategy pricingStrategy;

    // Private Constructor for singletone class
    private ParkingLot()
    {
        floors = new ArrayList<>();
        activeTicket = new HashMap<>();

        vehicalTicketMap = new HashMap<>();


        //Default parking Strategy 
        parkingStrategy = new FirstAvalibleParkingStrategy();


        // Default Pricing Strategy 
        
        pricingStrategy = new NormalPriceStartegy();



    }

    // Used to set the name of the parking lot
    public void setParkingLotName(String parkingLotName)
    {
        this.parkingLotName = parkingLotName;


    }

    // Used To add new Parking Floor
    public void addFloor(ParkingFloor floor)
    {
        // Insert in arrayList
        floors.add(floor);
    
    }

    // This Method return the list of all they floors
    public List<ParkingFloor> getFloor()
    {
        return floors;

    }

    // this method is used to change the default parking strategy
    public void setParkingStrategy(ParkingStrategy strategy)
    {
        this.parkingStrategy = strategy;

    }

    public void stePricing(pricingStrategy strategy)
    {
        this.pricingStrategy = strategy;
    }


    /*
        -> Cheak Duplicate Vehical
        -> Find Avalible Spot
        -> Identify The Floor
        -> Occupy Spot for they vehical
        -> generate ticket for the vehical
        -> Store the finel ticket
    
    
    */
    public ParkingTicket parkVehical(
                                    Vehical vehical,
                                    EntryGate entryGate
                                    )
    {
        // step 1 : Prevent the same vehical for beg=ing part of multiple types
        if(vehicalTicketMap.containsKey(vehical.getVehicalNumber()))
        {
            System.out.println("This Vehical is alread parked");
            throw new RuntimeException("This Vehical is Already Parked");
        }

        // Step2 : Find they avalible spot

        ParkingSpot spot = parkingStrategy.findSpot(floors, vehical);

        // If there is no empty spot
        if(spot == null)
        {
            throw new RuntimeException("There is no avalible spot PARKING IS FULL");


        }

        //Step3 :

        ParkingFloor selectedFloor = null;
        for(ParkingFloor floor:floors)
        {
            ParkingSpot temp = floor.findAvalibleSpot(vehical);
            if(temp == spot)
            {
                selectedFloor = floor;


            }
            break;
        }
        if(selectedFloor==null)
        {
            throw new RuntimeException("Unable to identify the floor");
        }

        //Step 4 : occupy the spot

        selectedFloor.occupySpot(spot, vehical);

        // Step 5 : Generate the Parking ticket fro the entry gate

        ParkingTicket ticket = entryGate.generateTicket(vehical, selectedFloor, spot);

        // Step 6 : Store the ticket using ticket number

        activeTicket.put(ticket.getTicketNumber(), ticket);

        // Step 7: Store the finel ticket using the vehical number

        vehicalTicketMap.put(vehical.getVehicalNumber(), ticket);
        return ticket;

    }

    /*
        -> Find the ticket 
        -> Process exit
        -> Calculate Charges 
        -> PAyment
        -> reslese Spot
        -> Remove Active records
    
    
    */
    public void removeVehical(int ticketNumber,ExitGate exitGate,PaymentStrategy paymentStrategy)
    {

        // Step 1 : Find active ticket using ticket number

        ParkingTicket ticket = activeTicket.get(ticketNumber);
        if(ticket == null)
        {
            throw new RuntimeException("There is no such ticket");
        }
        //Step 2 : Perform Billing And Payment 

        exitGate.processExit(ticket, pricingStrategy, paymentStrategy);

        // Step3 : Relese the occupied spot 

        ticket .getFloor().releseSpot(ticket.getSpot());

        // Step 4 : Remove ticket 
        activeTicket.remove(ticketNumber);

        //Step 5 : Remove vehical from active vehical 

        vehicalTicketMap.remove(ticket.getVehical().getVehicalNumber());

        System.out.println("Vehical Removed Successfully");


    }

    // Search the Specified Method 

    public ParkingTicket searchVehical(String vehicalNumber)
    {
        if(vehicalNumber == null)
        {
            throw new RuntimeException("There is no Number");
        }
        return vehicalTicketMap.get(vehicalNumber);
        
    }

    public void displayParkingLot()
    {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("---Parking Lot Details---");
        System.out.println("--------------------------");

        for(ParkingFloor floor:floors)
        {
            floor.displayFloor();
        }

    }
    // Method to return the singltone class object

    public static synchronized ParkingLot getInstance()
    {
        if(instance == null)
        {
            instance = new ParkingLot();
        }
        return instance;
    }


}// End of ParkingLotClass



/*
    Step 16 -> Entry Point Fnction(Controller Of Project)


    step 1 : Create Parking Lot 
    step 2 : Create Floors 
    step 3 : Add Parkig Spits 
    step 4 : Create Display Board 
    step 5 : Registers observers 
    step 6 : Add Floor to Parking Slot 
    step 7 : Create entr exit gates

    Step8 : Display menu 




*/

/*
 * ================================================================
 *  GUI CONTROLLER
 * ================================================================
 * This class replaces the old Scanner based main menu.
 * The original system-design classes are kept above and are used
 * directly by this GUI.
 */
class Parking_Engine
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new ParkingLotGUI().showGUI());
    }
}

/*
 * ================================================================
 * PARKING LOT SWING GUI
 * ================================================================
 *
 * Main GUI features:
 * 1. Dashboard with parking statistics
 * 2. Park vehicle
 * 3. Exit vehicle / payment
 * 4. Search vehicle
 * 5. Visual parking-floor status
 * 6. Active ticket information
 * 7. Refresh button
 *
 * The GUI does not replace the system-design logic.
 * It simply provides a user-friendly front end for it.
 */
class ParkingLotGUI
{
    // -------------------- Main System Objects --------------------

    private final ParkingLot parkingLot;
    private final EntryGate entryGate;
    private final ExitGate exitGate;

    // -------------------- Swing Components ----------------------

    private JFrame frame;
    private JLabel totalLabel;
    private JLabel availableLabel;
    private JLabel occupiedLabel;
    private JLabel bikeLabel;
    private JLabel carLabel;
    private JLabel truckLabel;

    private JPanel floorContainer;
    private JTextArea ticketArea;
    private JTextField searchField;

    // -------------------- UI Constants --------------------------

    private final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 25);
    private final Font CARD_TITLE_FONT = new Font("SansSerif", Font.BOLD, 13);
    private final Font CARD_VALUE_FONT = new Font("SansSerif", Font.BOLD, 25);
    private final Font NORMAL_FONT = new Font("SansSerif", Font.PLAIN, 14);

    // -------------------- Constructor ----------------------------

    public ParkingLotGUI()
    {
        // Get the Singleton ParkingLot object.
        parkingLot = ParkingLot.getInstance();

        // Configure the same parking lot as your original project.
        configureParkingLot();

        // Create the gates used by the system.
        entryGate = new EntryGate(1);
        exitGate = new ExitGate(1);
    }

    /*
     * Creates floors, spots and observers only once.
     *
     * Since ParkingLot is Singleton, this method checks whether
     * floors are already present before adding them.
     */
    private void configureParkingLot()
    {
        parkingLot.setParkingLotName("Marvellous Park Engine");

        if (!parkingLot.getFloor().isEmpty())
        {
            return;
        }

        // -------------------- Floor 1 ----------------------------

        ParkingFloor floor1 = new ParkingFloor(1);

        floor1.addParkingSpot(new BikeSpot(101));
        floor1.addParkingSpot(new BikeSpot(102));
        floor1.addParkingSpot(new CarSpot(103));
        floor1.addParkingSpot(new TruckSpot(104));

        // -------------------- Floor 2 ----------------------------

        ParkingFloor floor2 = new ParkingFloor(2);

        floor2.addParkingSpot(new BikeSpot(201));
        floor2.addParkingSpot(new BikeSpot(202));
        floor2.addParkingSpot(new CarSpot(203));
        floor2.addParkingSpot(new TruckSpot(204));

        // -------------------- Observer ---------------------------

        ParkingDisplayBoard board1 = new ParkingDisplayBoard(floor1);
        ParkingDisplayBoard board2 = new ParkingDisplayBoard(floor2);

        floor1.Observer(board1);
        floor2.Observer(board2);

        // -------------------- Add floors -------------------------

        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);
    }

    // ==============================================================
    // MAIN WINDOW
    // ==============================================================

    public void showGUI()
    {
        frame = new JFrame("Marvellous Park Engine - Parking Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1250, 780);
        frame.setMinimumSize(new Dimension(1000, 650));
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout());

        frame.add(createHeader(), BorderLayout.NORTH);
        frame.add(createSidebar(), BorderLayout.WEST);
        frame.add(createDashboard(), BorderLayout.CENTER);

        refreshDashboard();

        frame.setVisible(true);
    }

    // ==============================================================
    // HEADER
    // ==============================================================

    private JPanel createHeader()
    {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(15, 25, 15, 25));
        header.setBackground(new Color(25, 35, 55));

        JLabel title = new JLabel("MARVELLOUS PARK ENGINE");
        title.setFont(TITLE_FONT);
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Smart Parking Lot Management System");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(new Color(190, 205, 225));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(title);
        text.add(Box.createVerticalStrut(4));
        text.add(subtitle);

        JLabel status = new JLabel("● SYSTEM ONLINE");
        status.setFont(new Font("SansSerif", Font.BOLD, 13));
        status.setForeground(new Color(100, 220, 150));

        header.add(text, BorderLayout.WEST);
        header.add(status, BorderLayout.EAST);

        return header;
    }

    // ==============================================================
    // SIDEBAR
    // ==============================================================

    private JPanel createSidebar()
    {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBackground(new Color(35, 47, 68));
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel menuTitle = new JLabel("OPERATIONS");
        menuTitle.setForeground(new Color(170, 185, 205));
        menuTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        menuTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(menuTitle);
        sidebar.add(Box.createVerticalStrut(15));

        sidebar.add(createMenuButton("  +  Park Vehicle", e -> showParkDialog()));
        sidebar.add(Box.createVerticalStrut(8));

        sidebar.add(createMenuButton("  ⇥  Exit Vehicle", e -> showExitDialog()));
        sidebar.add(Box.createVerticalStrut(8));

        sidebar.add(createMenuButton("  ⌕  Search Vehicle", e -> showSearchDialog()));
        sidebar.add(Box.createVerticalStrut(8));

        sidebar.add(createMenuButton("  ▦  Parking Status", e -> showParkingStatus()));
        sidebar.add(Box.createVerticalStrut(8));

        sidebar.add(createMenuButton("  ↻  Refresh", e -> refreshDashboard()));

        sidebar.add(Box.createVerticalGlue());

        JButton exitButton = createMenuButton("  X  Close Application", e -> {
            int result = JOptionPane.showConfirmDialog(
                    frame,
                    "Do you want to close the Parking Management System?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });

        sidebar.add(exitButton);

        return sidebar;
    }

    private JButton createMenuButton(String text, ActionListener action)
    {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(48, 63, 88));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(13, 12, 13, 12));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(action);

        return button;
    }

    // ==============================================================
    // DASHBOARD
    // ==============================================================

    private JPanel createDashboard()
    {
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(new Color(242, 245, 249));
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top statistics cards.
        JPanel cards = new JPanel(new GridLayout(1, 4, 12, 0));
        cards.setOpaque(false);

        cards.add(createStatCard("TOTAL SPOTS", "0", new Color(48, 63, 88)));
        cards.add(createStatCard("AVAILABLE", "0", new Color(35, 135, 90)));
        cards.add(createStatCard("OCCUPIED", "0", new Color(210, 90, 70)));
        cards.add(createStatCard("VEHICLE TYPES", "3", new Color(100, 75, 160)));

        JPanel center = new JPanel(new BorderLayout(12, 12));
        center.setOpaque(false);

        // Floor status panel.
        JPanel floorPanel = new JPanel(new BorderLayout());
        floorPanel.setBackground(Color.WHITE);
        floorPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 225, 232)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel floorTitle = new JLabel("Parking Floor Status");
        floorTitle.setFont(new Font("SansSerif", Font.BOLD, 18));

        floorContainer = new JPanel();
        floorContainer.setOpaque(false);
        floorContainer.setLayout(new BoxLayout(floorContainer, BoxLayout.Y_AXIS));

        JScrollPane floorScroll = new JScrollPane(floorContainer);
        floorScroll.setBorder(null);
        floorScroll.getVerticalScrollBar().setUnitIncrement(12);

        floorPanel.add(floorTitle, BorderLayout.NORTH);
        floorPanel.add(floorScroll, BorderLayout.CENTER);

        // Right ticket panel.
        JPanel ticketPanel = new JPanel(new BorderLayout(8, 8));
        ticketPanel.setPreferredSize(new Dimension(330, 0));
        ticketPanel.setBackground(Color.WHITE);
        ticketPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 225, 232)),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel ticketTitle = new JLabel("Latest Ticket / Information");
        ticketTitle.setFont(new Font("SansSerif", Font.BOLD, 18));

        ticketArea = new JTextArea();
        ticketArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        ticketArea.setEditable(false);
        ticketArea.setLineWrap(true);
        ticketArea.setWrapStyleWord(true);
        ticketArea.setBackground(new Color(248, 249, 251));
        ticketArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        ticketPanel.add(ticketTitle, BorderLayout.NORTH);
        ticketPanel.add(new JScrollPane(ticketArea), BorderLayout.CENTER);

        center.add(floorPanel, BorderLayout.CENTER);
        center.add(ticketPanel, BorderLayout.EAST);

        main.add(cards, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);

        return main;
    }

    private JPanel createStatCard(String title, String value, Color accent)
    {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 225, 232)),
                new EmptyBorder(12, 15, 12, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(CARD_TITLE_FONT);
        titleLabel.setForeground(new Color(110, 120, 135));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(CARD_VALUE_FONT);
        valueLabel.setForeground(accent);

        if (title.equals("TOTAL SPOTS"))
            totalLabel = valueLabel;
        else if (title.equals("AVAILABLE"))
            availableLabel = valueLabel;
        else if (title.equals("OCCUPIED"))
            occupiedLabel = valueLabel;

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(valueLabel);

        return card;
    }

    // ==============================================================
    // REFRESH DASHBOARD
    // ==============================================================

    private void refreshDashboard()
    {
        int total = 0;
        int available = 0;
        int bike = 0;
        int car = 0;
        int truck = 0;

        for (ParkingFloor floor : parkingLot.getFloor())
        {
            for (SoptType type : SoptType.values())
            {
                int count = floor.getAvalibleCount(type);
                available += count;

                if (type == SoptType.BIKE)
                    bike += count;
                else if (type == SoptType.CAR)
                    car += count;
                else if (type == SoptType.TRUCK)
                    truck += count;
            }
        }

        // Your project currently has 4 spots per floor.
        total = parkingLot.getFloor().size() * 4;

        int occupied = total - available;

        if (totalLabel != null)
            totalLabel.setText(String.valueOf(total));

        if (availableLabel != null)
            availableLabel.setText(String.valueOf(available));

        if (occupiedLabel != null)
            occupiedLabel.setText(String.valueOf(occupied));

        rebuildFloorPanel();
    }

    // ==============================================================
    // FLOOR VISUALIZATION
    // ==============================================================

    private void rebuildFloorPanel()
    {
        floorContainer.removeAll();

        for (ParkingFloor floor : parkingLot.getFloor())
        {
            JPanel floorPanel = new JPanel(new BorderLayout(10, 8));
            floorPanel.setBackground(new Color(248, 249, 251));
            floorPanel.setBorder(new CompoundBorder(
                    new LineBorder(new Color(225, 230, 237)),
                    new EmptyBorder(10, 10, 10, 10)
            ));
            floorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));

            JLabel floorName = new JLabel("FLOOR " + floor.getFloorNumber());
            floorName.setFont(new Font("SansSerif", Font.BOLD, 16));

            JPanel countPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 2));
            countPanel.setOpaque(false);

            countPanel.add(createCountLabel("Bike: " +
                    floor.getAvalibleCount(SoptType.BIKE)));
            countPanel.add(createCountLabel("Car: " +
                    floor.getAvalibleCount(SoptType.CAR)));
            countPanel.add(createCountLabel("Truck: " +
                    floor.getAvalibleCount(SoptType.TRUCK)));

            JPanel top = new JPanel(new BorderLayout());
            top.setOpaque(false);
            top.add(floorName, BorderLayout.WEST);
            top.add(countPanel, BorderLayout.EAST);

            JPanel spots = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
            spots.setOpaque(false);

            // Read every real ParkingSpot from the floor.
            // This makes the GUI show the actual state of the system.
            for (ParkingSpot spot : floor.getParkingSpots())
            {
                addSpotButton(spots, spot);
            }

            floorPanel.add(top, BorderLayout.NORTH);
            floorPanel.add(spots, BorderLayout.CENTER);

            floorContainer.add(floorPanel);
            floorContainer.add(Box.createVerticalStrut(10));
        }

        floorContainer.revalidate();
        floorContainer.repaint();
    }

    private JLabel createCountLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(new Color(80, 90, 105));
        return label;
    }

    private void addSpotButton(JPanel panel, ParkingSpot spot)
    {
        String state = spot.isOccupied() ? "OCCUPIED" : "FREE";

        JButton spotButton = new JButton(
                "P" + spot.getSpotNumber() + "  " + state
        );

        spotButton.setFont(new Font("SansSerif", Font.BOLD, 11));
        spotButton.setFocusPainted(false);
        spotButton.setPreferredSize(new Dimension(120, 38));
        spotButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Green = free, red = occupied.
        if (spot.isOccupied())
        {
            spotButton.setBackground(new Color(220, 85, 70));
            spotButton.setForeground(Color.WHITE);
        }
        else
        {
            spotButton.setBackground(new Color(65, 180, 120));
            spotButton.setForeground(Color.WHITE);
        }

        // Show useful information when the user clicks a spot.
        spotButton.addActionListener(e -> {
            String message =
                    "Spot Number : " + spot.getSpotNumber() + "\n" +
                    "Spot Type   : " + spot.getSoptType() + "\n" +
                    "Status      : " + (spot.isOccupied() ? "OCCUPIED" : "FREE");

            if (spot.isOccupied())
            {
                message += "\nVehicle     : " +
                        spot.getVehical().getVehicalNumber();
            }

            JOptionPane.showMessageDialog(
                    frame,
                    message,
                    "Parking Spot Information",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        panel.add(spotButton);
    }

    // PARK VEHICLE
    // ==============================================================

    private void showParkDialog()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 5, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> typeBox =
                new JComboBox<>(new String[]{"Bike", "Car", "Truck"});

        JTextField numberField = new JTextField(15);

        addFormRow(panel, gbc, 0, "Vehicle Type:", typeBox);
        addFormRow(panel, gbc, 1, "Vehicle Number:", numberField);

        int result = JOptionPane.showConfirmDialog(
                frame,
                panel,
                "Park New Vehicle",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION)
            return;

        String number = numberField.getText().trim().toUpperCase();

        if (number.isEmpty())
        {
            showError("Please enter the vehicle number.");
            return;
        }

        try
        {
            VehicalType type;

            switch (typeBox.getSelectedIndex())
            {
                case 0:
                    type = VehicalType.BIKE;
                    break;

                case 1:
                    type = VehicalType.CAR;
                    break;

                default:
                    type = VehicalType.TRUCK;
            }

            // Factory Pattern:
            // The GUI does not directly create Bike/Car/Truck.
            Vehical vehicle = VehicalFactory.creatVehical(type, number);

            // ParkingLot performs duplicate checking, spot selection,
            // spot allocation and ticket generation.
            ParkingTicket ticket =
                    parkingLot.parkVehical(vehicle, entryGate);

            ParkingTicketRegistry.add(ticket);
            showTicket(ticket, "VEHICLE PARKED SUCCESSFULLY");

            refreshDashboard();
        }
        catch (Exception e)
        {
            showError(e.getMessage());
        }
    }

    // ==============================================================
    // EXIT VEHICLE
    // ==============================================================

    private void showExitDialog()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 5, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField ticketField = new JTextField(15);
        JComboBox<String> paymentBox =
                new JComboBox<>(new String[]{"Cash", "UPI", "Card"});

        addFormRow(panel, gbc, 0, "Ticket Number:", ticketField);
        addFormRow(panel, gbc, 1, "Payment Method:", paymentBox);

        int result = JOptionPane.showConfirmDialog(
                frame,
                panel,
                "Vehicle Exit & Payment",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION)
            return;

        try
        {
            int ticketNumber = Integer.parseInt(ticketField.getText().trim());

            ParkingTicket ticket = findTicket(ticketNumber);

            if (ticket == null)
            {
                showError("Ticket " + ticketNumber + " was not found.");
                return;
            }

            // Calculate the amount before the ticket is removed.
            long hours = ticket.calculateHours();

            pricingStrategy pricing = new NormalPriceStartegy();
            double amount =
                    pricing.calculatePrice(ticket.getVehical(), hours);

            PaymentStrategy payment;

            switch (paymentBox.getSelectedIndex())
            {
                case 0:
                    payment = new CashPayment();
                    break;

                case 1:
                    payment = new UPIpayment();
                    break;

                default:
                    payment = new Cardpayment();
            }

            parkingLot.removeVehical(ticketNumber, exitGate, payment);
            ParkingTicketRegistry.remove(ticketNumber);

            String receipt =
                    "PAYMENT SUCCESSFUL\n\n" +
                    "Ticket No : " + ticketNumber + "\n" +
                    "Vehicle   : " + ticket.getVehical().getVehicalNumber() + "\n" +
                    "Type      : " + ticket.getVehical().getVehicalType() + "\n" +
                    "Duration  : " + hours + " hour(s)\n" +
                    "Amount    : ₹" + String.format("%.2f", amount) + "\n" +
                    "Payment   : " + paymentBox.getSelectedItem() + "\n\n" +
                    "Thank you for using Marvellous Park Engine.";

            ticketArea.setText(receipt);

            JOptionPane.showMessageDialog(
                    frame,
                    receipt,
                    "Payment Receipt",
                    JOptionPane.INFORMATION_MESSAGE
            );

            refreshDashboard();
        }
        catch (NumberFormatException e)
        {
            showError("Ticket number must be a valid number.");
        }
        catch (Exception e)
        {
            showError(e.getMessage());
        }
    }

    // ==============================================================
    // SEARCH VEHICLE
    // ==============================================================

    private void showSearchDialog()
    {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        JLabel label = new JLabel("Enter Vehicle Number:");
        searchField = new JTextField(18);

        panel.add(label, BorderLayout.WEST);
        panel.add(searchField, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                frame,
                panel,
                "Search Vehicle",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION)
            return;

        String number = searchField.getText().trim().toUpperCase();

        if (number.isEmpty())
        {
            showError("Please enter a vehicle number.");
            return;
        }

        ParkingTicket ticket = parkingLot.searchVehical(number);

        if (ticket == null)
        {
            ticketArea.setText(
                    "SEARCH RESULT\n\n" +
                    "Vehicle: " + number + "\n\n" +
                    "STATUS: NOT PARKED"
            );

            JOptionPane.showMessageDialog(
                    frame,
                    "Vehicle " + number + " is not currently parked.",
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
        else
        {
            showTicket(ticket, "VEHICLE FOUND");
        }
    }

    // ==============================================================
    // PARKING STATUS
    // ==============================================================

    private void showParkingStatus()
    {
        StringBuilder status = new StringBuilder();

        status.append("MARVELLOUS PARK ENGINE\n");
        status.append("=======================\n\n");

        for (ParkingFloor floor : parkingLot.getFloor())
        {
            status.append("FLOOR ").append(floor.getFloorNumber()).append("\n");
            status.append("-----------------------\n");

            status.append("Bike Available : ")
                    .append(floor.getAvalibleCount(SoptType.BIKE))
                    .append("\n");

            status.append("Car Available  : ")
                    .append(floor.getAvalibleCount(SoptType.CAR))
                    .append("\n");

            status.append("Truck Available: ")
                    .append(floor.getAvalibleCount(SoptType.TRUCK))
                    .append("\n\n");
        }

        ticketArea.setText(status.toString());

        JOptionPane.showMessageDialog(
                frame,
                new JScrollPane(createTextArea(status.toString())),
                "Parking Status",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==============================================================
    // TICKET HELPERS
    // ==============================================================

    private ParkingTicket findTicket(int ticketNumber)
    {
        // The original ParkingLot class intentionally keeps the
        // activeTicket map private. We therefore search using the
        // configured active vehicle records through the floors.
        for (ParkingFloor floor : parkingLot.getFloor())
        {
            for (SoptType type : SoptType.values())
            {
                // Ticket lookup is handled by a small temporary
                // search through known vehicle numbers in this GUI.
            }
        }

        // Since the current domain class does not expose active tickets
        // by ticket number, we ask the user to search by vehicle if
        // direct ticket lookup is unavailable.
        //
        // To keep the GUI fully functional, use the helper registry.
        return ParkingTicketRegistry.get(ticketNumber);
    }

    private void showTicket(ParkingTicket ticket, String title)
    {
        String information =
                title + "\n\n" +
                "Ticket Number : " + ticket.getTicketNumber() + "\n" +
                "Vehicle       : " + ticket.getVehical().getVehicalNumber() + "\n" +
                "Vehicle Type  : " + ticket.getVehical().getVehicalType() + "\n" +
                "Floor         : " + ticket.getFloor().getFloorNumber() + "\n" +
                "Spot          : " + ticket.getSpot().getSpotNumber() + "\n" +
                "Entry Time    : " + ticket.getEntry() + "\n" +
                "Status        : " + ticket.getStatus();

        ticketArea.setText(information);

        JOptionPane.showMessageDialog(
                frame,
                information,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ==============================================================
    // UI UTILITY METHODS
    // ==============================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent component)
    {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(component, gbc);
    }

    private JTextArea createTextArea(String text)
    {
        JTextArea area = new JTextArea(text, 15, 40);
        area.setEditable(false);
        area.setFont(NORMAL_FONT);
        area.setBorder(new EmptyBorder(10, 10, 10, 10));
        return area;
    }

    private void showError(String message)
    {
        if (message == null || message.trim().isEmpty())
            message = "An unexpected error occurred.";

        JOptionPane.showMessageDialog(
                frame,
                message,
                "Operation Failed",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

/*
 * ================================================================
 * SMALL GUI REGISTRY
 * ================================================================
 *
 * Your original ParkingLot class stores active tickets internally.
 * This registry allows the GUI to remember tickets generated by
 * the GUI without changing the Singleton design.
 */
class ParkingTicketRegistry
{
    private static final Map<Integer, ParkingTicket> tickets =
            new HashMap<>();

    public static void add(ParkingTicket ticket)
    {
        if (ticket != null)
            tickets.put(ticket.getTicketNumber(), ticket);
    }

    public static ParkingTicket get(int ticketNumber)
    {
        return tickets.get(ticketNumber);
    }

    public static void remove(int ticketNumber)
    {
        tickets.remove(ticketNumber);
    }
}
