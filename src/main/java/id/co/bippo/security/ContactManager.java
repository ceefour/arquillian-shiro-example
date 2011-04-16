/**
 * 
 */
package id.co.bippo.security;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ceefour
 * For testing security.
 */
@Named @Secured @NamedResource("contact")
public class ContactManager {

	Logger logger = LoggerFactory.getLogger(ContactManager.class);
	String name = "Hendy Irawan";
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
