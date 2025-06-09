package demo.errorhandling;

public class InactiveClientException extends Exception{
    public InactiveClientException(String message){
        super(message);
    }
}
