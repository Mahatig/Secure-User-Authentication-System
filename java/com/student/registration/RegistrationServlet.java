package com.student.registration;
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upwd=request.getParameter("pass");
		String reupwd=request.getParameter("re-pass");
		String umobile=request.getParameter("contact");
		RequestDispatcher dispatcher=null;
		if(uname==null||uname.equals(""))
		{
			request.setAttribute("status","Invalid Name");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request,response);

		}
		if(uemail==null||uemail.equals(""))
		{
			request.setAttribute("status","InvalidEmail");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request,response);

		}
		if(upwd==null||upwd.equals(""))
		{
			request.setAttribute("status","InvalidPassword");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request,response);
		}
		else if(!upwd.equals(reupwd))
		{
			request.setAttribute("status","InvalidConfirmPassword");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request,response);
		}
		if(umobile==null||umobile.equals(""))
		{
			request.setAttribute("status","InvalidContactNo");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request,response);

		}
		else if(umobile.length()>10)
		{
			request.setAttribute("status","InvalidContactLength");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request,response);

		}
		Connection con=null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","tiger");
			PreparedStatement pst=con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values (?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			int rowCount=pst.executeUpdate();
			dispatcher=request.getRequestDispatcher("registration.jsp");
			if(rowCount>0)
			{
				request.setAttribute("status","success");
			}
			else
			{
				request.setAttribute("status","failed");
			}
			dispatcher.forward(request, response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

	}

}
