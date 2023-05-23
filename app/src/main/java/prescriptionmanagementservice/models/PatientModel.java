package prescriptionmanagementservice.models;

import java.util.Objects;

public class PatientModel {

    private String email;
    private String firstName;
    private String lastName;
    private String insurance;
    private String phone;
    private String address;

    public PatientModel(String email, String firstName, String lastName, String insurance, String phone, String address) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientModel)) return false;
        PatientModel that = (PatientModel) o;
        return getEmail().equals(that.getEmail()) && getFirstName().equals(that.getFirstName()) && getLastName().equals(that.getLastName()) && getInsurance().equals(that.getInsurance()) && getPhone().equals(that.getPhone()) && getAddress().equals(that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getFirstName(), getLastName(), getInsurance(), getPhone(), getAddress());
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

        public PatientModel build() {
            return new PatientModel(email, firstName, lastName, insurance, phone, address);
        }
    }
}
