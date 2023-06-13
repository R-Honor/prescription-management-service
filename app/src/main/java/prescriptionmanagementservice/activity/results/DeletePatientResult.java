package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PatientModel;

public class DeletePatientResult {

    private final PatientModel patientModel;

    public DeletePatientResult(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public PatientModel getPatient() {
        return patientModel;
    }

    @Override
    public String toString() {
        return "DeletePatientResult{" +
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

        public DeletePatientResult build() {
            return new DeletePatientResult(patientModel);
        }
    }
}
