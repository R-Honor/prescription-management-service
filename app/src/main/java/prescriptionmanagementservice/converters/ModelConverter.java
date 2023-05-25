package prescriptionmanagementservice.converters;

import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.models.PrescriptionModel;

public class ModelConverter {

    public PrescriptionModel toPrescriptionModel(Prescription prescription) {
        return PrescriptionModel.builder()
                .withPrescriptionId(prescription.getPrescriptionId())
                .withEmail(prescription.getEmail())
                .withDrug(prescription.getDrug())
                .withDose(prescription.getDose())
                .withSigCode(prescription.getSigCode())
                .withLastFillDate(prescription.getLastFillDate().toString())
                .withExpirationDate(prescription.getExpirationDate().toString())
                .withRefills(prescription.getRefills())
                .withStatus(prescription.getStatus())
                .withNotes(prescription.getNotes())
                .build();
    }
}
