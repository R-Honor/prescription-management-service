package prescriptionmanagementservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PrescriptionStatusEnum {
    @JsonProperty("PENDING") PENDING,
    @JsonProperty("READY") READY,
    @JsonProperty("FILLED") FILLED,
    @JsonProperty("EXPIRED") EXPIRED,
    @JsonProperty("DELETED") DELETED
}
