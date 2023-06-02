package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.CreatePatientRequest;
import prescriptionmanagementservice.activity.results.CreatePatientResult;

public class CreatePatientLambda extends LambdaActivityRunner<CreatePatientRequest, CreatePatientResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreatePatientRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreatePatientRequest> input, Context context) {
        log.info("Our input: {}", input.toString());

        return super.runActivity(
                () -> {
                    CreatePatientRequest authenticatedRequest = input.fromBody(CreatePatientRequest.class);

                    return CreatePatientRequest.builder()
                            .withEmail(authenticatedRequest.getEmail())
                            .withFirstName(authenticatedRequest.getFirstName())
                            .withLastName(authenticatedRequest.getLastName())
                            .withInsurance(authenticatedRequest.getInsurance())
                            .withPhone(authenticatedRequest.getPhone())
                            .withAddress(authenticatedRequest.getAddress())
                            .build();
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreatePatientActivity().handleRequest(request)
        );
    }
}
