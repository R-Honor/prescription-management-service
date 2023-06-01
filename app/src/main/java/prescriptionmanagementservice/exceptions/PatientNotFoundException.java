package prescriptionmanagementservice.exceptions;

public class PatientNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 800745334554512851L;

    public PatientNotFoundException() {
    }

    public PatientNotFoundException(String message) {
        super(message);
    }

    public PatientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PatientNotFoundException(Throwable cause) {
        super(cause);
    }
}
