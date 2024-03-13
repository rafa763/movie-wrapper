package tech.xserver.xmovies.exceptions;

public class BadReqException extends RuntimeException {
    public BadReqException(String message) {
        super(message);
    }
}
