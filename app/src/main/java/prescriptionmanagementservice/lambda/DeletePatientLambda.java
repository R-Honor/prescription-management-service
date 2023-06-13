package prescriptionmanagementservice.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.DeletePatientRequest;
import prescriptionmanagementservice.activity.results.DeletePatientResult;

public class DeletePatientLambda extends LambdaActivityRunner<DeletePatientRequest, DeletePatientResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeletePatientRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeletePatientRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        DeletePatientRequest.builder()
                                .withEmail(path.get("email"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideDeletePatientActivity().handleRequest(request)
        );
    }
}
