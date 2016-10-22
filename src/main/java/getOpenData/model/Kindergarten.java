package getOpenData.model;

public class Kindergarten {
    private String name;
    private String address;
    private double[] coordinates;

    public Kindergarten() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public Kindergarten(String name, String address, double[] coordinates) {
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;

    }
}
