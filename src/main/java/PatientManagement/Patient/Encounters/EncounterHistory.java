package PatientManagement.Patient.Encounters;

import PatientManagement.Clinic.Event;
import PatientManagement.Patient.Patient;
import java.util.ArrayList;

public class EncounterHistory {
    ArrayList<Encounter> encounters;
    Patient patient;

    public EncounterHistory(Patient p) {
        patient = p;
        encounters = new ArrayList<Encounter>();
    }

    // each encounter must link to the event at the site
    public Encounter newEncounter(String chiefcomplaint, Event ev) {
        Encounter e = new Encounter(patient, chiefcomplaint, ev, this);
        encounters.add(e);
        return e;
    }

    public ArrayList<Encounter> getEncounterList() {
        return encounters;
    }

    public Patient getPatient() {
        return patient;
    }

}
