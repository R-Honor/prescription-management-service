package prescriptionmanagementservice.models;

import java.time.ZonedDateTime;
import java.util.Objects;

public class PrescriptionModel {

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

    public PrescriptionModel(String prescriptionId, String email, String drug, String dose, String sigCode, String notes, ZonedDateTime lastFillDate, ZonedDateTime expirationDate, Integer refills, PrescriptionStatusEnum status) {
        this.prescriptionId = prescriptionId;
        this.email = email;
        this.drug = drug;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrescriptionModel)) return false;
        PrescriptionModel that = (PrescriptionModel) o;
        return getPrescriptionId().equals(that.getPrescriptionId()) &&
                getEmail().equals(that.getEmail()) &&
                getDrug().equals(that.getDrug()) &&
                getDose().equals(that.getDose()) &&
                getSigCode().equals(that.getSigCode()) &&
                getNotes().equals(that.getNotes()) &&
                Objects.equals(getLastFillDate(), that.getLastFillDate()) &&
                getExpirationDate().equals(that.getExpirationDate()) &&
                getRefills().equals(that.getRefills()) &&
                getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrescriptionId(), getEmail(), getDrug(),
                getDose(), getSigCode(), getNotes(), getLastFillDate(),
                getExpirationDate(), getRefills(), getStatus());
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

        public PrescriptionModel build() {
            return new PrescriptionModel(prescriptionId, email, drug, dose, sigCode, notes, lastFillDate, expirationDate, refills, status);
        }
    }
}
