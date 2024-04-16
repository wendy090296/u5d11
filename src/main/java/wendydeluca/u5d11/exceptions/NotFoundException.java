package wendydeluca.u5d11.exceptions;

import lombok.Getter;

import java.util.UUID;
@Getter
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(UUID id){
        super("The element with id" + id + "has not been found!");
    }


}
