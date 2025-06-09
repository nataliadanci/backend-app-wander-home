package demo.errorhandling;

public class SwitchBookingNotFoundException extends Exception{
    public SwitchBookingNotFoundException(String message){
        super(message);
    }
}
