package tinytwitt;

import com.googlecode.objectify.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@Entity
@Cache
public class Hashtag {
	@Id String chaine;
	List<Long> posts = new ArrayList<Long>();
	
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
	
	public void addPost(Long id) {
		this.posts.add(id);
	}
	
}
