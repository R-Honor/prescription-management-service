package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.CreatePrescriptionRequest;
import prescriptionmanagementservice.activity.results.CreatePrescriptionResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PrescriptionDao;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.models.PrescriptionModel;
import prescriptionmanagementservice.models.PrescriptionStatusEnum;

import javax.inject.Inject;
import java.util.UUID;

public class CreatePrescriptionActivity {

    private final Logger log = LogManager.getLogger();
    private final PrescriptionDao prescriptionDao;

    @Inject
    public CreatePrescriptionActivity(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    public CreatePrescriptionResult handleRequest(final CreatePrescriptionRequest createPrescriptionRequest) {
        log.info("Received CreatePrescriptionRequest {}", createPrescriptionRequest);

        Prescription newPrescription = new Prescription();
        newPrescription.setPrescriptionId(createPrescriptionRequest.getPrescriptionId());
        newPrescription.setEmail(createPrescriptionRequest.getEmail());
        newPrescription.setDrug(createPrescriptionRequest.getDrug());
        newPrescription.setDose(createPrescriptionRequest.getDose());
        newPrescription.setSigCode(createPrescriptionRequest.getSigCode());
        newPrescription.setNotes(createPrescriptionRequest.getNotes());
        newPrescription.setLastFillDate(createPrescriptionRequest.getLastFillDate());
        newPrescription.setExpirationDate(createPrescriptionRequest.getExpirationDate());
        newPrescription.setRefills(createPrescriptionRequest.getRefills());
        newPrescription.setStatus(createPrescriptionRequest.getStatus());

        PrescriptionModel prescriptionModel = new ModelConverter()
                .toPrescriptionModel(prescriptionDao.createPrescription(newPrescription));

        return CreatePrescriptionResult.builder()
                .withPrescription(prescriptionModel)
                .build();
    }

}