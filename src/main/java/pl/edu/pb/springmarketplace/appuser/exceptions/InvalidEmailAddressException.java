package pl.edu.pb.springmarketplace.appuser.exceptions;

public class InvalidEmailAddressException extends RuntimeException {
    public InvalidEmailAddressException (String message) {
        super(message);
    }
}

