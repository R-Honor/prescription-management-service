package prescriptionmanagementservice.activity.requests;

public class SearchPatientRequest {

    private final String criteria;

    public SearchPatientRequest(String criteria) {
        this.criteria = criteria;
    }

    public String getCriteria() {
        return criteria;
    }

    @Override
    public String toString() {
        return "SearchPatientRequest{" +
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

        public SearchPatientRequest build() {
            return new SearchPatientRequest(criteria);
        }
    }
}
