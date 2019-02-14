package controller;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Users;
import dao.Users_crud;
import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    // Define my two objects
    public Users users;
    public Users_crud users_crud;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // Assigned my two classes in the objects
        try {
            this.users = new Users();
            this.users_crud = new Users_crud();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String add = request.getParameter("add");
        if (request.getParameter("get_id") != null) {
            int site_id = Integer.parseInt(request.getParameter("get_id"));
            if (site_id > 0) {
                try {
                    users = users_crud.readById(site_id);
                    request.setAttribute("users", users);
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/editform.jsp");
                dispatcher.forward(request, response);
                
            }
        }

        //if the form is not submitted
        if (add == null) {
            try {
                List<Users> list = users_crud.read();
                request.setAttribute("list", list);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/viewusers.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            // TODO Auto-generated catch block
        }

    }

    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        String add = request.getParameter("add");
        String delete = request.getParameter("delete");
        String edit = request.getParameter("edit");
        //if the form is submitted
        if (add != null) {
            // Retrieves data from fields
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String sex = request.getParameter("sex");
            String country = request.getParameter("country");
            // Define the fields in the Setter
            users.setName(name);
            users.setPassword(password);
            users.setEmail(email);
            users.setSex(sex);
            users.setCountry(country);
            // Try insert into database
            try {
                users_crud.create(users);
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendRedirect("ajouter.jsp");
            } catch (SQLException e) {
                e.getMessage();
            }
        } else if (delete != null) {
            String id = request.getParameter("id");
            users_crud.delete(Integer.parseInt(id));
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("Servlet");

        } else if (edit != null) {
            int site_id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String sex = request.getParameter("sex");
            String country = request.getParameter("country");

            // Define the fields in the Setter
            // Id inconnu
            users.setId(site_id);
            users.setName(name);
            users.setPassword(password);
            users.setEmail(email);
            users.setSex(sex);
            users.setCountry(country);

            try {
                users_crud.update(users);
            } catch (SQLException ex) {
                Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
  

    }

}
