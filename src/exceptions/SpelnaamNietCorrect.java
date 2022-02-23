package exceptions;

public class SpelnaamNietCorrect extends IllegalArgumentException {
	public SpelnaamNietCorrect() { 
		super(); 
	}
	public SpelnaamNietCorrect(String message) { 
		super(message); 
	}
	public SpelnaamNietCorrect(String message, Throwable cause) { 
		super(message, cause); 
	}
	public SpelnaamNietCorrect(Throwable cause) { 
		super(cause); 
	}

}
