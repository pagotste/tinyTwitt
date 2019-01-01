package tinytwitt;

import com.googlecode.objectify.annotation.*;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
@Cache
public class Hashtag {
	@Id Long idHt; //généré
	@Index String chaine;
	@Parent Key parent;
	
	private Hashtag() {}
	public Hashtag(String s) {
		this.chaine = s;
		this.parent = KeyFactory.createKey("Twitt", "twitt");
	}
	
	public void setChaine(String c) {
		this.chaine = c;
	}
	
	public String getChaine() {
		return this.chaine;
	}
	
}
