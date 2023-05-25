package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PrescriptionModel;

public class CreatePrescriptionResult {

    private final PrescriptionModel prescriptionModel;

    public CreatePrescriptionResult(PrescriptionModel prescriptionModel) {
        this.prescriptionModel = prescriptionModel;
    }

    public PrescriptionModel getPrescription() {
        return prescriptionModel;
    }

    @Override
    public String toString() {
        return "CreatePrescriptionResult{" +
                "prescriptionModel=" + prescriptionModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PrescriptionModel prescriptionModel;

        public Builder withPrescription(PrescriptionModel prescriptionModel) {
            this.prescriptionModel = prescriptionModel;
            return this;
        }

        public CreatePrescriptionResult build() {
            return new CreatePrescriptionResult(prescriptionModel);
        }
    }
}
