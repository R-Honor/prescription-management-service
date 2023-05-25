package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.CreatePrescriptionRequest;
import prescriptionmanagementservice.activity.results.CreatePrescriptionResult;

public class CreatePrescriptionLambda extends LambdaActivityRunner<CreatePrescriptionRequest, CreatePrescriptionResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreatePrescriptionRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreatePrescriptionRequest> input, Context context) {
        log.info("Our input: {}", input.toString());

        return super.runActivity(
                () -> {
                    CreatePrescriptionRequest authenticatedRequest = input.fromBody(CreatePrescriptionRequest.class);
                    System.out.println("In lambda building request");
                    return CreatePrescriptionRequest.builder()
                            .withPrescriptionId(authenticatedRequest.getPrescriptionId())
                            .withEmail(authenticatedRequest.getEmail())
                            .withDrug(authenticatedRequest.getDrug())
                            .withDose(authenticatedRequest.getDose())
                            .withSigCode(authenticatedRequest.getSigCode())
                            .withNotes(authenticatedRequest.getNotes())
                            .withRefills(authenticatedRequest.getRefills())
                            .withLastFillDate(authenticatedRequest.getLastFillDate())
                            .withExpirationDate(authenticatedRequest.getExpirationDate())
                            .withStatus(authenticatedRequest.getStatus())
                            .build();
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreatePrescriptionActivity().handleRequest(request)
        );
    }
}
