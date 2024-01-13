import java.util.Scanner;

import PatientManagement.Catalogs.VitalSignsCatalog;
import PatientManagement.Clinic.Clinic;
import PatientManagement.Clinic.Event;
import PatientManagement.Clinic.PatientDirectory;
import PatientManagement.Persona.PersonDirectory;

public class PatientCareMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Clinic clinic = new Clinic("Northeastern Hospitals");

        // Configuring vital signs catalogN
        VitalSignsCatalog vsc = clinic.getVitalSignsCatalog();
        clinic.setupVitalSignsCatalog();
    
        // Adding a person
        PersonDirectory pd = clinic.getPersonDirectory();
    
        // Creating a patient
        PatientDirectory patientDirectory = clinic.getPatientDirectory();
        HospitalReports hospitalReports = new HospitalReports();

        // Configuring location, sites, and event
        Object[] results = hospitalReports.initializeLocationsSitesAndEvents(clinic, "Greater Boston Area", "Curry Center", "Health Services","curryPatientScreening","healthServicesPatientScreening");
        Event curryPatientScreening = (Event) results[2];
        Event healthServicesPatientScreening = (Event) results[3];

        // Generating Patients
        pd.generateRandomPersons(200, clinic, curryPatientScreening);
        pd.generateRandomPersons(150, clinic, healthServicesPatientScreening);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Northeastern Hospitals App");
            boolean isLoggedIn = login(scanner);
            if (isLoggedIn) {
                // Display menu and allow user to select an option
                while (true) {
                    System.out.println("Welcome to the Northeastern Hospitals app.");
                    System.out.println("Please select an option:");
                    System.out.println("1. Generate Report on Infections");
                    System.out.println("2. Print Abnormal Vital Signs Report");
                    System.out.println("3. Print Patient with Diseaces Report");
                    System.out.println("4. Exit");
    
                    String input = scanner.nextLine();
                    int choice;
    
                    try {
                        choice = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number between 1 and 3.");
                        continue;
                    }
    
                    switch (choice) {
                        case 1:
                            hospitalReports.generateReportOnInfections(patientDirectory, curryPatientScreening, healthServicesPatientScreening);
                            break;
                        case 2:
                            hospitalReports.printAbnormalVitalSigns(patientDirectory, vsc);
                            break;
                        case 3:
                            hospitalReports.printPatientsWithDisease("Covid", clinic);
                            hospitalReports.printPatientsWithDisease("Seasonal Flu", clinic);
                            hospitalReports.printPatientsWithDisease("HIV", clinic);
                            hospitalReports.printPatientsWithDisease("Fever", clinic); 
                            break;
                        case 4:
                            System.out.println("Logging out...");
                            break;
                        default:
                            System.out.println("Invalid input. Please enter a number between 1 and 3.");
                            break;
                    }
                    if (choice == 4) {
                        break; 
                    }
                }
            } else {
                System.out.println("User name or password is wrong. Please try again.");
            }
        }
    }
        
    private static boolean login(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        // Check username and password, return true if valid, false otherwise.
        return username.equals("Neuadmin") && password.equals("password");
    }

    

}
