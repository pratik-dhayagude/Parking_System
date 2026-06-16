import java.time.*;
import java.time.temporal.ChronoUnit;

public class main
{
    public static void main(String A[]) throws Exception
    {
        ParkingLot lot = new ParkingLot(5);

        Vehical Car = new Car("MH12AB1234");

        Ticket ticket = lot.parkVehicle(Car);

        Thread.sleep(5000);

        LocalDateTime exitTime = LocalDateTime.now();

        long hours = ChronoUnit.HOURS.between(  ticket.getEntryTime(),exitTime);
    
        if(hours == 0)
        {
            hours = 1;
        }

        double amount =Bill.calculateBill(  Car.getVehicleType(),hours);
      
        System.out.println("\n===== BILL =====");

        System.out.println("Vehicle:"+ Car.getVehicleNumber());

        System.out.println("Hours:"+ hours);

        System.out.println("Amount:"+"$"+ amount);

        lot.releaseSpot(ticket.getSpotNumber());
    }
}