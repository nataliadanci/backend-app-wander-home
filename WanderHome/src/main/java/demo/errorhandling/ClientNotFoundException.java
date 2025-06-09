package demo.errorhandling;

public class ClientNotFoundException extends Exception{
    public ClientNotFoundException(String message){
        super(message);
    }
}
