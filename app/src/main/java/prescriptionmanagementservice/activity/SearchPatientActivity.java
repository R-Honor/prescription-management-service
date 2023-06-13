package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.SearchPatientRequest;
import prescriptionmanagementservice.activity.results.SearchPatientResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.models.PatientModel;

import javax.inject.Inject;
import java.util.List;

import static prescriptionmanagementservice.utils.NullUtils.ifNull;

public class SearchPatientActivity {

    private final Logger log = LogManager.getLogger();
    private final PatientDao patientDao;

    @Inject
    public SearchPatientActivity(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public SearchPatientResult handleRequest(final SearchPatientRequest searchPatientRequest) {
        log.info("Received SearchPatientRequest {}", searchPatientRequest);

        String criteria = ifNull(searchPatientRequest.getCriteria(), "");
        String[] criteriaArray = criteria.isBlank() ? new String[0] : criteria.split("\\s");

        List<Patient> results = patientDao.searchPatients(criteriaArray);

        List<PatientModel> patientModels = new ModelConverter().toPatientModelList(results);

        return SearchPatientResult.builder()
                .withPatients(patientModels)
                .build();
    }
}
