package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.AdminDAO;
import DAO.GradebookDAO;
import DAO.MaConnexion;
import business.Professors;
import jakarta.servlet.annotation.WebServlet;


@WebServlet("/add-prof")
public class AddProfessorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
   

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       

    	String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String sex = request.getParameter("sex");
        int age = Integer.parseInt(request.getParameter("age"));
        String cneProf = request.getParameter("cneProf");

        Professors prof = new Professors(0, username, password, name, lastName, address, sex, age, cneProf);
        try {
        	 MaConnexion connexion= new MaConnexion();
             AdminDAO dao = new AdminDAO(connexion);
            dao.addProfessor(prof);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        response.sendRedirect("prof-liste.jsp");
    }
}
