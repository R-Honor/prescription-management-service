package prescriptionmanagementservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.exceptions.PatientNotFoundException;

import javax.inject.Inject;
import java.util.*;

public class PatientDao {

    private static final String LAST_FIRST_INDEX = "LastFirstIndex";
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

    public List<Patient> searchPatients(String[] criteria) {
        ArrayList<Patient> patientList = new ArrayList<>();
        DynamoDBQueryExpression<Patient> queryExpression;
        Map<String, AttributeValue> valueMap = new HashMap<>();

        if (Objects.equals(criteria[1], "none")) {
            valueMap.put(":lastName", new AttributeValue().withS(criteria[0]));

            queryExpression = new DynamoDBQueryExpression<Patient>()
                    .withIndexName(LAST_FIRST_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("lastName = :lastName")
                    .withExpressionAttributeValues(valueMap);
        }
        else {
            valueMap.put(":lastName", new AttributeValue().withS(criteria[0]));
            valueMap.put(":firstName", new AttributeValue().withS(criteria[1]));

            queryExpression = new DynamoDBQueryExpression<Patient>()
                    .withIndexName(LAST_FIRST_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("lastName = :lastName and firstName = :firstName")
                    .withExpressionAttributeValues(valueMap);
        }

        PaginatedQueryList<Patient> patientsFromGSI = mapper.query(Patient.class, queryExpression);
        for (Patient patient : patientsFromGSI) {
            Patient actualPatient = mapper.load(Patient.class, patient.getEmail());
            patientList.add(actualPatient);
        }
        return patientList;
    }
}
