package util;


import java.util.Locale;
import java.util.ResourceBundle; 

public class Taal {
	
	private ResourceBundle bundel; 
	
	public Taal() { 
		int taalKeuze = 0; 
		setBundel(taalKeuze);
	}
	
	public Taal(int taalKeuze) {
		setBundel(taalKeuze);
	}	
	
    public String vertaal(String keyword) {
        return bundel.getString(keyword);
    }
    
    public ResourceBundle getBundel() {
    	return bundel;
    }

	public void setBundel(int taalKeuze) {
		ResourceBundle bundel = null ;
		if (taalKeuze == 1) {
	        bundel = ResourceBundle.getBundle("resourceBundle.tekst", new Locale("nl", "NL"));
	    } else if (taalKeuze == 2) {
	        bundel = ResourceBundle.getBundle("resourceBundle.tekst", new Locale("en", "US"));
	    } else if (taalKeuze == 3) {
	        bundel = ResourceBundle.getBundle("resourceBundle.tekst", new Locale("fr", "FR"));
	    }
		this.bundel = bundel; 
	}

}


