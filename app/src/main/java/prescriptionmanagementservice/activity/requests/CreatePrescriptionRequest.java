package prescriptionmanagementservice.activity.requests;

import prescriptionmanagementservice.models.PrescriptionStatusEnum;

import java.time.ZonedDateTime;
import java.util.UUID;

public class CreatePrescriptionRequest {

    private String prescriptionId;
    private String email;
    private String drug;
    private String dose;
    private String sigCode;
    private String notes;

    private ZonedDateTime lastFillDate;
    private ZonedDateTime expirationDate;

    private Integer refills;

    private PrescriptionStatusEnum status;

    public CreatePrescriptionRequest() {

    }
    public CreatePrescriptionRequest(String email, String drug, String dose, String sigCode, String notes, ZonedDateTime lastFillDate,ZonedDateTime expirationDate, Integer refills) {
        this.prescriptionId = String.valueOf(UUID.randomUUID());
        this.email = email;
        this.drug = drug;
        this.dose = dose;
        this.sigCode = sigCode;
        this.notes = notes;
        this.lastFillDate = lastFillDate;
        this.expirationDate = expirationDate;
        this.refills = refills;
        this.status = PrescriptionStatusEnum.PENDING;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getEmail() {
        return email;
    }

    public String getDrug() {
        return drug;
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
        return "CreatePrescriptionRequest{" +
                "prescriptionId='" + prescriptionId + '\'' +
                ", email='" + email + '\'' +
                ", drug='" + drug + '\'' +
                ", dose='" + dose + '\'' +
                ", sigCode='" + sigCode + '\'' +
                ", notes='" + notes + '\'' +
                ", lastFillDate=" + lastFillDate +
                ", expirationDate=" + expirationDate +
                ", refills=" + refills +
                ", status=" + status +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String prescriptionId;
        private String email;
        private String drug;
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
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withDrug(String drug) {
            this.drug = drug;
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

        public CreatePrescriptionRequest build() {
            return new CreatePrescriptionRequest(email, drug, dose, sigCode, notes, lastFillDate, expirationDate, refills);
        }
    }
}
