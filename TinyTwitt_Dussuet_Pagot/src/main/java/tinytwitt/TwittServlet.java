package tinytwitt;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class TwittServlet extends HttpServlet {
	
	// Enregistrement de la classe persistable auprès d'Objectif
	static {
		//ObjectifyService.register(x.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Message.class);
        ObjectifyService.register(Hashtag.class);
	}
	
	//Charge la timeline d'un utilisateur
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// Requête Objectify
			//Recupere les messages postes par l'utilisateur (comment recup l'utilisateur en REST?)
			//TODO: messages postes par l'utilisateur et sa liste de follow
			List<Message> messages = ofy().load().type(Message.class).ancestor(Key.create(User.class,"jean")).order("-date").limit(5).list();
            
            req.setAttribute("messages", messages);
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            
		} catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//Envoyer un message
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			
			if(req.getParameter("message") != null) {
				//On recupère la clé
				Key<User> keyUser = Key.create(User.class,req.getParameter("user"));
				// Création de l'objet
	            Message message = new Message(req.getParameter("message"), keyUser);
	            // Enregistrement de l'objet dans le Datastore avec Objectify
	            ofy().save().entity(message).now();

	            resp.sendRedirect("/");	
			} else if (req.getParameter("nom") != null) {
				//Creer un utilisateur
				User user = new User(req.getParameter("nom"));
				ofy().save().entity(user).now();
				resp.sendRedirect("/");
			}
			
            
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
