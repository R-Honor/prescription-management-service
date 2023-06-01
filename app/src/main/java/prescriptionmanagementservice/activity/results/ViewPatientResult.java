package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PatientModel;

public class ViewPatientResult {
    private final PatientModel patientModel;

    public ViewPatientResult(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

    public PatientModel getPatient() {
        return patientModel;
    }

    @Override
    public String toString() {
        return "ViewPatientResult{" +
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

        public ViewPatientResult build() {
            return new ViewPatientResult(patientModel);
        }
    }
}
