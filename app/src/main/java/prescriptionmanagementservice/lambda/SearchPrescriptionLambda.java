package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.SearchPrescriptionRequest;
import prescriptionmanagementservice.activity.results.SearchPrescriptionResult;

public class SearchPrescriptionLambda extends LambdaActivityRunner<SearchPrescriptionRequest, SearchPrescriptionResult>
        implements RequestHandler<LambdaRequest<SearchPrescriptionRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchPrescriptionRequest> input, Context context) {
        log.info("Input to the SearchPrescriptionLambda: '{}'", input.toString());

        return super.runActivity(
                () -> input.fromQuery(query -> SearchPrescriptionRequest.builder()
                        .withCriteria(query.get("q"))
                        .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideSearchPrescriptionActivity().handleRequest(request)
        );
    }
}
