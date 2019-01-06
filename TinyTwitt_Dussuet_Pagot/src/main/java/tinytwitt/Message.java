package tinytwitt;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.annotation.*;

@Entity
public class Message {
    @Id Long idMessage; // Sera généré automatiquement
    @Index Date date;
    String message;  
    @Index Long usrId;
    
    private Message() {}
    public Message(String message, Long user) {
        this.message = message;
        this.date = new Date();
        this.usrId = user;
    }
    
    public Long getIdMessage() {
    	return this.idMessage;
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
    
    public void setUsrId(Long l) {
    	this.usrId = l;
    }
    
    public Long getUsrId(){
    	return this.usrId;
    }
    
}