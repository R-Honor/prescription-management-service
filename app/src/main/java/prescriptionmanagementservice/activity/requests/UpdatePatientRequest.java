package prescriptionmanagementservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = UpdatePatientRequest.Builder.class)
public class UpdatePatientRequest {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final String insurance;
    private final String phone;
    private final String address;

    public UpdatePatientRequest(String email, String firstName, String lastName, String insurance, String phone, String address) {
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
        return "UpdatePatientRequest{" +
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

    @JsonPOJOBuilder
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

        public UpdatePatientRequest build() {
            return new UpdatePatientRequest(email, firstName, lastName, insurance, phone, address);
        }
    }
}
