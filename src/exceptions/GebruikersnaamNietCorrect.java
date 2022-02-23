package exceptions; 

public class GebruikersnaamNietCorrect extends Exception{

	    public GebruikersnaamNietCorrect() {
	    super();
	    }
	    public GebruikersnaamNietCorrect(String message) {
	        super(message);
	    }
	    public GebruikersnaamNietCorrect(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public GebruikersnaamNietCorrect(Throwable cause) {
	        super(cause);
	    }

}
