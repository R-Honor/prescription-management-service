package prescriptionmanagementservice.converters;

import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.models.PatientModel;
import prescriptionmanagementservice.models.PrescriptionModel;

import java.util.ArrayList;
import java.util.List;

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

    public List<PrescriptionModel> toPrescriptionModelList(List<Prescription> prescriptions) {
        List<PrescriptionModel> prescriptionModels = new ArrayList<>();

        for (Prescription prescription : prescriptions) {
            prescriptionModels.add(toPrescriptionModel(prescription));
        }

        return prescriptionModels;
    }

    public PatientModel toPatientModel(Patient patient) {
        return PatientModel.builder()
                .withEmail(patient.getEmail())
                .withFirstName(patient.getFirstName())
                .withLastName(patient.getLastName())
                .withInsurance(patient.getInsurance())
                .withPhone(patient.getPhone())
                .withAddress(patient.getAddress())
                .build();
    }
}
