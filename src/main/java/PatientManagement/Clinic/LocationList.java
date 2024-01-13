package PatientManagement.Clinic;

import java.util.ArrayList;

public class LocationList {
    ArrayList<Location> locations;

    public LocationList() {
        locations = new ArrayList<Location>();
    }

    public Location getLocation(String locname) {
        // search for a location object by locname;
        Location l = null;
        return l;
    }

    public Location newLocation(String name) {
        Location newLoc = new Location(name);
        locations.add(newLoc);
        return newLoc;
    }

}
