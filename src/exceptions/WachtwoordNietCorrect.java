package exceptions;

public class WachtwoordNietCorrect extends IllegalArgumentException{
	    public WachtwoordNietCorrect() {
	        super();
	    }
	    public WachtwoordNietCorrect(String message) {
	        super(message);
	    }
	    public WachtwoordNietCorrect(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public WachtwoordNietCorrect(Throwable cause) {
	        super(cause);
	    }

}
