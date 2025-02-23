package com.student.registration;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;




/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		HttpSession session=request.getSession();
		RequestDispatcher dispatcher=null;
		if(uemail==null||uemail.equals(""))
		{
			request.setAttribute("status","Invalid Email");
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);

		}
		if(upwd==null||upwd.equals(""))
		{
			request.setAttribute("status","Invalid Password");
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
		}
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","tiger");
			PreparedStatement pst=con.prepareStatement("select *  from users where uemail=? and upwd=? ");
			pst.setString(1, uemail);
			pst.setString(2, upwd);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				session.setAttribute("name", rs.getString("uname"));
				dispatcher=request.getRequestDispatcher("index.jsp");
			}
			else
			{
				request.setAttribute("status", "failed");
				dispatcher=request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request,response);
		}
		catch(Exception e)
		{
			
		}
	}

}
