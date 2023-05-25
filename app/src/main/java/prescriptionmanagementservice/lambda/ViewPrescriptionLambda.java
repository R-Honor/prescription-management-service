package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.ViewPrescriptionRequest;
import prescriptionmanagementservice.activity.results.ViewPrescriptionResult;

public class ViewPrescriptionLambda extends LambdaActivityRunner<ViewPrescriptionRequest, ViewPrescriptionResult>
        implements RequestHandler<LambdaRequest<ViewPrescriptionRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<ViewPrescriptionRequest> input, Context context) {

        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path -> ViewPrescriptionRequest.builder()
                        .withPrescriptionId(path.get("prescriptionId"))
                        .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideViewPrescriptionActivity().handleRequest(request)
        );
    }
}
