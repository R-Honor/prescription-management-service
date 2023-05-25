package prescriptionmanagementservice.exceptions;

public class PrescriptionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 800745345634512851L;

    public PrescriptionNotFoundException() {
    }

    public PrescriptionNotFoundException(String message) {
        super(message);
    }

    public PrescriptionNotFoundException(Throwable cause) {
        super(cause);
    }

    public PrescriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
