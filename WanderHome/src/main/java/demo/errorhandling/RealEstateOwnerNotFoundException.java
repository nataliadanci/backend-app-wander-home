package demo.errorhandling;

public class RealEstateOwnerNotFoundException extends Exception{
    public RealEstateOwnerNotFoundException(String message){
        super(message);
    }
}
