package demo.errorhandling;

public class DuplicatedClientUsernameException extends Exception{
    public DuplicatedClientUsernameException(String message){
        super(message);
    }
}
