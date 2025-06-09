package demo.errorhandling;

public class ClientAccountNotFoundException extends Exception{
    public ClientAccountNotFoundException(String message){
        super(message);
    }
}
