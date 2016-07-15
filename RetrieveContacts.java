package cazoomiTest101;

import java.io.IOException;
import java.io.StringReader;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@WebServlet("/RetrieveContacts")
public class RetrieveContacts extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.setContentType("text/html;charset=UTF-8");
		
		
	    try {
	    	ClientConfig clientConfig = new ClientConfig();
			 
		    HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("b3b9f5eb79a73e7059d49056e1a4bc40", "Password1!");
		    clientConfig.register( feature) ;
		 
		    
		 
		    Client client = ClientBuilder.newClient( clientConfig );
		    WebTarget webTarget = client.target("https://cazoomi1.highrisehq.com/");
		    
		     
		    String xml=webTarget.path("people").request().accept(MediaType.APPLICATION_XML).get(String.class);
	    	Connection conn=null;
	        Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dbcazoomitest", "root", "");
			Statement stmt=null;
			
			stmt=(Statement) conn.createStatement();
			
	        DocumentBuilderFactory dbf =
	        DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(xml));
	        
	        
	        Document doc = db.parse(is);
	        NodeList nodes = doc.getElementsByTagName("person");
	        
	        //NodeList nodeTag = doc.getElementsByTagName("tags");
	        // iterate the employees
	       
	        for (int i = 0; i < nodes.getLength(); i++)
	        {
	        
	           Element element = (Element) nodes.item(i);
	           NodeList nameTag = element.getElementsByTagName("name");
	           Element nameTagName = (Element) nameTag.item(0);
	           String tag_name =getDetails(nameTagName);
	  
	           NodeList name = element.getElementsByTagName("id");
	           Element line = (Element) name.item(0);
	           String id= getDetails(line);

	           NodeList firstname = element.getElementsByTagName("first-name");
	           line = (Element) firstname.item(0);
	           String fname= getDetails(line);
	           
	           NodeList lastname = element.getElementsByTagName("last-name");
	           line = (Element) lastname.item(0);
	           String lname= getDetails(line);
	           
	           
	           String sql="insert into tags (tag_name) values ('" + tag_name + "')";
		        stmt.executeUpdate(sql);
		        
		        String sqlTag="insert into contacts (c_id,lname,fname,tag_name) values ('"+ id +"','" + lname + "','" + fname +"','" + tag_name + "')";
		        stmt.executeUpdate(sqlTag);
		        
	          
	        }
	        response.getWriter().print("<html><h3>Success...</h3>"
	        		+ "<form action ='ViewContacts' method = 'get'>"
	        		+ "<input type='Submit' value='View Contacts'></input>"
	        		+ "</form></html>");
	    }
	    catch (Exception e) {
	        response.getWriter().println(e);
	    }
	  
	   
           		
	 }
	public static String getDetails(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	       CharacterData cd = (CharacterData) child;
	       return cd.getData();
	    }
	    return "?";
	}
	}


