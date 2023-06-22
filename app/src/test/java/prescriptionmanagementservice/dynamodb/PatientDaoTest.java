package prescriptionmanagementservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.exceptions.PatientNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PatientDaoTest {

    @Mock
    private DynamoDBMapper mapper;

    @InjectMocks
    private PatientDao patientDao;

    @BeforeEach
    public  void setUp() {
        openMocks(this);
        patientDao = new PatientDao(mapper);
    }

    @Test
    void viewPatient_validEmail_callsLoadReturnsPatient() {
        //GIVEN
        String validEmail = "aoeuaoeu@aoeu.aoeu";

        Patient patient = new Patient();
        patient.setEmail(validEmail);

        when(mapper.load(Patient.class, validEmail)).thenReturn(patient);

        //WHEN
        Patient result = patientDao.viewPatient(validEmail);

        //THEN
        assertEquals(validEmail, result.getEmail());
    }

    @Test
    void viewPatient_invalidEmail_throwsError() {
        //GIVEN
        String invalidEmail = "aoeuaoeu@aoeu.aoeu";

        when(mapper.load(Patient.class, invalidEmail)).thenReturn(null);

        //WHEN THEN
        assertThrows(PatientNotFoundException.class, () -> patientDao.viewPatient(invalidEmail));
    }

    @Test
    void createPatient_patient_returnsPatient() {
        //GIVEN
        Patient patient = new Patient();
        patient.setEmail("aoeuaoeu@aoeu.aoeu");

        //WHEN
        Patient result = patientDao.createPatient(patient);

        //THEN
        verify(mapper).save(patient);
        assertEquals(patient, result);
    }

    @Test
    void savePatient_patient_returnsUpdatedPatient() {
        //GIVEN
        Patient patient = new Patient();
        patient.setEmail("aoeuaoeu@aoeu.aoeu");

        //WHEN
        Patient result = patientDao.savePatient(patient);

        //THEN
        verify(mapper).save(patient);
        assertEquals(patient, result);
    }

    @Test
    void deletePatient_validEmail_returnsDeletedPatient() {
        //GIVEN
        String validEmail = "aoeuaoeu@aoeu.aoeu";

        Patient patient = new Patient();
        patient.setEmail(validEmail);

        when(mapper.load(Patient.class, validEmail)).thenReturn(patient);

        //WHEN
        Patient result = patientDao.deletePatient(validEmail);

        //THEN
        verify(mapper).load(Patient.class, validEmail);
        verify(mapper).delete(patient);
        assertEquals(patient, result);
    }
}