package prescriptionmanagementservice.activity.requests;

import prescriptionmanagementservice.activity.SearchPrescriptionActivity;

public class SearchPrescriptionRequest {

    private final String criteria;

    public SearchPrescriptionRequest(String criteria) {
        this.criteria = criteria;
    }

    public String getCriteria() {
        return criteria;
    }

    @Override
    public String toString() {
        return "SearchPrescriptionRequest{" +
                "criteria='" + criteria + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String criteria;

        public Builder withCriteria(String criteria) {
            this.criteria = criteria;
            return this;
        }

        public SearchPrescriptionRequest build() {
            return new SearchPrescriptionRequest(criteria);
        }
    }
}
