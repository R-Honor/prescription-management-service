package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.UpdatePrescriptionRequest;
import prescriptionmanagementservice.activity.results.UpdatePrescriptionResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PrescriptionDao;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.exceptions.PrescriptionNotFoundException;

import javax.inject.Inject;

public class UpdatePrescriptionActivity {

    private final Logger log = LogManager.getLogger();
    private final PrescriptionDao prescriptionDao;

    @Inject
    public UpdatePrescriptionActivity(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    public UpdatePrescriptionResult handleRequest(final UpdatePrescriptionRequest updatePrescriptionRequest) {
        log.info("Received UpdatePrescriptionRequest {}", updatePrescriptionRequest);
        Prescription prescription;

        try {
            prescription = prescriptionDao.viewPrescription(updatePrescriptionRequest.getPrescriptionId());
        }
        catch (PrescriptionNotFoundException e) {
            throw new PrescriptionNotFoundException("You attempted to update a prescription that does not exist", e.getCause());
        }

        prescription.setDose(updatePrescriptionRequest.getDose());
        prescription.setSigCode(updatePrescriptionRequest.getSigCode());
        prescription.setNotes(updatePrescriptionRequest.getNotes());
        prescription.setLastFillDate(updatePrescriptionRequest.getLastFillDate());
        prescription.setExpirationDate(updatePrescriptionRequest.getExpirationDate());
        prescription.setRefills(updatePrescriptionRequest.getRefills());
        prescription.setStatus(updatePrescriptionRequest.getStatus());

        prescription = prescriptionDao.savePrescription(prescription);

        return UpdatePrescriptionResult.builder()
                .withPrescription(new ModelConverter().toPrescriptionModel(prescription))
                .build();
    }
}
