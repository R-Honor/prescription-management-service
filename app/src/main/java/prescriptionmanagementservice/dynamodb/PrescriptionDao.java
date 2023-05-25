package prescriptionmanagementservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.exceptions.PrescriptionNotFoundException;

import javax.inject.Inject;

public class PrescriptionDao {

    private static final String EMAIL_STATUS_INDEX = "EmailStatusIndex";
    private final DynamoDBMapper mapper;

    @Inject
    public PrescriptionDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public Prescription viewPrescription(String prescriptionId) {
        Prescription prescription = this.mapper.load(Prescription.class, prescriptionId);

        if (prescription == null) {
            throw new PrescriptionNotFoundException("Could not find a prescription with id " + prescriptionId);
        }
        return prescription;
    }

    public Prescription createPrescription(Prescription prescription) {
        this.mapper.save(prescription);
        return prescription;
    }
}
