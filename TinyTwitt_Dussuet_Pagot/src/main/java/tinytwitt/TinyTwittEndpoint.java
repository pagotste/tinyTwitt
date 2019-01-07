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
	
	//TODO : update quand angularjs fonctionnel
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
			path="messages/{contenu}/{auteur}/{date}",
			httpMethod = HttpMethod.POST
			)
	public void createMessage(@Named("contenu") String c,@Named("auteur") Long n,@Named("date") String d) {
		Message m = new Message(c,n);
		m.setDate(d);
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
			path="messages/{id}/{nbposts}",
			httpMethod = HttpMethod.GET
	)
	public List<Message> listMessages(@Named("id") Long id, @Named("nbposts") int nb){
		Key<User> k = Key.create(User.class,id);
		User u = ofy().load().key(k).now();
		List<Long> users = new ArrayList<Long>();
		users.add(id);
		users.addAll(u.follow);
		List<Message> lm = ofy().load().type(Message.class).filter("usrId in", users).order("-date").limit(nb).list();
		return lm;
	}
	
	//Methodes pour hashtags
	@ApiMethod(
			name="consulterhashtag",
			path="hashtags/{id}/{nbposts}",
			httpMethod = HttpMethod.GET
			)
	public List<Message> consulterHashtag(@Named("id") String id, @Named("nbposts") int nb){
		Hashtag h = ofy().load().type(Hashtag.class).id(id).now();
		List<Message> lm = new ArrayList<Message>();
		int i = h.posts.size()-1;
		while (i >= h.posts.size()-nb && i >=0 ) {
			lm.add(ofy().load().type(Message.class).id(h.posts.get(i)).now());
			i--;
		}
		return lm;
	}
	
	//Methodes de test
	
	//TODO: update quand createuser a jour
	@ApiMethod(
            name="createmultipleusers",
            path = "users/ajout/{nb}",
            httpMethod = HttpMethod.PUT
            )
    public void createMultipleUsers(@Named("nb") Integer n) {
		List<User> lu = new ArrayList<User>();
        for(int i = 0; i< n ; i++) {
        	User u = new User("user"+i);
        	lu.add(u);
        }
        ofy().save().entities(lu).now();
    }
	
	@ApiMethod(
            name="addmultiplefollow",
            path="/users/{user}/follows/{nb}",
            httpMethod = HttpMethod.PUT
            )
    public void addMultiplefollowUser(@Named("user") Long n,@Named("nb") int nombre) {
        User user = ofy().load().type(User.class).id(n).now();
        List<User> lf = new ArrayList<User>();
        for(int i=0 ; i <nombre ; i++) {
            User follow = new User("testFollow"+i);
            follow.setFollowers(1);
            lf.add(follow);
        }
        ofy().save().entities(lf).now();
        for(User u : lf) {
        	user.addFollow(u.getId());
        }
        ofy().save().entity(user).now();
    }
	
	@ApiMethod(
            name="addmultiplefollowers",
            path="/users/{user}/followers/{nb}",
            httpMethod = HttpMethod.PUT
            )
    public void addMultiplefollowersUser(@Named("user") Long n,@Named("nb") int nombre) {
        User user = ofy().load().type(User.class).id(n).now();
        List<User> lf = new ArrayList<User>();
        for(int i=0 ; i <nombre ; i++) {
            User follower = new User("testFollower"+i);
            follower.addFollow(n);
            lf.add(follower);
        }
        ofy().save().entities(lf).now();        
        user.setFollowers(user.getFollowers()+nombre);
        ofy().save().entity(user).now();
    }
    
	@ApiMethod(
			name = "createmultiplemessages",
			path="messages/ajout/{id}/{nb}",
			httpMethod = HttpMethod.PUT
			)
	public void addMultipleMessages(@Named("id") Long id, @Named("nb") int nb) {
		List<Message> lm = new ArrayList<Message>();
		for (int i = 0; i < nb ; i++) {
			Message m = new Message("message"+i,id);
			lm.add(m);
		}
		ofy().save().entities(lm).now();
	}

	@ApiMethod(
			name = "createmultiplemessagesforhashtag",
			path="messages/{id}/{nb}",
			httpMethod = HttpMethod.PUT
			)
	public void addMultipleMessagesHashtag(@Named("id") Long id, @Named("nb") int nb) {
		List<Message> lm = new ArrayList<Message>();
		Hashtag h = new Hashtag("#test");
		for (int i = 0; i < nb ; i++) {
			Message m = new Message("message"+i+" #test",id);
			lm.add(m);
		}
		ofy().save().entities(lm).now();
		for (Message mess : lm) {
			h.addPost(mess.getIdMessage());
		}
		ofy().save().entity(h).now();		
	}

	@ApiMethod(
			name = "deleteall",
			path="",
			httpMethod = HttpMethod.DELETE
			)
	public void deleteAll() {
		List<Message> lm = ofy().load().type(Message.class).list();
		List<User> lu =  ofy().load().type(User.class).list();
		List<Hashtag> lh =  ofy().load().type(Hashtag.class).list();
		ofy().delete().entities(lh).now();
		ofy().delete().entities(lm).now();
		ofy().delete().entities(lu).now();
	}
}
