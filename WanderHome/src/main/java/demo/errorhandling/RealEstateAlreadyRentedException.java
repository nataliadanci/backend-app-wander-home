package demo.errorhandling;

public class RealEstateAlreadyRentedException extends Exception{
    public RealEstateAlreadyRentedException(String message){
        super(message);
    }
}
