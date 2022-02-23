package exceptions; 

public class VerplichtVeldException extends Exception{

		public VerplichtVeldException() {
	    super();
	    }
	    public VerplichtVeldException(String message) {
	        super(message);
	    }
	    public VerplichtVeldException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public VerplichtVeldException(Throwable cause) {
	        super(cause);
	    }

}