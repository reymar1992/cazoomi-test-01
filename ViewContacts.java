package cazoomiTest101;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@WebServlet("/ViewContacts")
public class ViewContacts extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		try{
			
			Connection conn=null;
			Statement sqlSt = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dbcazoomitest", "root", "");
			
			
			
			response.getWriter().print("<html>"
					+ "<form action ='DisplayFilter' method ='get'>"
					+ "<title>View Contacts</title>"
					+ "<h3>List of Contacts: Filter by Tags</h3><br>Select tags:");
			
					sqlSt=(Statement)conn.createStatement();
					String sql="select distinct tag_name from tags";
					ResultSet rs=sqlSt.executeQuery(sql);
					
					response.getWriter().println( "<select name = 'tag_name'>");
							while(rs.next())
							{
								
								
								response.getWriter().println("<option value='"+rs.getString("tag_name")+"'>"+rs.getString("tag_name"));
							}
							
					response.getWriter().println("</option></select><input type='Submit' value='Search'></input>"
					+ "</form>"
					+ "</html>"
					+ "");
			
		}catch (Exception e)
		{
			response.getWriter().println(e);
		}
	}

}
