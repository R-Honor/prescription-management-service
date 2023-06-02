package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.CreatePatientRequest;
import prescriptionmanagementservice.activity.results.CreatePatientResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.models.PatientModel;

import javax.inject.Inject;

public class CreatePatientActivity {

    private final Logger log = LogManager.getLogger();
    private final PatientDao patientDao;

    @Inject
    public CreatePatientActivity(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public CreatePatientResult handleRequest(final CreatePatientRequest createPatientRequest) {
        log.info("Received CreatePatientRequest {}", createPatientRequest);

        Patient newPatient = new Patient();
        newPatient.setEmail(createPatientRequest.getEmail());
        newPatient.setFirstName(createPatientRequest.getFirstName());
        newPatient.setLastName(createPatientRequest.getLastName());
        newPatient.setInsurance(createPatientRequest.getInsurance());
        newPatient.setPhone(createPatientRequest.getPhone());
        newPatient.setAddress(createPatientRequest.getAddress());

        PatientModel patientModel = new ModelConverter()
                .toPatientModel(patientDao.createPatient(newPatient));

        return CreatePatientResult.builder()
                .withPatient(patientModel)
                .build();
    }
}
