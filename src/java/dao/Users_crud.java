package dao;

import model.Database;
import model.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * This class inherits the database class Contains the crud from the Users table
 *
 * @author 80010-37-15
 */
public class Users_crud extends Database {

    Users users;

    public Users_crud() throws SQLException {
        users = new Users();
    }

    /**
     * Create a user
     *
     * @param users
     * @throws SQLException
     */
    public void create(Users users) throws SQLException {
        String sql = "INSERT INTO users (name, password, email, sex, country) VALUES (?, ?, ?, ?, ?)";
        Connection con = (Connection) Database.getConnection();
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
        ps.setString(1, users.getName());
        ps.setString(2, users.getPassword());
        ps.setString(3, users.getEmail());
        ps.setString(4, users.getSex());
        ps.setString(5, users.getCountry());
        ps.execute();
    }

    /**
     * Return a user list
     *
     * @return
     * @throws SQLException
     */
    public List<Users> read() throws SQLException {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Connection con = (Connection) Database.getConnection();
        Statement stm = (Statement) con.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {
            Users user = new Users();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setSex(rs.getString("sex"));
            user.setCountry(rs.getString("country"));
            list.add(user);
        }
        return list;

    }

    /**
     * Find a user
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Users readById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection con = (Connection) Database.getConnection();
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            users.setId(rs.getInt("id"));
            users.setName(rs.getString("name"));
            users.setPassword(rs.getString("password"));
            users.setEmail(rs.getString("email"));
            users.setSex(rs.getString("sex"));
            users.setCountry(rs.getString("country"));
        }
        return users;
    }

    /**
     * Retrieves a user's password via email
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public Users getLogin(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        Connection con = (Connection) Database.getConnection();
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            users.setId(rs.getInt("id"));
            users.setName(rs.getString("name"));
            users.setPassword(rs.getString("password"));
            users.setEmail(rs.getString("email"));
            users.setSex(rs.getString("sex"));
            users.setCountry(rs.getString("country"));
        }
        return users;
    }

    /**
     * Update a user
     *
     * @param users
     * @throws SQLException
     */
    public void update(Users users) throws SQLException {
        String sql = "UPDATE users SET name = ?, password = ?, email = ?, sex = ?, country = ? WHERE id = ?";
        Connection con = (Connection) Database.getConnection();
        PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
        ps.setString(1, users.getName());
        ps.setString(2, users.getPassword());
        ps.setString(3, users.getEmail());
        ps.setString(4, users.getSex());
        ps.setString(5, users.getCountry());
        ps.setInt(6, users.getId());
        ps.execute();
    }

    /**
     * Delete a user
     *
     * @param id
     */
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        Connection con = (Connection) Database.getConnection();
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.getMessage();
        }

    }

}
