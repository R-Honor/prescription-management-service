package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PrescriptionModel;

public class ViewPrescriptionResult {

    private final PrescriptionModel prescriptionModel;

    public ViewPrescriptionResult(PrescriptionModel prescriptionModel) {
        this.prescriptionModel = prescriptionModel;
    }

    public PrescriptionModel getPrescription() {
        return prescriptionModel;
    }

    @Override
    public String toString() {
        return "ViewPrescriptionResult{" +
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

        public ViewPrescriptionResult build() {
            return new ViewPrescriptionResult(prescriptionModel);
        }
    }
}
