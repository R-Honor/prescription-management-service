package prescriptionmanagementservice.activity.requests;

public class CreatePatientRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String insurance;
    private String phone;
    private String address;

    public  CreatePatientRequest() {

    }
    public CreatePatientRequest(String email, String firstName, String lastName, String insurance, String phone, String address) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.insurance = insurance;
        this.phone = phone;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "CreatePatientRequest{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", insurance='" + insurance + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String email;
        private String firstName;
        private String lastName;
        private String insurance;
        private String phone;
        private String address;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withInsurance(String insurance) {
            this.insurance = insurance;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public CreatePatientRequest build() {
            return new CreatePatientRequest(email, firstName, lastName, insurance, phone, address);
        }
    }
}
