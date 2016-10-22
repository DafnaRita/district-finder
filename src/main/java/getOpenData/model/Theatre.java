package getOpenData.model;


public class Theatre {
    private String name;
    private String address;
    private int coordinates;

    public Theatre() {
    }

    public Theatre(String name, String address, int coordinates) {

        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int coordinates) {
        this.coordinates = coordinates;
    }
}
