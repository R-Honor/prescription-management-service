package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.DeletePrescriptionRequest;
import prescriptionmanagementservice.activity.results.DeletePrescriptionResult;

public class DeletePrescriptionLambda extends LambdaActivityRunner<DeletePrescriptionRequest, DeletePrescriptionResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeletePrescriptionRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeletePrescriptionRequest> input, Context context) {
        log.info("handleRequest");

        return super.runActivity(
                () -> input.fromPath(path ->
                            DeletePrescriptionRequest.builder()
                                    .withPrescriptionId(path.get("prescriptionId"))
                                    .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideDeletePrescriptionActivity().handleRequest(request)
        );
    }
}
