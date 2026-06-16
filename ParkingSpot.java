class ParkingSpot
{
    private int spotNumber;
    private boolean occupied;

    public ParkingSpot(int spotNumber)
    {
        this.spotNumber = spotNumber;
        occupied = false;
    }

    public boolean isOccupied()
    {
        return occupied;
    }

    public void occupy()
    {
        occupied = true;
    }

    public void freeSpot()
    {
        occupied = false;
    }

    public int getSpotNumber()
    {
        return spotNumber;
    }
}