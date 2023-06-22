package prescriptionmanagementservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.activity.requests.UpdatePrescriptionRequest;
import prescriptionmanagementservice.activity.results.UpdatePrescriptionResult;
import prescriptionmanagementservice.converters.ZonedDateTimeConverter;
import prescriptionmanagementservice.dynamodb.PrescriptionDao;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.models.PrescriptionModel;
import prescriptionmanagementservice.models.PrescriptionStatusEnum;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdatePrescriptionActivityTest {

    @Mock
    private PrescriptionDao prescriptionDao;

    @InjectMocks
    private UpdatePrescriptionActivity updatePrescriptionActivity;


    @BeforeEach
    public  void setUp() {
        openMocks(this);
        updatePrescriptionActivity = new UpdatePrescriptionActivity(prescriptionDao);
    }

    @Test
    void handleRequest() {

        //GIVEN
        UpdatePrescriptionRequest updatePrescriptionRequest = UpdatePrescriptionRequest.builder()
                .withDose("200mg")
                .withSigCode("qH")
                .withNotes("Cats are awesome")
                .withLastFillDate(new ZonedDateTimeConverter().unconvert("1922-12-30T12:00:00.000Z"))
                .withExpirationDate(new ZonedDateTimeConverter().unconvert("1991-12-26T12:00:00.000Z"))
                .withRefills(2)
                .withStatus(PrescriptionStatusEnum.READY)
                .build();

        PrescriptionModel prescriptionModel = PrescriptionModel.builder()
                .withEmail("aoeuaoeu@aoeu.aoeu")
                .withDrug("Advil")
                .withDose("200mg")
                .withSigCode("qH")
                .withNotes("Cats are awesome")
                .withLastFillDate("1922-12-30T12:00Z")
                .withExpirationDate("1991-12-26T12:00Z")
                .withRefills(2)
                .withStatus(PrescriptionStatusEnum.READY)
                .build();

        Prescription prescription = new Prescription();
        prescription.setEmail("aoeuaoeu@aoeu.aoeu");
        prescription.setDrug("Advil");
        prescription.setDose("100mg");
        prescription.setSigCode("BID");
        prescription.setNotes("Cats are cool");
        prescription.setLastFillDate(new ZonedDateTimeConverter().unconvert("1922-12-30T12:00:00.000Z"));
        prescription.setExpirationDate(new ZonedDateTimeConverter().unconvert("1991-12-26T12:00:00.000Z"));
        prescription.setRefills(3);
        prescription.setStatus(PrescriptionStatusEnum.PENDING);

        Prescription updatedPrescription = new Prescription();
        updatedPrescription.setEmail("aoeuaoeu@aoeu.aoeu");
        updatedPrescription.setDrug("Advil");
        updatedPrescription.setDose("200mg");
        updatedPrescription.setSigCode("qH");
        updatedPrescription.setNotes("Cats are awesome");
        updatedPrescription.setLastFillDate(new ZonedDateTimeConverter().unconvert("1922-12-30T12:00:00.000Z"));
        updatedPrescription.setExpirationDate(new ZonedDateTimeConverter().unconvert("1991-12-26T12:00:00.000Z"));
        updatedPrescription.setRefills(2);
        updatedPrescription.setStatus(PrescriptionStatusEnum.READY);

        UpdatePrescriptionResult expected = UpdatePrescriptionResult.builder()
                .withPrescription(prescriptionModel)
                .build();

        when(prescriptionDao.viewPrescription(any())).thenReturn(prescription);
        when(prescriptionDao.savePrescription(any())).thenReturn(updatedPrescription);

        //WHEN
        UpdatePrescriptionResult result = updatePrescriptionActivity.handleRequest(updatePrescriptionRequest);

        //THEN
        assertEquals(expected.getPrescription().getDrug(), result.getPrescription().getDrug());
        assertEquals(expected.getPrescription().getDose(), result.getPrescription().getDose());
        assertEquals(expected.getPrescription().getEmail(), result.getPrescription().getEmail());
        assertEquals(expected.getPrescription().getExpirationDate(), result.getPrescription().getExpirationDate());
        assertEquals(expected.getPrescription().getLastFillDate(), result.getPrescription().getLastFillDate());
        assertEquals(expected.getPrescription().getSigCode(), result.getPrescription().getSigCode());
        assertEquals(expected.getPrescription().getNotes(), result.getPrescription().getNotes());
        assertEquals(expected.getPrescription().getRefills(), result.getPrescription().getRefills());
        assertEquals(expected.getPrescription().getStatus(), result.getPrescription().getStatus());
    }
}