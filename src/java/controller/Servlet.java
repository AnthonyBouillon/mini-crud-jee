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
        String delete = request.getParameter("delete");
        String edit = request.getParameter("edit");
        String login = request.getParameter("sign_in");
        String add = request.getParameter("add");

        /**
         * Si l'utilisateur a soumis le formulaire d'inscription, redirection
         * vers la page de connexion
         */
        if (add != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }

        /**
         * Si l'utilisateur à soumis le formulaire de connexion et que c'est
         * correct, redirection vers la liste des utilisateurs
         */
        if (login != null) {
            // Données du formulaire
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            try {
                // Récupère les informations de l'utilisateur via son email
                users = users_crud.getLogin(email);
                // Si l'email existe et que le mot de passe correspond => session + redirection avec liste 
                if (users.getEmail() != null && users.getPassword() != null && users.getPassword().equals(password)) {
                    HttpSession session_username = request.getSession();
                    String username = users.getName();
                    session_username.setAttribute("session_username", username);
                    List<Users> list = users_crud.read();
                    request.setAttribute("list", list);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/viewusers.jsp");
                    dispatcher.forward(request, response);
                } else {
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.sendRedirect("login.jsp");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        /**
         * Si l'url contient l'identifiant de l'utilisateur => récupération de
         * ses données + redirection vers le formulaire de modification
         */
        if (request.getParameter("get_id") != null) {
            int id_url = Integer.parseInt(request.getParameter("get_id"));
            try {
                users = users_crud.readById(id_url);
                request.setAttribute("users", users);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/editform.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        // Si il a juste cliquer sur le lien pour voir la liste => chargement de la liste 
        if (add == null && edit == null && login == null && delete == null) {
            try {
                List<Users> list = users_crud.read();
                request.setAttribute("list", list);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/viewusers.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
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
                httpResponse.sendRedirect("login.jsp");
            } catch (SQLException e) {
                e.getMessage();
            }
        } else if (delete != null) {
            String id = request.getParameter("id");
            users_crud.delete(Integer.parseInt(id));
            try {
                users_crud.update(users);
                List<Users> list = users_crud.read();
                request.setAttribute("list", list);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/viewusers.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else if (edit != null) {
            int id_url = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String sex = request.getParameter("sex");
            String country = request.getParameter("country");

            // Define the fields in the Setter
            // Id inconnu
            users.setId(id_url);
            users.setName(name);
            users.setPassword(password);
            users.setEmail(email);
            users.setSex(sex);
            users.setCountry(country);
            try {
                users_crud.update(users);
                List<Users> list = users_crud.read();
                request.setAttribute("list", list);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/viewusers.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

}
