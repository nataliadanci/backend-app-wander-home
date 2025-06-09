package demo.errorhandling;

public class RealEstateNotFoundException extends Exception{
    public RealEstateNotFoundException(String message){
        super(message);
    }
}
