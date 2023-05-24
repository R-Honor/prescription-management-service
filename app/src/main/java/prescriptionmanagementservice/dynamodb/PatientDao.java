package prescriptionmanagementservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

public class PatientDao {

    private static final String FIRST_LAST_INDEX = "FirstLastIndex";
    private final DynamoDBMapper mapper;

    @Inject
    public PatientDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


}
