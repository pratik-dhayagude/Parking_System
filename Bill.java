class Bill
{
    public static double calculateBill(String vehicleType,long hours)
    {
        double rate = 0;

        if(vehicleType.equals("Bike"))
        {
            rate = 20;
        }
        else if(vehicleType.equals("Car"))
        {
            rate = 50;
        }
        else if(vehicleType.equals("Truck"))
        {
            rate = 100;
        }

        return rate * hours;
    }
}