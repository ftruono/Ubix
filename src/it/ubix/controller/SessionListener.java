package it.ubix.controller;

import java.util.HashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import it.ubix.model.Utente;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    private static HashMap<Integer,Integer> map; //id , n° sessioni attive 
    public SessionListener() {
        map=new HashMap<>();
    }

	

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
    }

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		String attr=event.getName();
        if(attr.equals("user")) {
        	HttpSession session = event.getSession();
        	Utente ut=(Utente)session.getAttribute("user");
	        refreshMap(ut.getIdutente(), 0);
        }
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		String attr=event.getName();
		if(attr.equals("user")) {
			HttpSession session = event.getSession();
        	Utente ut=(Utente)event.getValue();
        	refreshMap(ut.getIdutente(),1);
			
		}
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private synchronized void refreshMap(int id,int op) {
		switch(op) {
		case 0:{
			if(map.containsKey(id)) {
	        	int value=map.get(id)+1;
	        	map.put(id, value);
	        }else {
	        	map.put(id, 1);
	        }
			break;
		}
		case 1:{
			if(map.containsKey(id)) {
	        	int value=map.get(id)-1;
	        	map.put(id, value);
	        }
			
		  break;	
		}
		
		}
			
		
	}
	
	public static synchronized int getSize(int id) {
		return map.get(id);
	}
	
}
