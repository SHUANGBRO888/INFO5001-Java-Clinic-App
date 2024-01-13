import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import PatientManagement.Catalogs.AgeGroup;
import PatientManagement.Catalogs.Limits;
import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.Event;
import PatientManagement.Clinic.EventSchedule;
import PatientManagement.Clinic.Location;
import PatientManagement.Clinic.LocationList;
import PatientManagement.Clinic.PatientDirectory;
import PatientManagement.Clinic.Site;
import PatientManagement.Clinic.SiteCatalog;
import PatientManagement.Patient.Patient;
import PatientManagement.Patient.Encounters.Diagnosis;
import PatientManagement.Patient.Encounters.Encounter;
import PatientManagement.Persona.Person;

public class HospitalReports {
    
    public static void generateReportOnInfections(PatientDirectory patientDirectory, Event curryPatientScreening, Event healthServicesPatientScreening) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the current month: ");
        String month = scanner.nextLine();
    
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\nReport of Infection");
        System.out.println("\nMonth: \t" + month + "\t\tNumber of Patient\tTotal Confirmed Diagnose\tInfections\t\tPercentage of Infection");
        System.out.println("\nGreater Boston Area");
    
        int curryCenterInfections = 0;
        int healthCenterInfections = 0;
       
        int curryCenterDiagnoses = 0;
        int healthCenterDiagnoses = 0;
       
        int curryCenterPatients = 0;
        int healthServicesPatients = 0;
    
        for (Patient patient : patientDirectory.getPatientList()) {
            Encounter firstEncounter = patient.getEncounterHistory().getEncounterList().get(0);
            Diagnosis firstDiagnosis = firstEncounter.getDiagnosis(); 
            Event patientEvent = firstEncounter.getEvent();
    
            if (patientEvent.equals(curryPatientScreening)) {
                curryCenterPatients++;
            } else if (patientEvent.equals(healthServicesPatientScreening)) {
                healthServicesPatients++;
            }
    
            if (firstDiagnosis.getCategory().equalsIgnoreCase("Further Check")) {
                if (patientEvent.equals(curryPatientScreening)) {
                    curryCenterDiagnoses++;
    
                    Encounter secondEncounter = patient.getEncounterHistory().getEncounterList().get(1);
                    Diagnosis secondDiagnosis = secondEncounter.getDiagnosis();
    
                    if (!secondDiagnosis.getCategory().equalsIgnoreCase("No Infection")) {
                        curryCenterInfections++;
                    }
                } else if (patientEvent.equals(healthServicesPatientScreening)) {
                    healthCenterDiagnoses++;
    
                    Encounter secondEncounter = patient.getEncounterHistory().getEncounterList().get(1);
                    Diagnosis secondDiagnosis = secondEncounter.getDiagnosis();
    
                    if (!secondDiagnosis.getCategory().equalsIgnoreCase("No Infection")) {
                        healthCenterInfections++;
                    }
                }
            }
        }
    
        double curryCenterInfectionPercentage = (double) curryCenterInfections / curryCenterPatients * 100;
        double healthCenterInfectionPercentage = (double) healthCenterInfections / healthServicesPatients * 100;
    
        System.out.print("Curry Center");
        System.out.println("\t\t" + curryCenterPatients + "\t\t\t" + curryCenterDiagnoses + "\t\t\t\t" + curryCenterInfections + "\t\t\t" + String.format("%.2f", curryCenterInfectionPercentage) + "%");
        System.out.print("Health Center");
        System.out.println("\t\t" + healthServicesPatients + "\t\t\t" + healthCenterDiagnoses + "\t\t\t\t" + healthCenterInfections + "\t\t\t" + String.format("%.2f", healthCenterInfectionPercentage) + "%");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        printInfectionTrend(curryCenterInfections, healthCenterInfections, curryCenterInfectionPercentage, healthCenterInfectionPercentage, month);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void printInfectionTrend(int curryCenterInfections, int healthCenterInfections, double curryCenterInfectionPercentage, double healthCenterInfectionPercentage, String currentMonth) {
        System.out.println("\nInfection Trend:");
    
        double curryGrowthRate = curryCenterInfectionPercentage > 50 ? 1.1 : 0.9;
        double healthGrowthRate = healthCenterInfectionPercentage > 50 ? 1.1 : 0.9;
    
        String[] nextMonths = new String[4];
        nextMonths[0] = currentMonth;
        String[] followingMonths = getNextThreeMonths(currentMonth);
        System.arraycopy(followingMonths, 0, nextMonths, 1, followingMonths.length);
    
        System.out.print("Month\t\t");
        for (String month : nextMonths) {
            System.out.print(month + "\t");
        }
        System.out.println();
    
        System.out.print("Curry Center\t");
        for (int i = 0; i < nextMonths.length; i++) {
            if (i > 0) {
                curryCenterInfections = (int) (curryCenterInfections * curryGrowthRate);
            }
            System.out.print(curryCenterInfections + "\t");
        }
        System.out.println();
    
        System.out.print("Health Center\t");
        for (int i = 0; i < nextMonths.length; i++) {
            if (i > 0) {
                healthCenterInfections = (int) (healthCenterInfections * healthGrowthRate);
            }
            System.out.print(healthCenterInfections + "\t");
        }
        System.out.println();
    }
    

