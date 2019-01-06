package tinytwitt;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.Query;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import tinytwitt.User;

@Api(
	name= "tinytwitt",
	version="v1",
	description="API pour l'application TinyTwitt"
	)
public class TinyTwittEndpoint {
	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Message.class);
		ObjectifyService.register(Hashtag.class);
	}
	
	//Methodes pour les utilisateurs
	@ApiMethod(
			name="newuser",
			path = "users/{nom}",
			httpMethod = HttpMethod.PUT
			)
	public void createUser(User u,@Named("nom") String n) {
		u.setName(n);
		ofy().save().entity(u);
	}
	
	
	@ApiMethod(
			name="listusers",
			path="users",
			httpMethod = HttpMethod.GET
	)
    public List<User> listUsers() {
		List<User> lu = ofy().load().type(User.class).list();
		return lu;
    }
	
	@ApiMethod(
			name="getuser",
			path="users/{id}",
			httpMethod = HttpMethod.GET
			)
	public User getUser(@Named("id") Long n) {
		User u = ofy().load().type(User.class).id(n).now();
		return u;
	}
	
	//Methodes pour les follows
	@ApiMethod(
			name="addfollow",
			path="users/{id}/follows/{user}",
			httpMethod = HttpMethod.PUT
			)
	public void followUser(@Named("id") Long n, @Named("user") Long u) {
		User user = ofy().load().type(User.class).id(n).now();
		User follow = ofy().load().type(User.class).id(u).now();
		if(!user.follow.contains(u) && n != u) {
			follow.setFollowers(follow.getFollowers()+1);
			user.addFollow(u);
			ofy().save().entities(follow,user).now();
		} 
	}
	
	@ApiMethod(
			name="unfollow",
			path="users/{id}/unfollow/{user}",
			httpMethod = HttpMethod.PUT
			)
	public void unfollowUser(@Named("id") Long n, @Named("user") Long u) {
		User user = ofy().load().type(User.class).id(n).now();
		User follow = ofy().load().type(User.class).id(u).now();
		if(user.follow.contains(u) && n != u) {
			follow.setFollowers(follow.getFollowers()-1);
			user.removeFollow(u);
			ofy().save().entities(follow,user).now();
		} 
	}
	
	@ApiMethod(
			name="listfollow",
			path="users/{id}/follows",
			httpMethod = HttpMethod.GET
			)
	public List<User> listFollows(@Named("id") Long n){
		User user = ofy().load().type(User.class).id(n).now();
		List<User> lu = new ArrayList<User>();
		for (Long l : user.follow) {
			lu.add(ofy().load().type(User.class).id(l).now());
		}
		return lu;
	}
	
	//Methodes pour les messages
	@ApiMethod(
			name="postmessage",
			path="messages",
			httpMethod = HttpMethod.PUT
			)
	public void createMessage(Message m) {
		List<Hashtag> htl = new ArrayList<Hashtag>();
		String[] hashtags = m.getMessage().split("#");
		if(hashtags.length>1) {
			ofy().save().entity(m).now();
			for(int i = 1; i<hashtags.length;i++) {
				Hashtag h = ofy().load().type(Hashtag.class).id("#"+hashtags[i]).now();
				if(h == null ) h = new Hashtag("#"+hashtags[i]);
				h.addPost(m.getIdMessage());
				htl.add(h);
			}
			
			ofy().save().entities(htl).now();
		} else {
			ofy().save().entity(m).now();
		}
		
	}
	
	@ApiMethod(
			name="listmessages",
			path="messages/{id}",
			httpMethod = HttpMethod.GET
	)
	public List<Message> listMessages(@Named("id") Long id){
		Key<User> k = Key.create(User.class,id);
		User u = ofy().load().key(k).now();
		List<Long> users = new ArrayList<Long>();
		users.add(id);
		users.addAll(u.follow);
		List<Message> lm = ofy().load().type(Message.class).filter("usrId in", users).order("-date").list();
		return lm;
	}
	
	//Methodes pour hashtags
	@ApiMethod(
			name="consulterhashtag",
			path="hashtags/{id}",
			httpMethod = HttpMethod.GET
			)
	public List<Message> consulterHashtag(@Named("id") String id){
		Hashtag h = ofy().load().type(Hashtag.class).id(id).now();
		List<Message> lm = new ArrayList<Message>();
		for (Long l : h.posts) {
			lm.add(ofy().load().type(Message.class).id(l).now());
		}
		return lm;
	}
}
