package PatientManagement.Clinic;

import PatientManagement.Catalogs.AgeGroup;
import PatientManagement.Catalogs.DrugCatalog;
import PatientManagement.Catalogs.VitalSignLimits;
import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Persona.PersonDirectory;

public class Clinic {
    PatientDirectory patientdirectory;
    PersonDirectory persondirectory;
    SiteCatalog sitelist;
    LocationList locationlist;
    DrugCatalog drugcatalog;
    EventSchedule eventschedule;
    VitalSignsCatalog vitalSignsCatalog;
    String name; // name of the clinic

    public Clinic(String n) {
        eventschedule = new EventSchedule();
        sitelist = new SiteCatalog();
        locationlist = new LocationList();
        persondirectory = new PersonDirectory();
        patientdirectory = new PatientDirectory(this);
        vitalSignsCatalog = new VitalSignsCatalog();
        name = n;
    }

    public SiteCatalog getSiteCatalog() {
        return sitelist;
    }

    public LocationList getLocationList() {
        return locationlist;
    }

    public Site newSite(Location location) {

        Site s = sitelist.newSite(location);
        return s;
    }

    public VitalSignsCatalog getVitalSignsCatalog() {
        return vitalSignsCatalog;
    }

    public void setupVitalSignsCatalog() {
        VitalSignsCatalog vsc = getVitalSignsCatalog();

        // Define age groups and their ranges
        AgeGroup children = vsc.newAgeGroup("Children", 12, 0);
        AgeGroup adults_21_50 = vsc.newAgeGroup("Adults 21-50", 50, 21);
        AgeGroup seniors = vsc.newAgeGroup("Seniors", 200, 51);

        // Define vital sign limits for each age group
        VitalSignLimits heartRateLimits = vsc.newVitalSignLimits("HR");
        VitalSignLimits bloodPressureLimits = vsc.newVitalSignLimits("BP");

        // Add heart rate limits for age groups
        heartRateLimits.addLimits(children, 120, 70);
        heartRateLimits.addLimits(adults_21_50, 105, 55);
        heartRateLimits.addLimits(seniors, 110, 50);

        // Add blood pressure limits for age groups
        bloodPressureLimits.addLimits(children, 120, 80);
        bloodPressureLimits.addLimits(adults_21_50, 140, 70);
        bloodPressureLimits.addLimits(seniors, 150, 60);
    }

    public PersonDirectory getPersonDirectory() {
        return persondirectory;
    }

    public PatientDirectory getPatientDirectory() {
        return patientdirectory;
    }

    public EventSchedule getEventSchedule() {
        return eventschedule;
    }
}
