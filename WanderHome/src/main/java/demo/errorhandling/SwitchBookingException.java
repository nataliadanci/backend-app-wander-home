package demo.errorhandling;

import demo.entity.SwitchBooking;

public class SwitchBookingException extends Exception{
    public SwitchBookingException(String message){
        super(message);
    }
}
