package tinytwitt;

import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.annotation.*;

@Entity
@Cache
public class Message {
    @Id Long id; // Sera généré automatiquement
    @Index Date date;
    String name;
    String message;
    
    @Parent Key parent;
    
    private Message() {}
    public Message(String name, String message) {
        this.name = name;
        this.message = message;
        this.date = new Date();
        this.parent = KeyFactory.createKey("LivreOr", "livreOr");
    }
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}