package prescriptionmanagementservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.exceptions.PrescriptionNotFoundException;

import javax.inject.Inject;
import java.util.*;

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

    public Prescription savePrescription(Prescription prescription) {
        this.mapper.save(prescription);
        return prescription;
    }

    public Prescription deletePrescription(Prescription prescription) {
        this.mapper.save(prescription);
        return prescription;
    }

    public List<Prescription> searchPrescriptions(String[] criteria) {
        ArrayList<Prescription> prescriptionList = new ArrayList<>();

        if (Objects.equals(criteria[1], "none")) {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":email", new AttributeValue().withS(criteria[0]));

            DynamoDBQueryExpression<Prescription> queryExpression = new DynamoDBQueryExpression<Prescription>()
                    .withIndexName(EMAIL_STATUS_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("email = :email")
                    .withExpressionAttributeValues(valueMap);

            PaginatedQueryList<Prescription> prescriptionsFromGSI = mapper.query(Prescription.class, queryExpression);

            for (Prescription prescription : prescriptionsFromGSI) {
                Prescription actualPrescription = mapper.load(Prescription.class, prescription.getPrescriptionId());
                prescriptionList.add(actualPrescription);
            }
        }
        else {
            Map<String, AttributeValue> valueMap = new HashMap<>();
            valueMap.put(":email", new AttributeValue().withS(criteria[0]));
            valueMap.put(":prescriptionStatus", new AttributeValue().withS(criteria[1]));

            Map<String, String> nameMap = new HashMap<>();
            nameMap.put("#statusAttribute", "status");

            DynamoDBQueryExpression<Prescription> queryExpression = new DynamoDBQueryExpression<Prescription>()
                    .withIndexName(EMAIL_STATUS_INDEX)
                    .withConsistentRead(false)
                    .withKeyConditionExpression("email = :email and #statusAttribute = :prescriptionStatus")
                    .withExpressionAttributeValues(valueMap)
                    .withExpressionAttributeNames(nameMap);

            PaginatedQueryList<Prescription> prescriptionsFromGSI = mapper.query(Prescription.class, queryExpression);

            for (Prescription prescription : prescriptionsFromGSI) {
                Prescription actualPrescription = mapper.load(Prescription.class, prescription.getPrescriptionId());
                prescriptionList.add(actualPrescription);
            }
        }
        return prescriptionList;
    }
}
