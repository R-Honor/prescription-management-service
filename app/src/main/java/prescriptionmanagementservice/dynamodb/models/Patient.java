package prescriptionmanagementservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Objects;

@DynamoDBTable(tableName = "patients")
public class Patient {

    private static final String LAST_FIRST_INDEX = "LastFirstIndex";

    private String email;
    private String firstName;
    private String lastName;
    private String insurance;
    private String phone;
    private String address;

    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBIndexRangeKey(globalSecondaryIndexName = LAST_FIRST_INDEX, attributeName = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = LAST_FIRST_INDEX, attributeName = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "insurance")
    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    @DynamoDBAttribute(attributeName = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", insurance='" + insurance + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return getEmail().equals(patient.getEmail()) &&
                getFirstName().equals(patient.getFirstName()) &&
                getLastName().equals(patient.getLastName()) &&
                getInsurance().equals(patient.getInsurance()) &&
                getPhone().equals(patient.getPhone()) &&
                getAddress().equals(patient.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getFirstName(), getLastName(),
                getInsurance(), getPhone(), getAddress());
    }

}
