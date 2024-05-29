package controller; 

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.AdminDAO;
import DAO.MaConnexion;
import jakarta.servlet.annotation.WebServlet;


public class DeleteProfessorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
        	 MaConnexion connexion= new MaConnexion();
             AdminDAO dao = new AdminDAO(connexion);
            dao.deleteProfessor(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        response.sendRedirect("./admin/prof-liste.jsp");
    }
}
