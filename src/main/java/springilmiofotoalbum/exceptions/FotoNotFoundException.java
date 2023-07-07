package springilmiofotoalbum.exceptions;

public class FotoNotFoundException extends RuntimeException {

    public FotoNotFoundException(String message) {
        super(message);
    }
}
