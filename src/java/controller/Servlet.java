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

/**
 * Servlet implementation class Servlet WebServlet => It is necessary
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

        // Corresponds to the form button
        String delete = request.getParameter("delete");
        String edit = request.getParameter("edit");
        String login = request.getParameter("sign_in");
        String add = request.getParameter("add");

        // If user has click on the button on form "add" => redirection to the login page
        if (add != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }

        // If the url countains the user id => data recovery + redirection to the modification form
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

        // If he just clicked on the link to see the list => loading the list
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

        // Liaison entre get et post => necessary
        doGet(request, response);

        // Corresponds to the form button
        String add = request.getParameter("add");
        String delete = request.getParameter("delete");
        String edit = request.getParameter("edit");
        String login = request.getParameter("sign_in");

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
                // Redirect to the login page
                response.sendRedirect("login.jsp");
            } catch (SQLException e) {
                e.getMessage();
            }
            /**
             * If delete is submitted => calls the method of deleting a user
             * reloading the user list
             */
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
            /**
             * Retrieves data from fields and url Define the fields in the
             * Setter Tries to modify a user and then reloads the list
             */
        } else if (edit != null) {
            int id_url = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String sex = request.getParameter("sex");
            String country = request.getParameter("country");

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
            /**
             * If the user has submitted the login form and it is correct =>
             * redirection to the user list
             */
        } else if (login != null) {
            // Data from fields
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            try {
                // Retrieves data user's via email
                users = users_crud.getLogin(email);
                // If the email exist and the password matches => session + redirection with list
                if (users.getEmail() != null && users.getPassword() != null && users.getPassword().equals(password) && email != null && password != null) {
                  
                    // Session
                    HttpSession session_username = request.getSession();
                    String username = users.getName();
                    session_username.setAttribute("session_username", username);
                    // Reloading to the user list
                    List<Users> list = users_crud.read();
                    request.setAttribute("list", list);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/viewusers.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

}
