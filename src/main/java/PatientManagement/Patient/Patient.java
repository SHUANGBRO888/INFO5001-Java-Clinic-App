package PatientManagement.Patient;

import PatientManagement.Catalogs.Limits;
import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.Event;
import PatientManagement.Clinic.EventSchedule;
import PatientManagement.Clinic.PatientDirectory;
import PatientManagement.Patient.ClinicalHistory.AlergyHistory;
import PatientManagement.Patient.ClinicalHistory.VaccinationHistory;
import PatientManagement.Patient.Encounters.Diagnosis;
import PatientManagement.Patient.Encounters.Encounter;
import PatientManagement.Patient.Encounters.EncounterHistory;
import PatientManagement.Patient.Encounters.VitalSigns;
import PatientManagement.Persona.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.javafaker.Faker;

public class Patient {
    Clinic clinic;
    EncounterHistory encounterhistory;
    VaccinationHistory vachistory;
    Person person;
    AlergyHistory alergyhistory;
    VitalSigns vitalSigns;

    public Patient(Person p, Clinic clinic) {
        encounterhistory = new EncounterHistory(this); // link this patient object back
        person = p;
        this.clinic = clinic;
    }

    public EncounterHistory getEncounterHistory() {
        return encounterhistory;
    }
    // the below method will return the encounter where the infection occured
    // from the returned encounter you pull the event, the site, etc.

    public Encounter getConfirmedEncounter() {
        ArrayList<Encounter> patientencounterlist = encounterhistory.getEncounterList();

        for (Encounter currentencounter : patientencounterlist) {
            Diagnosis diag = currentencounter.getDiagnosis();
            if (diag.isConfirmed()) {
                return currentencounter;// send back the whole encounter so we extract event and site
            }
        }
        return null;
    }

    public boolean isConfirmedPositive() {

        ArrayList<Encounter> patientencounterlist = encounterhistory.getEncounterList();

        for (Encounter currentencounter : patientencounterlist) {
            Diagnosis diag = currentencounter.getDiagnosis();
            return diag.isConfirmed();

        }
        return false;
    }

    public Person getPerson() {
        return person;
    }

    public Encounter newEncounter(String chiefcomplaint, Event ev) {
        return encounterhistory.newEncounter(chiefcomplaint, ev);
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void assignRandomDiagnosisAndStatus(Event event) {
        List<String> diseases = Arrays.asList("Covid", "Seasonal Flu", "HIV", "Fever", "No Infection");
        List<String> status = Arrays.asList("Further Check", "Healthy");
        Faker faker = new Faker();
    
        // First diagnosis
        String randomStatus = status.get(faker.random().nextInt(status.size()));
        Encounter firstEncounter = newEncounter("Initial Check", event);
        if (randomStatus.equals("Further Check")) {
            firstEncounter.newDiagnosis("Further Check", true);
    
            // Second diagnosis (specific diagnosis)
            String randomDisease = diseases.get(faker.random().nextInt(diseases.size()));
            Encounter secondEncounter = newEncounter("Second Check", event);
            if (!randomDisease.equals("No Infection")) {
                secondEncounter.newDiagnosis(randomDisease, true);
            } else {
                secondEncounter.newDiagnosis("No Infection", false);
            }
        } else {
            firstEncounter.newDiagnosis("Healthy", false);
        }
    }

    public VitalSigns getVitalSigns() {
        return vitalSigns;
    }

    public boolean hasDisease(String disease) {
        ArrayList<Encounter> patientEncounterList = encounterhistory.getEncounterList();
    
        for (Encounter currentEncounter : patientEncounterList) {
            Diagnosis diag = currentEncounter.getDiagnosis();
            if (diag.getCategory().equals(disease) && diag.isConfirmed()) {
                return true;
            }
        }
        return false;
    }

    
}
