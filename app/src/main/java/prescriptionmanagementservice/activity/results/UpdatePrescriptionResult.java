package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PrescriptionModel;

public class UpdatePrescriptionResult {

    private final PrescriptionModel prescriptionModel;

    public UpdatePrescriptionResult(PrescriptionModel prescriptionModel) {
        this.prescriptionModel = prescriptionModel;
    }

    public PrescriptionModel getPrescription() {
        return prescriptionModel;
    }

    @Override
    public String toString() {
        return "UpdatePrescriptionResult{" +
                "prescriptionModel=" + prescriptionModel +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private PrescriptionModel prescriptionModel;

        public Builder withPrescription(PrescriptionModel prescriptionModel) {
            this.prescriptionModel = prescriptionModel;
            return this;
        }

        public UpdatePrescriptionResult build() {
            return new UpdatePrescriptionResult(prescriptionModel);
        }
    }
}
