package exeptions;

import responses.Response;

public class DsmException extends RuntimeException {

    public DsmException(String errorMessage) {
        super(errorMessage);
    }

    public DsmException(String errorMessage, Object... args) {
        super(String.format(errorMessage, args));
    }

    public DsmException(String errorMessage, Throwable errCause) {
        super(errorMessage, errCause);
    }

    public DsmException(Exception e) {
        super(e);
    }

    public DsmException(Response.Error error) {
        super("MainErrorCode= "+error.getCode()+" Desciption= "+ error.getDescription().orElse("") + " Error= "+error.getErrors());
    }
}
