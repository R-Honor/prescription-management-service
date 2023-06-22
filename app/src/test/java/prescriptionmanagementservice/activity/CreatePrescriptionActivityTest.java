package prescriptionmanagementservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.activity.requests.CreatePrescriptionRequest;
import prescriptionmanagementservice.activity.results.CreatePrescriptionResult;
import prescriptionmanagementservice.converters.ZonedDateTimeConverter;
import prescriptionmanagementservice.dynamodb.PrescriptionDao;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.models.PrescriptionModel;
import prescriptionmanagementservice.models.PrescriptionStatusEnum;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CreatePrescriptionActivityTest {

    @Mock
    private PrescriptionDao prescriptionDao;

    @InjectMocks
    private CreatePrescriptionActivity createPrescriptionActivity;


    @BeforeEach
    public  void setUp() {
        openMocks(this);
        createPrescriptionActivity = new CreatePrescriptionActivity(prescriptionDao);
    }

    @Test
    void handleRequest() {

        //GIVEN
        CreatePrescriptionRequest createPrescriptionRequest = CreatePrescriptionRequest.builder()
                .withEmail("aoeuaoeu@aoeu.aoeu")
                .withDrug("Advil")
                .withDose("100mg")
                .withSigCode("BID")
                .withNotes("Cats are cool")
                .withLastFillDate(new ZonedDateTimeConverter().unconvert("1922-12-30T12:00:00.000Z"))
                .withExpirationDate(new ZonedDateTimeConverter().unconvert("1991-12-26T12:00:00.000Z"))
                .withRefills(3)
                .build();

        PrescriptionModel prescriptionModel = PrescriptionModel.builder()
                .withEmail("aoeuaoeu@aoeu.aoeu")
                .withDrug("Advil")
                .withDose("100mg")
                .withSigCode("BID")
                .withNotes("Cats are cool")
                .withLastFillDate("1922-12-30T12:00Z")
                .withExpirationDate("1991-12-26T12:00Z")
                .withRefills(3)
                .withStatus(PrescriptionStatusEnum.PENDING)
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

        CreatePrescriptionResult expected = CreatePrescriptionResult.builder()
                .withPrescription(prescriptionModel)
                .build();

        when(prescriptionDao.createPrescription(any())).thenReturn(prescription);

        //WHEN
        CreatePrescriptionResult result = createPrescriptionActivity.handleRequest(createPrescriptionRequest);

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