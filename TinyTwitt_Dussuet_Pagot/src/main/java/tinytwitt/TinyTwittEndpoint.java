package tinytwitt;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;

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
	}
	
	@ApiMethod(
			name="newuser",
			path = "users",
			httpMethod = HttpMethod.PUT
			)
	public void createUser(User u) {
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

}
