package tinytwitt;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public Message(String message, Key<User> user) {
        this.message = message;
        this.date = new Date();
        this.parent = user;
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(String d) {
    	Date date = null;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
		try {
			date = DATE_FORMAT.parse(d);
			this.date = date;
		} catch (ParseException e) {
		}
    }

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void addHashtag(Long ht) {
    	this.hashtagList.add(ht);
    }
    
    public void setParent(Key<User> p) {
    	this.parent = p;
    }
    
    public String getParentString(){
    	return this.parent.toWebSafeString();
    }
    
}