package prescriptionmanagementservice.activity.requests;

public class ViewPatientRequest {

    private final String email;

    public ViewPatientRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ViewPatientRequest{" +
                "email='" + email + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ViewPatientRequest build() {
            return new ViewPatientRequest(email);
        }
    }
}
