package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PrescriptionModel;

public class DeletePrescriptionResult {

    private final PrescriptionModel prescriptionModel;

    public DeletePrescriptionResult(PrescriptionModel prescriptionModel) {
        this.prescriptionModel = prescriptionModel;
    }

    public PrescriptionModel getPrescription() {
        return prescriptionModel;
    }

    @Override
    public String toString() {
        return "DeletePrescriptionResult{" +
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

        public DeletePrescriptionResult build() {
            return new DeletePrescriptionResult(prescriptionModel);
        }
    }
}
