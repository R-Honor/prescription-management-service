package prescriptionmanagementservice.activity.results;

import prescriptionmanagementservice.models.PrescriptionModel;

import java.util.ArrayList;
import java.util.List;

public class SearchPrescriptionResult {

    private final List<PrescriptionModel> prescriptions;

    public SearchPrescriptionResult(List<PrescriptionModel> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<PrescriptionModel> getPrescriptions() {
        return prescriptions;
    }

    @Override
    public String toString() {
        return "SearchPrescriptionResult{" +
                "prescriptions=" + prescriptions +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<PrescriptionModel> prescriptions;

        public Builder withPrescriptions(List<PrescriptionModel> prescriptions) {
            this.prescriptions = new ArrayList<>(prescriptions);
            return this;
        }

        public SearchPrescriptionResult build() {
            return new SearchPrescriptionResult(prescriptions);
        }
    }

}
