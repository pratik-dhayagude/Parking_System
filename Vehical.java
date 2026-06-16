abstract class Vehical
{
    protected String vehicleNumber;
    protected String vehicleType;
    public Vehical(String vehicleNumber,String vehicleType)
    {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }
    public String getVehicleNumber()
    {
        return vehicleNumber;
    }
    public String getVehicleType()
    {
        return vehicleType;
    }
}