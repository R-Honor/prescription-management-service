package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.SearchPatientRequest;
import prescriptionmanagementservice.activity.requests.SearchPatientRequest;
import prescriptionmanagementservice.activity.results.SearchPatientResult;

public class SearchPatientLambda extends LambdaActivityRunner<SearchPatientRequest, SearchPatientResult>
        implements RequestHandler<LambdaRequest<SearchPatientRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchPatientRequest> input, Context context) {
        log.info("Input to the SearchPatientLambda: '{}'", input.toString());

        return super.runActivity(
                () -> input.fromQuery(query -> SearchPatientRequest.builder()
                        .withCriteria(query.get("q"))
                        .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideSearchPatientActivity().handleRequest(request)
        );
    }
}
