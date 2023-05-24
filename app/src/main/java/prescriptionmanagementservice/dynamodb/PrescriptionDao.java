package prescriptionmanagementservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

public class PrescriptionDao {

    private static final String EMAIL_STATUS_INDEX = "EmailStatusIndex";
    private final DynamoDBMapper mapper;

    @Inject
    public PrescriptionDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


}
