package prescriptionmanagementservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import prescriptionmanagementservice.models.PrescriptionStatusEnum;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Prescription {
    private static final String EMAIL_STATUS_INDEX = "EmailStatusIndex";

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

    @DynamoDBHashKey(attributeName = "prescriptionId")
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = EMAIL_STATUS_INDEX, attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "drug")
    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    @DynamoDBAttribute(attributeName = "dose")
    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    @DynamoDBAttribute(attributeName = "sigCode")
    public String getSigCode() {
        return sigCode;
    }

    public void setSigCode(String sigCode) {
        this.sigCode = sigCode;
    }

    @DynamoDBAttribute(attributeName = "lastFillDate")
    public ZonedDateTime getLastFillDate() {
        return lastFillDate;
    }

    public void setLastFillDate(ZonedDateTime lastFillDate) {
        this.lastFillDate = lastFillDate;
    }

    @DynamoDBAttribute(attributeName = "expirationDate")
    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(ZonedDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @DynamoDBAttribute(attributeName = "refills")
    public Integer getRefills() {
        return refills;
    }

    public void setRefills(Integer refills) {
        this.refills = refills;
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = EMAIL_STATUS_INDEX, attributeName = "status")
    public PrescriptionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatusEnum status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Prescription{" +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prescription)) return false;
        Prescription that = (Prescription) o;
        return getEmail().equals(that.getEmail()) &&
                getPrescriptionId().equals(that.getPrescriptionId()) &&
                getDrug().equals(that.getDrug()) &&
                getDose().equals(that.getDose()) &&
                getSigCode().equals(that.getSigCode()) &&
                Objects.equals(getLastFillDate(), that.getLastFillDate()) &&
                getExpirationDate().equals(that.getExpirationDate()) &&
                getRefills().equals(that.getRefills()) &&
                getStatus() == that.getStatus() &&
                getNotes().equals(that.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPrescriptionId(), getDrug(),
                getDose(), getSigCode(), getLastFillDate(), getExpirationDate(),
                getRefills(), getStatus(), getNotes());
    }
}
