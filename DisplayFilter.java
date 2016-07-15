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

/**
 * Servlet implementation class DisplayFilter
 */
@WebServlet("/DisplayFilter")
public class DisplayFilter extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	
	{
		response.setContentType("text/html");
		try{
			
			Connection conn=null;
			Statement sqlSt = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dbcazoomitest", "root", "");
			
			
			
			response.getWriter().print("<html>"
					+ "<form action ='ViewContacts' method ='get'>"
					+ "<title>View Contacts</title>"
					+ "<h3>List of Contacts: "+request.getParameter("tag_name") +"</h3><br>");
			
					sqlSt=(Statement)conn.createStatement();
					String sql="select distinct tag_name,fname,lname,c_id from contacts where tag_name='"+request.getParameter("tag_name")+"'";
					ResultSet rs=sqlSt.executeQuery(sql);
					
					response.getWriter().println( "<input type='Submit' value='Back'></input><br><table border=1>"
							+ "<th>ID</th>"
							+ "<th>Last Name</th>"
							+ "<th>First Name</th>"
							+ "<th>Tag Name</th><tr>");
							
							while(rs.next())
							{
								
								
								response.getWriter().println("<td>"+rs.getString("c_id")+"</td>");
								response.getWriter().println("<td>"+rs.getString("lname")+"</td>");
								response.getWriter().println("<td>"+rs.getString("fname")+"</td>");
								response.getWriter().println("<td>"+rs.getString("tag_name")+"</td>");
							}
							
					response.getWriter().println("</tr></table>"
					+ "</form>"
					+ "</html>"
					+ "");
			
		}catch (Exception e)
		{
			response.getWriter().println(e);
		}
	
	}

}
