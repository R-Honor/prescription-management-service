package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.SearchPrescriptionRequest;
import prescriptionmanagementservice.activity.results.SearchPrescriptionResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PrescriptionDao;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.models.PrescriptionModel;

import javax.inject.Inject;

import java.util.List;

import static prescriptionmanagementservice.utils.NullUtils.ifNull;

public class SearchPrescriptionActivity {

    private final Logger log = LogManager.getLogger();
    private final PrescriptionDao prescriptionDao;

    @Inject
    public SearchPrescriptionActivity(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    public SearchPrescriptionResult handleRequest(final SearchPrescriptionRequest searchPrescriptionRequest) {
        log.info("Received SearchPrescriptionRequest {}", searchPrescriptionRequest);

        String criteria = ifNull(searchPrescriptionRequest.getCriteria(), "");
        String[] criteriaArray = criteria.isBlank() ? new String[0] : criteria.split("\\s");

        List<Prescription> results = prescriptionDao.searchPrescriptions(criteriaArray);

        List<PrescriptionModel> prescriptionModels = new ModelConverter().toPrescriptionModelList(results);

        return SearchPrescriptionResult.builder()
                .withPrescriptions(prescriptionModels)
                .build();
    }
}
