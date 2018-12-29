package tinytwitt;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import static com.googlecode.objectify.ObjectifyService.ofy;

public class TwittServlet extends HttpServlet {
	
	// Enregistrement de la classe persistable auprès d'Objectif
	static {
		//ObjectifyService.register(x.class);
        ObjectifyService.register(Message.class);
        //ObjectifyService.register(User.class);
        //ObjectifyService.register(Hashtag.class);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// Requête Objectify
			List<Message> messages = ofy().load().type(Message.class).ancestor(KeyFactory.createKey("LivreOr", "livreOr")).order("-date").limit(5).list();
            
            req.setAttribute("messages", messages);
            this.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            
		} catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// Création de l'objet
            Message message = new Message(req.getParameter("name"), req.getParameter("message"));
            // Enregistrement de l'objet dans le Datastore avec Objectify
            ofy().save().entity(message).now();

            resp.sendRedirect("/");
            
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
}
