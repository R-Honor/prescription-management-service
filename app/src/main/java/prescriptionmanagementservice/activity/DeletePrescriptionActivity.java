package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.DeletePrescriptionRequest;
import prescriptionmanagementservice.activity.results.DeletePrescriptionResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PrescriptionDao;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.exceptions.PrescriptionNotFoundException;

import javax.inject.Inject;

public class DeletePrescriptionActivity {

    private final Logger log = LogManager.getLogger();
    private final PrescriptionDao prescriptionDao;

    @Inject
    public DeletePrescriptionActivity(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    public DeletePrescriptionResult handleRequest(final DeletePrescriptionRequest deletePrescriptionRequest) {
        log.info("Received DeletePrescriptionRequest {}", deletePrescriptionRequest);
        Prescription prescription;

        try {
            prescription = prescriptionDao.viewPrescription(deletePrescriptionRequest.getPrescriptionId());
        }
        catch (PrescriptionNotFoundException e) {
            throw new PrescriptionNotFoundException("You attempted to delete a prescription that does not exist", e.getCause());
        }

        prescription.setStatus(deletePrescriptionRequest.getStatus());

        prescription = prescriptionDao.deletePrescription(prescription);

        return DeletePrescriptionResult.builder()
                .withPrescription(new ModelConverter().toPrescriptionModel(prescription))
                .build();
    }
}
