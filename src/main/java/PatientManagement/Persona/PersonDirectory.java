package PatientManagement.Persona;

import java.util.ArrayList;
import com.github.javafaker.Faker;

import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.Event;
import PatientManagement.Patient.Patient;
import PatientManagement.Patient.Encounters.Encounter;
import PatientManagement.Patient.Encounters.EncounterHistory;

public class PersonDirectory {
    ArrayList<Person> personlist;
    int personCounter;
    Faker faker;

    public PersonDirectory() {
        personlist = new ArrayList<Person>();
        personCounter = 1;
        faker = new Faker();
    }

    public Person newPerson(String id, String name, int age) {
        Person p = new Person(id, name, age);
        personlist.add(p);
        return p;
    }

    public void generateRandomPersons(int numOfPersons, Clinic clinic, Event event) {
        for (int i = 0; i < numOfPersons; i++) {
            String id = String.format("%03d", personCounter++);
            String randomName = faker.name().fullName();
            int randomAge = faker.number().numberBetween(1, 100);
            Person p = new Person(id, randomName, randomAge);
            personlist.add(p);
    
            // Convert the person to a patient and add to the patient directory
            Patient patient = clinic.getPatientDirectory().newPatient(p);

            // Assign random diagnosis and status to the patient
            patient.assignRandomDiagnosisAndStatus(event);
    
            // Create a random encounter for the patient
            EncounterHistory encounterHistory = patient.getEncounterHistory();
            Encounter encounter = new Encounter(patient, "Checkup", event, encounterHistory);
    
            // Add vital signs to the encounter
            int randomHR = faker.number().numberBetween(40, 150);
            int randomBP = faker.number().numberBetween(60, 180);
            encounter.addNewVitals("HR", randomHR);
            encounter.addNewVitals("BP", randomBP);
        }
    }
    

    public Person findPerson(String id) {

        for (Person p : personlist) {

            if (p.isMatch(id)) {
                return p;
            }
        }
        return null; // not found after going through the whole list
    }

    public void printPersons() {
        for (Person p : personlist) {
            System.out.println("ID: " + p.getPersonId() + ", Name: " + p.getName() + ", Age: " + p.getAge());
        }
    }

   
    public ArrayList<Person> getPersonList() {
        return personlist;
    }
    
}