    public static String[] getNextThreeMonths(String currentMonth) {
        String[] allMonths = {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
        
        int currentMonthIndex = -1;
    
        for (int i = 0; i < allMonths.length; i++) {
            if (allMonths[i].equalsIgnoreCase(currentMonth)) {
                currentMonthIndex = i;
                break;
            }
        }
    
        if (currentMonthIndex == -1) {
            System.out.println("Invalid month input. Please try again.");
            return new String[0];
        }
    
        String[] nextThreeMonths = new String[3];
    
        for (int i = 1; i <= 3; i++) {
            int nextMonthIndex = (currentMonthIndex + i) % 12;
            nextThreeMonths[i - 1] = allMonths[nextMonthIndex];
        }
    
        return nextThreeMonths;
    }

    public static Object[] initializeLocationsSitesAndEvents(Clinic clinic, String locationName, String siteName1, String siteName2, String eventName1, String eventName2) {
        LocationList locations = clinic.getLocationList();
        SiteCatalog sites = clinic.getSiteCatalog();
        EventSchedule eventSchedule = clinic.getEventSchedule();
    
        // Add location
        Location customLocation = locations.newLocation(locationName);
    
        // Add sites
        Site customSite1 = sites.newSite(customLocation);
        Site customSite2 = sites.newSite(customLocation);
    
        // Set site names
        customSite1.setName(siteName1);
        customSite2.setName(siteName2);
    
        // Create events
        Event event1 = eventSchedule.newEvent(customSite1, "0",eventName1);
        Event event2 = eventSchedule.newEvent(customSite2, "0",eventName2);
       
        return new Object[] {customSite1, customSite2, event1, event2};
    }
        
    public static void printAbnormalVitalSigns(PatientDirectory patientDirectory, VitalSignsCatalog vitalSignsCatalog) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\nAbnormal vital signs report:");
        System.out.println("NO. \tID\tName\t\t\t\tAge\tBP\tHR\tAbnormal");
    
        int index = 1;
        for (Patient patient : patientDirectory.getPatientList()) {
            Person person = patient.getPerson();
            int age = person.getAge();
            AgeGroup ageGroup = vitalSignsCatalog.findAgeGroup(age);
    
            int randomBP = ThreadLocalRandom.current().nextInt(60, 181);
            int randomHR = ThreadLocalRandom.current().nextInt(40, 151);
    
            boolean bpAbnormal = false;
            boolean hrAbnormal = false;
    
            if (ageGroup != null) {
                Limits bpLimits = vitalSignsCatalog.findVitalSignLimits(age, "BP");
                Limits hrLimits = vitalSignsCatalog.findVitalSignLimits(age, "HR");
    
                if (bpLimits != null && hrLimits != null) {
                    bpAbnormal = randomBP < bpLimits.getLowerLimit() || randomBP > bpLimits.getUpperLimit();
                    hrAbnormal = randomHR < hrLimits.getLowerLimit() || randomHR > hrLimits.getUpperLimit();
                }
            }
    
            if (bpAbnormal || hrAbnormal) {
                System.out.print(index + "\t" + person.getPersonId() + "\t" + String.format("%-30s", person.getName()) + "\t" + age + "\t" + randomBP + "\t" + randomHR);
    
                ArrayList<String> abnormalList = new ArrayList<>();
                if (bpAbnormal) {
                    abnormalList.add("BP");
                }
                if (hrAbnormal) {
                    abnormalList.add("HR");
                }
    
                System.out.println("\t" + String.join(", ", abnormalList));
                index++;
            }
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }
    
    
    public static void printPatientsWithDisease(String disease, Clinic clinic) {
        PatientDirectory patientDirectory = clinic.getPatientDirectory();
        ArrayList<Patient> patients = patientDirectory.getPatientList();
    
        System.out.println("Patients with " + disease + ":");
        System.out.printf("%-5s%-10s%-25s%-5s%n", "NO.", "ID", "Name", "Age");
        int index = 1;
        for (Patient patient : patients) {
            if (patient.hasDisease(disease)) {
                System.out.printf("%-5d%-10s%-25s%-5d%n", index, patient.getPerson().getPersonId(), patient.getPerson().getName(), patient.getPerson().getAge());
                index++;
            }
        }
    }
    
}
