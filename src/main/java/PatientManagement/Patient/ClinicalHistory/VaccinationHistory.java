package PatientManagement.Patient.ClinicalHistory;

import java.util.ArrayList;

public class VaccinationHistory {

    ArrayList<Vaccination> vaccinations;

    public void addVaccination(Vaccination v) {
        vaccinations.add(v);
    }

}