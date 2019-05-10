package foo;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

public class Petition implements java.io.Serializable {
	String contenu;
	Key owner;
	Date date;
	int signatures;
	
	public Petition () {
		this.contenu = "";
		this.owner = null;
		this.date = null;
		signatures = 0;
	}
	
	public Petition (String contenu, Key cle) {
		this.contenu = contenu;
		this.owner = cle;
	}
	
	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Key getParent() {
		return owner;
	}
	
	public int getSignatures() {
		return signatures;
	}
	
	public void setSignatures(int sig) {
		this.signatures = sig;
	}

	public void setParent(Key parent) {
		this.owner = parent;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

//	public static ArrayList<String> getHashtag(String msg) {
//		ArrayList<String> list = new ArrayList<String>();
//		if(msg.contains("#")) {
//			String s= "";
//			Boolean currentHashtag = false;
//			for(int i = 0; i < msg.length(); i++) {
//				if(currentHashtag) {
//					if(msg.charAt(i) == ' ') {
//						currentHashtag = false;
//						list.add(s);
//						s ="";
//					}else if (msg.charAt(i) == '#') {
//						list.add(s);
//						s ="";
//					}else {
//						s += msg.charAt(i);
//					}
//				}
//				else if (msg.charAt(i) == '#') {
//					currentHashtag = true;
//				}
//			}
//			if(currentHashtag) {
//				list.add(s);
//			}
//		}
//		return list;
//	}
}