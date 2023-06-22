package prescriptionmanagementservice.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import prescriptionmanagementservice.activity.requests.SearchPrescriptionRequest;
import prescriptionmanagementservice.activity.results.SearchPrescriptionResult;
import prescriptionmanagementservice.converters.ZonedDateTimeConverter;
import prescriptionmanagementservice.dynamodb.PrescriptionDao;
import prescriptionmanagementservice.dynamodb.models.Prescription;
import prescriptionmanagementservice.models.PrescriptionStatusEnum;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class SearchPrescriptionActivityTest {

    @Mock
    private PrescriptionDao prescriptionDao;

    @InjectMocks
    private SearchPrescriptionActivity searchPrescriptionActivity;


    @BeforeEach
    public  void setUp() {
        openMocks(this);
        searchPrescriptionActivity = new SearchPrescriptionActivity(prescriptionDao);
    }
    @Test
    void handleRequest() {

        SearchPrescriptionRequest searchPrescriptionRequest = SearchPrescriptionRequest.builder()
                .withCriteria("aoeuaoeu@aoeu.aoeu+PENDING")
                .build();

        Prescription prescription1 = new Prescription();
        prescription1.setEmail("aoeuaoeu@aoeu.aoeu");
        prescription1.setDrug("Advil");
        prescription1.setDose("100mg");
        prescription1.setSigCode("BID");
        prescription1.setNotes("Cats are cool");
        prescription1.setLastFillDate(new ZonedDateTimeConverter().unconvert("1922-12-30T12:00:00.000Z"));
        prescription1.setExpirationDate(new ZonedDateTimeConverter().unconvert("1991-12-26T12:00:00.000Z"));
        prescription1.setRefills(3);
        prescription1.setStatus(PrescriptionStatusEnum.PENDING);

        Prescription prescription2 = new Prescription();
        prescription2.setEmail("aoeuaoeu@aoeu.aoeu");
        prescription2.setDrug("Advil");
        prescription2.setDose("100mg");
        prescription2.setSigCode("BID");
        prescription2.setNotes("Cats are cool");
        prescription2.setLastFillDate(new ZonedDateTimeConverter().unconvert("1922-12-30T12:00:00.000Z"));
        prescription2.setExpirationDate(new ZonedDateTimeConverter().unconvert("1991-12-26T12:00:00.000Z"));
        prescription2.setRefills(3);
        prescription2.setStatus(PrescriptionStatusEnum.PENDING);


        List<Prescription> prescriptionList = new ArrayList<>();
        prescriptionList.add(prescription1);
        prescriptionList.add(prescription2);

        when(prescriptionDao.searchPrescriptions(any())).thenReturn(prescriptionList);

        //WHEN
        SearchPrescriptionResult result = searchPrescriptionActivity.handleRequest(searchPrescriptionRequest);

        //THEN
        assertEquals(prescriptionList.get(0).getDrug(), result.getPrescriptions().get(0).getDrug());
        assertEquals(prescriptionList.get(0).getDose(), result.getPrescriptions().get(0).getDose());
        assertEquals(prescriptionList.get(0).getEmail(), result.getPrescriptions().get(0).getEmail());
        assertEquals(prescriptionList.get(0).getSigCode(), result.getPrescriptions().get(0).getSigCode());
        assertEquals(prescriptionList.get(0).getNotes(), result.getPrescriptions().get(0).getNotes());
        assertEquals(prescriptionList.get(0).getRefills(), result.getPrescriptions().get(0).getRefills());
        assertEquals(prescriptionList.get(0).getStatus(), result.getPrescriptions().get(0).getStatus());
    }
}