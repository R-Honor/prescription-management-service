package prescriptionmanagementservice.dependency;

import dagger.Component;
import prescriptionmanagementservice.activity.*;
import prescriptionmanagementservice.activity.requests.SearchPatientRequest;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Prescription Management Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    ViewPrescriptionActivity provideViewPrescriptionActivity();

    CreatePrescriptionActivity provideCreatePrescriptionActivity();

    UpdatePrescriptionActivity provideUpdatePrescriptionActivity();

    SearchPrescriptionActivity provideSearchPrescriptionActivity();

    DeletePrescriptionActivity provideDeletePrescriptionActivity();



    ViewPatientActivity provideViewPatientActivity();

    CreatePatientActivity provideCreatePatientActivity();

    UpdatePatientActivity provideUpdatePatientActivity();

    DeletePatientActivity provideDeletePatientActivity();

    SearchPatientActivity provideSearchPatientActivity();
}
