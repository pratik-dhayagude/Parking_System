import java.util.*;

class ParkingLot
{
    private ParkingSpot spots[];

    public ParkingLot(int totalSpots)
    {
        spots = new ParkingSpot[totalSpots];

        for(int i = 0; i < totalSpots; i++)
        {
            spots[i] = new ParkingSpot(i + 1);
        }
    }

    public Ticket parkVehicle(Vehical vehicle)
    {
        for(ParkingSpot spot : spots)
        {
            if(!spot.isOccupied())
            {
                spot.occupy();

                Ticket ticket =
                new Ticket(vehicle.getVehicleNumber(),
                           spot.getSpotNumber());

                System.out.println("\nVehicle Parked Successfully");

                ticket.displayTicket();

                return ticket;
            }
        }

        System.out.println("Parking Full");
        return null;
    }

    public void releaseSpot(int spotNumber)
    {
        spots[spotNumber - 1].freeSpot();
    }
}