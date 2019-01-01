package tinytwitt;

import java.util.HashSet;
import java.util.Set;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.annotation.*;

@Entity
@Cache
public class User {
	@Id Long idUser; //généré
	@Index String name;
	Set<Long> follow = new HashSet<Long>();
	int followers = 0;
	@Parent Key parent;
	
	private User() {} //Objectify
	public User(String name) {
		this.name = name;
		this.parent = KeyFactory.createKey("Twitt", "twitt");
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addFollow(Long id) {
		this.follow.add(id);
	}
	
	public void removeFollow(Long id) {
		this.follow.remove(id);
	}
	
	public int getFollowers() {
		return this.followers;
	}
	
	public void setFollowers(int f) {
		this.followers = f;
	}
	
	
}
