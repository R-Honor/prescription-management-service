package prescriptionmanagementservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.activity.requests.CreatePatientRequest;
import prescriptionmanagementservice.activity.results.CreatePatientResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.models.PatientModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CreatePatientActivityTest {

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private CreatePatientActivity createPatientActivity;


    @BeforeEach
    public  void setUp() {
        openMocks(this);
        createPatientActivity = new CreatePatientActivity(patientDao);
    }

    @Test
    void handleRequest() {

        //GIVEN
        CreatePatientRequest createPatientRequest = CreatePatientRequest.builder()
                .withEmail("aoeuaoeu@aoeu.aoeu")
                .withFirstName("Billy")
                .withLastName("Bob")
                .withInsurance("Cigna")
                .withAddress("123 Street Ln")
                .withPhone("000-000-0000")
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

        CreatePatientResult expected = CreatePatientResult.builder()
                .withPatient(patientModel)
                .build();

        when(patientDao.createPatient(any())).thenReturn(patient);

        //WHEN
        CreatePatientResult result = createPatientActivity.handleRequest(createPatientRequest);

        //THEN
        assertEquals(expected.getPatient(), result.getPatient());
    }
}