package exception;

public class SeverErrorException extends RuntimeException {

    public SeverErrorException(String message){
        super(message);
    }

    public SeverErrorException(String message, Throwable cause){
        super(message,cause);
    }

}
