package foo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

@Api(name = "cloud")
public class PetitionEndpoint extends HttpServlet {
	
//	@ApiMethod(
//	        path = "msg/gethashtag/{hashtag}/{limit}",
//	        httpMethod = HttpMethod.GET
//	    )
//	public ArrayList<Petition> getMessageHashtag(@Named("hashtag") String hashtag,@Named("limit") Integer limit){
//		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//		ArrayList<Petition> list = new ArrayList<>();
//        
//		Filter pf = new FilterPredicate("Hashtag", FilterOperator.EQUAL, hashtag);
//		Query q = new Query("MessageData").setFilter(pf).addSort("Date", SortDirection.DESCENDING);
//		q.setKeysOnly();
//
//		PreparedQuery pq = datastore.prepare(q);
//		
//		List<Entity> results = pq.asList(FetchOptions.Builder.withLimit(limit));
//		for (Entity r : results) {
//			try {
//				Entity e = datastore.get(r.getParent());
//				Petition m = new Petition();
//				m.setContenu(e.getProperty("contenu").toString());
//				m.setDate((Date)e.getProperty("date"));
//				m.setParent(e.getParent());
//				list.add(m);
//			} catch (EntityNotFoundException e) {
//				return null;
//			}
//		}
//		return list;
//	}
	
	public List<Entity> queryForge(DatastoreService datastore, String pseudo){
        
		Filter filter = new FilterPredicate("pseudo", FilterOperator.EQUAL, pseudo);
		Query query = new Query("UserCloud").setFilter(filter);
		query.setKeysOnly();
		PreparedQuery prepQuery = datastore.prepare(query);
		List<Entity> results = prepQuery.asList(FetchOptions.Builder.withDefaults());
		
		return results;
		
	}
	
	@ApiMethod(
		name="topTenPet", 
		path = "petition/topten", 
		httpMethod=HttpMethod.GET
	)
	public list<Entity> topTenPet(){
		DatastoreService store = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Petition").addSort("signatures", Query.SortDirection.DESCENDING); 
		PreparedQuery pq = store.prepare(query);
		return pq.asList(FetchOptions.Builder.withLimit(100));

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            DatastoreService store = DatastoreServiceFactory.getDatastoreService();
            Query query = new Query("Petition").addSort("date", SortDirection.DESCENDING);
            List<Entity> results = store.prepare(query).asList(FetchOptions.Builder.withLimit(100));

            this.getServletContext().getRequestDispatcher("../webapp/main.js").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {	
        try {
            DatastoreService store = DatastoreServiceFactory.getDatastoreService();
			Petition pet = new Petition(req.getParameter("owner").toKey(), req.getParameter("contenu"), new Date());
            ofy().save().entity(pet);
            store.put(pet);
            //resp.sendRedirect("/CreatePetition");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@ApiMethod(
	        path = "petition/getuser/{pseudo}/{limit}",
	        httpMethod = HttpMethod.GET
	    )
	public ArrayList<Petition> getPetitionFollow(@Named("pseudo") String pseudo,@Named("limit") Integer limit){
		DatastoreService Store = DatastoreServiceFactory.getDatastoreService();
		List<Entity> results = queryForge(Store, pseudo);
		for (Entity r : results) {
			try {
				Entity e = Store.get(r.getParent());
				Petition m = new Petition();
				m.setContenu(e.getProperty("contenu").toString());
				m.setDate((Date)e.getProperty("date"));
				m.setParent(e.getParent());
				results.add(m);
			} catch (EntityNotFoundException e) {
				return null;
			}
		}
		return list;
	}

	// @ApiMethod(
	//         path = "petition/create",
	//         httpMethod = HttpMethod.POST
	//     )
	// public void createMessage(@Named("owner") String pseudo, @Named("contenu") String contenu,) {
	// 	Date d = new Date();
	// 	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	// 	UserPetitionEndpoint ue = new UserPetitionEndpoint();
		
	// 	Key keyUser = KeyFactory.createKey("User", pseudo);
	// 	Entity m = new Entity("Petition", keyUser);
		
	// 	m.setProperty("contenu",contenu);
	// 	m.setProperty("date",d);
	// 	m.setProperty("signatures",0);
	// 	datastore.put(m);
		
	// 	Entity md = new Entity("PetitionData", m.getKey());
		
	// 	UserPetition u = ue.getUserPet(pseudo);
	// 	md.setProperty("Creator",u.getName());
	// 	md.setProperty("Date",d);
	// 	datastore.put(md);
	// }


	@ApiMethod(
		name="newPet", 
		path="petition/newPet", 
		httpMethod=HttpMethod.POST
	)
	public Entity newPet(@Named("contenu") String contenu, @Named("owner") String owner) {
		Entity e = new Entity("Petition");
		Date d = new Date();
		DatastoreService store = DatastoreServiceFactory.getDatastoreService();
		e.setProperty("contenu", contenu);
		e.setProperty("owner", owner);
		e.setProperty("signatures", 0);
		e.setProperty("date",d);
		store.put(e);
		return e;
		
	}

	@ApiMethod(
		name="getAlllPets", 
		path="petition/getAllPets", 
		httpMethod=HttpMethod.GET
	)
	public List<Entity> getAllPets(){
		DatastoreService store = DatastoreServiceFactory.getDatastoreService();
		Query query=new Query("Petition");
		PreparedQuery pq = store.prepare(query);
		return pq.asList(FetchOptions.Builder.withDefaults());
	}



}