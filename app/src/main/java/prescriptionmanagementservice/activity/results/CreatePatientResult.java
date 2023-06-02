package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PatientModel;

public class CreatePatientResult {

    private final PatientModel patientModel;

    public CreatePatientResult(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public PatientModel getPatient() {
        return patientModel;
    }

    @Override
    public String toString() {
        return "CreatePatientResult{" +
                "patientModel=" + patientModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PatientModel patientModel;

        public Builder withPatient(PatientModel patientModel) {
            this.patientModel = patientModel;
            return this;
        }

        public CreatePatientResult build() {
            return new CreatePatientResult(patientModel);
        }
    }
}
