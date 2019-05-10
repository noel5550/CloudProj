package foo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class PetitionEndpoint {
	
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
		q.setKeysOnly();
		PreparedQuery prepQuery = datastore.prepare(query);
		List<Entity> results = prepQuery.asList(FetchOptions.Builder.withDefaults());
		
		return results;
		
	}
	
	@ApiMethod(
	        path = "petition/getuser/{pseudo}/{limit}",
	        httpMethod = HttpMethod.GET
	    )
	public ArrayList<Petition> getPetitionFollow(@Named("pseudo") String pseudo,@Named("limit") Integer limit){
		DatastoreService Store = DatastoreServiceFactory.getDatastoreService();
		List<Entity> results = queryForge(store, pseudo);
		for (Entity r : results) {
			try {
				Entity e = Store.get(r.getParent());
				Petition m = new Petition();
				m.setContenu(e.getProperty("contenu").toString());
				m.setDate((Date)e.getProperty("date"));
				m.setParent(e.getParent());
				list.add(m);
			} catch (EntityNotFoundException e) {
				return null;
			}
		}
		return list;
	}

	@ApiMethod(
	        path = "petition/create",
	        httpMethod = HttpMethod.POST
	    )
	public void createMessage(@Named("owner") String pseudo, @Named("contenu") String contenu,) {
		Date d = new Date();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		UserPetitionEndpoint ue = new UserPetitionEndpoint();
		
		Key keyUser = KeyFactory.createKey("User", pseudo);
		Entity m = new Entity("Petition", keyUser);
		
		m.setProperty("contenu",contenu);
		m.setProperty("date",d);
		m.setProperty("signatures",0);
		datastore.put(m);
		
		Entity md = new Entity("PetitionData", m.getKey());
		
		UserPetition u = ue.getUserPet(pseudo);
		md.setProperty("Creator",u.getName());
		md.setProperty("Date",d);
		datastore.put(md);
	}
}