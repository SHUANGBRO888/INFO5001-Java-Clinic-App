package PatientManagement.Clinic;

public class Site {

    Location location;
    String name;

    public Site(Location loc) {
        location = loc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

}
