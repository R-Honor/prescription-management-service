package prescriptionmanagementservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.activity.requests.SearchPatientRequest;
import prescriptionmanagementservice.activity.results.SearchPatientResult;
import prescriptionmanagementservice.converters.ModelConverter;
import prescriptionmanagementservice.dynamodb.PatientDao;
import prescriptionmanagementservice.dynamodb.models.Patient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SearchPatientActivityTest {

    @Mock
    private PatientDao patientDao;

    @InjectMocks
    private SearchPatientActivity searchPatientActivity;


    @BeforeEach
    public  void setUp() {
        openMocks(this);
        searchPatientActivity = new SearchPatientActivity(patientDao);
    }
    @Test
    void handleRequest() {

        SearchPatientRequest searchPatientRequest = SearchPatientRequest.builder()
                .withCriteria("Bob+Billy")
                .build();

        Patient patient1 = new Patient();
        patient1.setEmail("aoeuaoeu@aoeu.aoeu");
        patient1.setFirstName("Billy");
        patient1.setLastName("Bob");
        patient1.setInsurance("Cigna");
        patient1.setAddress("123 Street Ln");
        patient1.setPhone("000-000-0000");

        Patient patient2 = new Patient();
        patient2.setEmail("aoeuaoeu@aoeu.aoeu");
        patient2.setFirstName("Billy");
        patient2.setLastName("Bob");
        patient2.setInsurance("Cigna");
        patient2.setAddress("123 Street Ln");
        patient2.setPhone("000-000-0000");

        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient1);
        patientList.add(patient2);

        when(patientDao.searchPatients(any())).thenReturn(patientList);

        //WHEN
        SearchPatientResult result = searchPatientActivity.handleRequest(searchPatientRequest);

        //THEN
        assertEquals(new ModelConverter().toPatientModelList(patientList), result.getPatients());
    }
}