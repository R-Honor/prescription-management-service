package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.ViewPatientRequest;
import prescriptionmanagementservice.activity.results.ViewPatientResult;

public class ViewPatientLambda extends LambdaActivityRunner<ViewPatientRequest, ViewPatientResult>
        implements RequestHandler<LambdaRequest<ViewPatientRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<ViewPatientRequest> input, Context context) {

        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path -> ViewPatientRequest.builder()
                        .withEmail(path.get("email"))
                        .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideViewPatientActivity().handleRequest(request)
        );
    }
}
