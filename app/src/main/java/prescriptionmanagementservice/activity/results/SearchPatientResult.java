package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PatientModel;

import java.util.ArrayList;
import java.util.List;

public class SearchPatientResult {

    private final List<PatientModel> patients;

    public SearchPatientResult(List<PatientModel> patients) {
        this.patients = patients;
    }

    public List<PatientModel> getPatients() {
        return patients;
    }

    @Override
    public String toString() {
        return "SearchPatientResult{" +
                "patients=" + patients +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<PatientModel> patients;

        public Builder withPatients(List<PatientModel> patients) {
            this.patients = new ArrayList<>(patients);
            return this;
        }

        public SearchPatientResult build() {
            return new SearchPatientResult(patients);
        }
    }
}
