import java.time.LocalDateTime;

class Ticket
{
    private static int counter = 1000;

    private int ticketId;
    private String vehicleNumber;
    private int spotNumber;
    private LocalDateTime entryTime;
    public Ticket(String vehicleNumber,int spotNumber)
    {
        ticketId = ++counter;
        this.vehicleNumber = vehicleNumber;
        this.spotNumber = spotNumber;
        entryTime = LocalDateTime.now();
    }
    public int getTicketId()
    {
        return ticketId;
    }
    public String getVehicleNumber()
    {
        return vehicleNumber;
    }
    public int getSpotNumber()
    {
        return spotNumber;
    }
    public LocalDateTime getEntryTime()
    {
        return entryTime;
    }
    public void displayTicket()
    {
        System.out.println("\n===== TICKET =====");
        System.out.println("Ticket ID : " + ticketId);
        System.out.println("Vehicle   : " + vehicleNumber);
        System.out.println("Spot No   : " + spotNumber);
        System.out.println("Entry     : " + entryTime);
    }
}