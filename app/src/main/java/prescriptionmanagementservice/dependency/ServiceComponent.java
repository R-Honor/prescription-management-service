package prescriptionmanagementservice.dependency;

import dagger.Component;
import prescriptionmanagementservice.activity.*;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
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
}
