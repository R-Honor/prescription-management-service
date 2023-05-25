package prescriptionmanagementservice.activity.requests;

public class ViewPrescriptionRequest {

    private final String prescriptionId;

    public ViewPrescriptionRequest(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    @Override
    public String toString() {
        return "ViewPrescriptionRequest{" +
                "prescriptionId='" + prescriptionId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String prescriptionId;

        public Builder withPrescriptionId(String prescriptionId) {
            this.prescriptionId = prescriptionId;
            return this;
        }

        public ViewPrescriptionRequest build() {
            return new ViewPrescriptionRequest(prescriptionId);
        }
    }
}
