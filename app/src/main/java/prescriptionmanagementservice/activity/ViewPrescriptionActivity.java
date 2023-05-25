package prescriptionmanagementservice.activity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.ViewPrescriptionRequest;
import prescriptionmanagementservice.activity.results.ViewPrescriptionResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PrescriptionDao;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.models.PrescriptionModel;

import javax.inject.Inject;

public class ViewPrescriptionActivity {

    private final Logger log = LogManager.getLogger();
    private final PrescriptionDao prescriptionDao;

    @Inject
    public ViewPrescriptionActivity(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    public ViewPrescriptionResult handleRequest(final ViewPrescriptionRequest viewPrescriptionRequest) {
        log.info("Received ViewPrescriptionRequest {}", viewPrescriptionRequest);
        String requestedPrescriptionId = viewPrescriptionRequest.getPrescriptionId();
        Prescription prescription = prescriptionDao.viewPrescription(requestedPrescriptionId);
        PrescriptionModel prescriptionModel = new ModelConverter().toPrescriptionModel(prescription);

        return ViewPrescriptionResult.builder()
                .withPrescription(prescriptionModel)
                .build();
    }
}
