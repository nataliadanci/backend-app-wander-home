package demo.errorhandling;

import com.mysql.cj.util.DnsSrv;

public class SwitchBookingDateException extends Exception{
    public SwitchBookingDateException(String message){
        super(message);
    }
}
