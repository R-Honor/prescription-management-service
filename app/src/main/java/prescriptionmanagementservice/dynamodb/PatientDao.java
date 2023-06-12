package prescriptionmanagementservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.exceptions.PatientNotFoundException;

import javax.inject.Inject;

public class PatientDao {

    private static final String FIRST_LAST_INDEX = "FirstLastIndex";
    private final DynamoDBMapper mapper;

    @Inject
    public PatientDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public Patient viewPatient(String email) {
        Patient patient = this.mapper.load(Patient.class, email);

        if (patient == null) {
            throw new PatientNotFoundException("Could not find a patient with email: " + email);
        }
        return patient;
    }

    public Patient createPatient(Patient patient) {
        this.mapper.save(patient);
        return patient;
    }

    public Patient savePatient(Patient patient) {
        this.mapper.save(patient);
        return patient;
    }

    public Patient deletePatient(String email) {
        Patient patientToRemove;

        try {
            patientToRemove = viewPatient(email);
        }
        catch (PatientNotFoundException e) {
            throw new PatientNotFoundException(String.format("A patient with the email %s was not found to delete", email));
        }
        this.mapper.delete(patientToRemove);

        return patientToRemove;
    }
}
