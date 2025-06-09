package demo.errorhandling;

public class RentalNotFoundException extends Exception{
    public RentalNotFoundException(String message){
        super(message);
    }
}
