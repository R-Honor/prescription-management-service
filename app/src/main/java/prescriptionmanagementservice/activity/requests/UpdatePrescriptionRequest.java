package prescriptionmanagementservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import prescriptionmanagementservice.models.PrescriptionStatusEnum;

import java.time.ZonedDateTime;

@JsonDeserialize(builder = UpdatePrescriptionRequest.Builder.class)
public class UpdatePrescriptionRequest {

    private final String prescriptionId;
    private final String dose;
    private final String sigCode;
    private final String notes;

    private final ZonedDateTime lastFillDate;
    private final ZonedDateTime expirationDate;

    private final Integer refills;

    private final PrescriptionStatusEnum status;

    public UpdatePrescriptionRequest(String prescriptionId, String dose, String sigCode, String notes, ZonedDateTime lastFillDate, ZonedDateTime expirationDate, Integer refills, PrescriptionStatusEnum status) {
        this.prescriptionId = prescriptionId;
        this.dose = dose;
        this.sigCode = sigCode;
        this.notes = notes;
        this.lastFillDate = lastFillDate;
        this.expirationDate = expirationDate;
        this.refills = refills;
        this.status = status;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getDose() {
        return dose;
    }

    public String getSigCode() {
        return sigCode;
    }

    public String getNotes() {
        return notes;
    }

    public ZonedDateTime getLastFillDate() {
        return lastFillDate;
    }

    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    public Integer getRefills() {
        return refills;
    }

    public PrescriptionStatusEnum getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UpdatePrescriptionRequest{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", dose='" + dose + '\'' +
                ", sigCode='" + sigCode + '\'' +
                ", notes='" + notes + '\'' +
                ", lastFillDate=" + lastFillDate +
                ", expirationDate=" + expirationDate +
                ", refills=" + refills +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        private String prescriptionId;
        private String dose;
        private String sigCode;
        private String notes;

        private ZonedDateTime lastFillDate;
        private ZonedDateTime expirationDate;

        private Integer refills;

        private PrescriptionStatusEnum status;

        public Builder withPrescriptionId(String prescriptionId) {
            this.prescriptionId = prescriptionId;
            return this;
        }

        public Builder withDose(String dose) {
            this.dose = dose;
            return this;
        }

        public Builder withSigCode(String sigCode) {
            this.sigCode = sigCode;
            return this;
        }

        public Builder withNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder withLastFillDate(ZonedDateTime lastFillDate) {
            this.lastFillDate = lastFillDate;
            return this;
        }

        public Builder withExpirationDate(ZonedDateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder withRefills(Integer refills) {
            this.refills = refills;
            return this;
        }

        public Builder withStatus(PrescriptionStatusEnum status) {
            this.status = status;
            return this;
        }

        public UpdatePrescriptionRequest build() {
            return new UpdatePrescriptionRequest(prescriptionId, dose, sigCode, notes, lastFillDate, expirationDate, refills, status);
        }
    }
}
