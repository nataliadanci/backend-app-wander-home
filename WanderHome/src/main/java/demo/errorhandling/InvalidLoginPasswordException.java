package demo.errorhandling;

public class InvalidLoginPasswordException extends Exception{
    public InvalidLoginPasswordException(String message){
        super(message);
    }
}
