package prescriptionmanagementservice.activity.requests;

public class DeletePatientRequest {

    private final String email;

    public DeletePatientRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "DeletePatientRequest{" +
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

        public DeletePatientRequest build() {
            return new DeletePatientRequest(email);
        }
    }
}
