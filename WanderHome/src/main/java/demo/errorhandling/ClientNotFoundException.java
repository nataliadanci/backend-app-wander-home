package demo.errorhandling;

import demo.entity.Client;

public class ClientNotFoundException extends Exception{
    public ClientNotFoundException(String message){
        super(message);
    }
}
