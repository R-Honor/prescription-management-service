package prescriptionmanagementservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.exceptions.PrescriptionNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PrescriptionDaoTest {
    @Mock
    private DynamoDBMapper mapper;

    @InjectMocks
    private PrescriptionDao prescriptionDao;

    @BeforeEach
    public  void setUp() {
        openMocks(this);
        prescriptionDao = new PrescriptionDao(mapper);
    }

    @Test
    void viewPrescription_validPrescriptionId_callsLoadReturnsPrescription() {
        //GIVEN
        String validPrescriptionId = "123456789";

        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(validPrescriptionId);

        when(mapper.load(Prescription.class, validPrescriptionId)).thenReturn(prescription);

        //WHEN
        Prescription result = prescriptionDao.viewPrescription(validPrescriptionId);

        //THEN
        assertEquals(validPrescriptionId, result.getPrescriptionId());
    }

    @Test
    void viewPrescription_invalidPrescriptionId_throwsError() {
        //GIVEN
        String invalidPrescriptionId = "123456789";

        when(mapper.load(Prescription.class, invalidPrescriptionId)).thenReturn(null);

        //WHEN THEN
        assertThrows(PrescriptionNotFoundException.class, () -> prescriptionDao.viewPrescription(invalidPrescriptionId));
    }

    @Test
    void createPrescription_prescription_returnsPrescription() {
        //GIVEN
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId("123456789");

        //WHEN
        Prescription result = prescriptionDao.createPrescription(prescription);

        //THEN
        verify(mapper).save(prescription);
        assertEquals(prescription, result);
    }

    @Test
    void savePrescription_prescription_returnsUpdatedPrescription() {
        //GIVEN
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId("123456789");

        //WHEN
        Prescription result = prescriptionDao.savePrescription(prescription);

        //THEN
        verify(mapper).save(prescription);
        assertEquals(prescription, result);
    }

    @Test
    void deletePrescription_validPrescriptionId_returnsDeletedPrescription() {
        //GIVEN
        String validPrescriptionId = "123456789";

        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(validPrescriptionId);

        //WHEN
        Prescription result = prescriptionDao.deletePrescription(prescription);

        //THEN
        verify(mapper).save(prescription);
        assertEquals(prescription, result);
    }
}