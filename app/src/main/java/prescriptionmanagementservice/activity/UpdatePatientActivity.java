package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.UpdatePatientRequest;
import prescriptionmanagementservice.activity.results.UpdatePatientResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.exceptions.PatientNotFoundException;

import javax.inject.Inject;

public class UpdatePatientActivity {

    private final Logger log = LogManager.getLogger();
    private final PatientDao patientDao;

    @Inject
    public UpdatePatientActivity(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public UpdatePatientResult handleRequest(final UpdatePatientRequest updatePatientRequest) {
        log.info("Received UpdatePatientRequest {}", updatePatientRequest);
        Patient patient;

        try {
            patient = patientDao.viewPatient(updatePatientRequest.getEmail());
        }
        catch (PatientNotFoundException e) {
            throw new PatientNotFoundException("You attempted to update a patient that does not exist", e.getCause());
        }

        patient.setFirstName(updatePatientRequest.getFirstName());
        patient.setLastName(updatePatientRequest.getLastName());
        patient.setInsurance(updatePatientRequest.getInsurance());
        patient.setPhone(updatePatientRequest.getPhone());
        patient.setAddress(updatePatientRequest.getAddress());

        patient = patientDao.savePatient(patient);

        return UpdatePatientResult.builder()
                .withPatient(new ModelConverter().toPatientModel(patient))
                .build();
    }
}
