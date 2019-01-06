package tinytwitt;

import com.googlecode.objectify.annotation.*;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
@Cache
public class Hashtag {
	@Id Long idHt; //généré
	@Index String chaine;
	
	private Hashtag() {}
	public Hashtag(String s) {
		this.chaine = s;
	}
	
	public void setChaine(String c) {
		this.chaine = c;
	}
	
	public String getChaine() {
		return this.chaine;
	}
	
}
