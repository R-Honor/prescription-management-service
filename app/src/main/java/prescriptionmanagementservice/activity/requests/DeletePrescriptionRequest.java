package prescriptionmanagementservice.activity.requests;

import prescriptionmanagementservice.models.PrescriptionStatusEnum;

public class DeletePrescriptionRequest {

    private final String prescriptionId;
    private final PrescriptionStatusEnum status;

    public DeletePrescriptionRequest(String prescriptionId) {
        this.prescriptionId = prescriptionId;
        this.status = PrescriptionStatusEnum.DELETED;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public PrescriptionStatusEnum getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "DeletePrescriptionRequest{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String prescriptionId;

        private PrescriptionStatusEnum status;

        public Builder withPrescriptionId(String prescriptionId) {
            this.prescriptionId = prescriptionId;
            return this;
        }

        public Builder withStatus(PrescriptionStatusEnum status) {
            this.status = status;
            return this;
        }

        public DeletePrescriptionRequest build() {
            return new DeletePrescriptionRequest(prescriptionId);
        }
    }
}
