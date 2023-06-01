package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.ViewPatientRequest;
import prescriptionmanagementservice.activity.results.ViewPatientResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.models.PatientModel;

import javax.inject.Inject;

public class ViewPatientActivity {

    private final Logger log = LogManager.getLogger();
    private final PatientDao patientDao;

    @Inject
    public ViewPatientActivity(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public ViewPatientResult handleRequest(final ViewPatientRequest viewPatientRequest) {
        log.info("Received ViewPatientRequest {}", viewPatientRequest);
        String requestedEmail = viewPatientRequest.getEmail();
        Patient patient = patientDao.viewPatient(requestedEmail);
        PatientModel patientModel = new ModelConverter().toPatientModel(patient);

        return ViewPatientResult.builder()
                .withPatient(patientModel)
                .build();
    }
}
