package PatientManagement.Clinic;

import java.util.ArrayList;

public class SiteCatalog {

    ArrayList<Site> sites;

    public SiteCatalog() {
        sites = new ArrayList<Site>();
    }

    public Site newSite(Location l) {
        Site s = new Site(l);
        sites.add(s);
        return s;
    }
}
