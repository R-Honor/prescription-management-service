package prescriptionmanagementservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.activity.requests.UpdatePatientRequest;
import prescriptionmanagementservice.activity.results.UpdatePatientResult;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;
import prescriptionmanagementservice.models.PatientModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdatePatientActivityTest {

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private UpdatePatientActivity updatePatientActivity;


    @BeforeEach
    public  void setUp() {
        openMocks(this);
        updatePatientActivity = new UpdatePatientActivity(patientDao);
    }

    @Test
    void handleRequest() {

        //GIVEN
        UpdatePatientRequest updatePatientRequest = UpdatePatientRequest.builder()
                .withEmail("aoeuaoeu@aoeu.aoeu")
                .withFirstName("Kitty")
                .withLastName("Cat")
                .withInsurance("Catsurance")
                .withAddress("789 Cat St")
                .withPhone("123-456-7890")
                .build();

        PatientModel patientModel = PatientModel.builder()
                .withEmail("aoeuaoeu@aoeu.aoeu")
                .withFirstName("Kitty")
                .withLastName("Cat")
                .withInsurance("Catsurance")
                .withAddress("789 Cat St")
                .withPhone("123-456-7890")
                .build();

        Patient patient = new Patient();
        patient.setEmail("aoeuaoeu@aoeu.aoeu");
        patient.setFirstName("Billy");
        patient.setLastName("Bob");
        patient.setInsurance("Cigna");
        patient.setAddress("123 Street Ln");
        patient.setPhone("000-000-0000");

        Patient updatedPatient = new Patient();
        updatedPatient.setEmail("aoeuaoeu@aoeu.aoeu");
        updatedPatient.setFirstName("Kitty");
        updatedPatient.setLastName("Cat");
        updatedPatient.setInsurance("Catsurance");
        updatedPatient.setAddress("789 Cat St");
        updatedPatient.setPhone("123-456-7890");

        UpdatePatientResult expected = UpdatePatientResult.builder()
                .withPatient(patientModel)
                .build();

        when(patientDao.viewPatient(any())).thenReturn(patient);
        when(patientDao.savePatient(any())).thenReturn(updatedPatient);

        //WHEN
        UpdatePatientResult result = updatePatientActivity.handleRequest(updatePatientRequest);

        //THEN
        assertEquals(expected.getPatient(), result.getPatient());
    }
}