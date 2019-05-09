//package foo;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.cloud.MessageEndpoint;
//import com.google.appengine.api.blobstore.BlobKey;
//import com.google.appengine.api.blobstore.BlobstoreService;
//import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
//import com.google.appengine.api.datastore.DatastoreFailureException;
//import com.google.appengine.api.datastore.DatastoreService;
//import com.google.appengine.api.datastore.DatastoreServiceFactory;
//import com.google.appengine.api.datastore.Entity;
//import com.google.appengine.api.images.ImagesService;
//import com.google.appengine.api.images.ImagesServiceFactory;
//import com.google.appengine.api.images.ServingUrlOptions;
//import com.google.appengine.api.users.UserService;
//import com.google.appengine.api.users.UserServiceFactory;
//
//public class Petition extends HttpServlet {
//	DatastoreService store;
//
//	@Override
//	public void init() throws ServletException{
//		store = DatastoreServiceFactory.getDatastoreService();
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
//	 */
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//		
//		if(request.getParameter("rechercheUser") != null) {
//			response.sendRedirect("/findUser?pseudo=" + request.getParameter("valueRecherche"));
//		}
//		else if (request.getParameter("recherchePetition") != null) {
//			response.sendRedirect("/timeline?tag=" + request.getParameter("valueRecherche"));
//		}
//		else if (request.getParameter("Signer") != null) {
//			
//			UserService userService = UserServiceFactory.getUserService();
//			String cleFichierUploade = "";
//	        
//			long t1 = System.currentTimeMillis();
//			
//			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
//	        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
//	        List<BlobKey> blobKeys = blobs.get("photoFile");
//	        
//        	try {
//				cleFichierUploade = blobKeys.get(0).getKeyString();
//				urlImage = imagesService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(blobKeys.get(0)));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				cleFichierUploade = "";
//
//			}
//			
//			MessageEndpoint me = new MessageEndpoint();
//			me.createMessage(userService.getCurrentUser().getNickname(), request.getParameter("msg"), cleFichierUploade, urlImage);
//			
//			long t2 = System.currentTimeMillis() - t1;
//			response.sendRedirect("/timeline?tempsCreationMessage=" +  String.valueOf(t2));
//		}
//		
//		
//		
//		
//		
//		Entity petition = new Entity("Petition");
//		petition.setProperty("sender",petitionContent.get("petitionContent_sender"));
//		petition.setProperty("title",petitionContent.get("petitionContent_title"));
//		petition.setProperty("body",petitionContent.get("petitionContent_body"));
//		petition.setProperty("category",petitionContent.get("petitionContent_category"));
//		petition.setProperty("pour",petitionContent.get("petitionContent_pour"));
//		petition.setProperty("contre",petitionContent.get("petitionContent_contre"));
//		
//		try {
//			store.put(petition);
//			String valider = "La pétition est crée : " + petitionContent.get("petitionContent_title");			
//			request.setAttribute("validation", valider);
//		  
//		}
//		catch (DatastoreFailureException e) {
//		    throw new ServletException("Datastore error", e);
//		}
//	}
//
//	@Override
//	public void doGet(HttpServletRequest request, HttpServletResponse response) 
//			throws IOException {
//
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("UTF-8");
//
//		response.getWriter().print("Hello App Engine!\r\n");
//
//	}
//}
