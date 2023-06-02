package prescriptionmanagementservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import prescriptionmanagementservice.activity.requests.UpdatePatientRequest;
import prescriptionmanagementservice.activity.results.UpdatePatientResult;

public class UpdatePatientLambda extends LambdaActivityRunner<UpdatePatientRequest, UpdatePatientResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdatePatientRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdatePatientRequest> input, Context context) {
        log.info("Input to the UpdatePatientLambda: '{}'", input.toString());

        return super.runActivity(
                () -> {
                    UpdatePatientRequest authenticatedRequest = input.fromBody(UpdatePatientRequest.class);

                    return UpdatePatientRequest.builder()
                            .withEmail(authenticatedRequest.getEmail())
                            .withFirstName(authenticatedRequest.getFirstName())
                            .withLastName(authenticatedRequest.getLastName())
                            .withInsurance(authenticatedRequest.getInsurance())
                            .withPhone(authenticatedRequest.getPhone())
                            .withAddress(authenticatedRequest.getAddress())
                            .build();
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdatePatientActivity().handleRequest(request)
        );
    }
}
