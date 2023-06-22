package prescriptionmanagementservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.activity.requests.DeletePatientRequest;
import prescriptionmanagementservice.activity.results.DeletePatientResult;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.models.PatientModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DeletePatientActivityTest {

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private DeletePatientActivity deletePatientActivity;


    @BeforeEach
    public  void setUp() {
        openMocks(this);
        deletePatientActivity = new DeletePatientActivity(patientDao);
    }

    @Test
    void handleRequest() {

        //GIVEN
        DeletePatientRequest deletePatientRequest = DeletePatientRequest.builder()
                .withEmail("aoeuaoeu@aoeu.aoeu")
                .build();

        PatientModel patientModel = PatientModel.builder()
                .withEmail("aoeuaoeu@aoeu.aoeu")
                .withFirstName("Billy")
                .withLastName("Bob")
                .withInsurance("Cigna")
                .withAddress("123 Street Ln")
                .withPhone("000-000-0000")
                .build();

        Patient patient = new Patient();
        patient.setEmail("aoeuaoeu@aoeu.aoeu");
        patient.setFirstName("Billy");
        patient.setLastName("Bob");
        patient.setInsurance("Cigna");
        patient.setAddress("123 Street Ln");
        patient.setPhone("000-000-0000");

        DeletePatientResult expected = DeletePatientResult.builder()
                .withPatient(patientModel)
                .build();

        when(patientDao.deletePatient(any())).thenReturn(patient);

        //WHEN
        DeletePatientResult result = deletePatientActivity.handleRequest(deletePatientRequest);

        //THEN
        assertEquals(expected.getPatient(), result.getPatient());
    }
}