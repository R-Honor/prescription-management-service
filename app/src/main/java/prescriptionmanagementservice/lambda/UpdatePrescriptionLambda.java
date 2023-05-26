package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.UpdatePrescriptionRequest;
import prescriptionmanagementservice.activity.results.UpdatePrescriptionResult;

public class UpdatePrescriptionLambda extends LambdaActivityRunner<UpdatePrescriptionRequest, UpdatePrescriptionResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdatePrescriptionRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdatePrescriptionRequest> input, Context context) {
        log.info("Input to the UpdatePrescriptionLambda: '{}'", input.toString());

        return super.runActivity(
                () -> {
                    UpdatePrescriptionRequest authenticatedRequest = input.fromBody(UpdatePrescriptionRequest.class);

                    return UpdatePrescriptionRequest.builder()
                            .withPrescriptionId(authenticatedRequest.getPrescriptionId())
                            .withDose(authenticatedRequest.getDose())
                            .withSigCode(authenticatedRequest.getSigCode())
                            .withNotes(authenticatedRequest.getNotes())
                            .withLastFillDate(authenticatedRequest.getLastFillDate())
                            .withExpirationDate(authenticatedRequest.getExpirationDate())
                            .withRefills(authenticatedRequest.getRefills())
                            .withStatus(authenticatedRequest.getStatus())
                            .build();
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdatePrescriptionActivity().handleRequest(request)
        );
    }
}
