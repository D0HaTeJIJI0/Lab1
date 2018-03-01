package travnich.exceptions;

public class WrongMarkException extends Throwable {

    private static final String message = "Mark is not in the range (1 - 10)!";

    public WrongMarkException(){

        super(message);

    }

}
