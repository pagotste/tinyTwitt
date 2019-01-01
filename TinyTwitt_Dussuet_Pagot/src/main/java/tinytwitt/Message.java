package tinytwitt;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

@Entity
@Cache
public class Message {
    @Id Long idMessage; // Sera généré automatiquement
    @Index Date date;
    String message;
    @Index Set<Long> hashtagList = new HashSet<Long>();  
    @Parent Key<User> parent;
    
    private Message() {}
    public Message(String name, String message, Key<User> user) {
        this.message = message;
        this.date = new Date();
        this.parent = user;
    }
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void addHashtag(Long ht) {
    	this.hashtagList.add(ht);
    }
}