package PatientManagement.Clinic;

import PatientManagement.Catalogs.AgeGroup;
import PatientManagement.Catalogs.VitalSignLimits;
import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Patient.Patient;
import PatientManagement.Persona.Person;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PatientDirectory {
    Clinic clinic;
    ArrayList<Patient> patients;

    PatientDirectory(Clinic clinic) {
        this.clinic = clinic;
        patients = new ArrayList<Patient>();
    }

    public int getConfirmedPositiveTotals() {
        int sum = 0;

        for (Patient p : patients) {
            if (p.isConfirmedPositive()) {
                sum = sum + 1;
            }
        }
        return sum;
    }

    public ArrayList<Patient> getAllConfirmedPositives() {
        ArrayList<Patient> temp = new ArrayList<Patient>();
        for (Patient p : patients) {
            if (p.isConfirmedPositive() == true) {
                temp.add(p);
            }
        }
        return temp; // has the list of encounters with confirmed diagnosis
    }

    public Patient newPatient(Person person) {
        Patient patient = new Patient(person, clinic);
        patients.add(patient);
        return patient;
    }

    
    public ArrayList<Patient> getPatientList() {
        return patients;
    }

    public void printPatients() {
        for (Patient patient : patients) {
            System.out.println("ID: " + patient.getPerson().getPersonId() + ", Name: " + patient.getPerson().getName() + ", Age: " + patient.getPerson().getAge());
        }
    }
  

}