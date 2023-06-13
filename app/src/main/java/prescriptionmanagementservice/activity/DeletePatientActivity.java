package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.DeletePatientRequest;
import prescriptionmanagementservice.activity.results.DeletePatientResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.models.PatientModel;

import javax.inject.Inject;

public class DeletePatientActivity {

    private final Logger log = LogManager.getLogger();
    private final PatientDao patientDao;

    @Inject
    public DeletePatientActivity(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public DeletePatientResult handleRequest(final DeletePatientRequest deletePatientRequest) {
        log.info("Received DeletePatientRequest {}", deletePatientRequest);
        String requestedEmail = deletePatientRequest.getEmail();
        log.info("Email from request is {}", requestedEmail);

        Patient patient = patientDao.deletePatient(requestedEmail);

        PatientModel patientModel = new ModelConverter().toPatientModel(patient);
        log.info("PatientModel = {}", patientModel.toString());
        return DeletePatientResult.builder()
                .withPatient(patientModel)
                .build();
    }
}
