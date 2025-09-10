package dao;

import model.User;
import datasource.Database;
import exception.DataAccessException;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcUserDao implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcUserDao.class.getName());
    @Override
    public Optional<User> findByUsername(String u) {
        String sql = "SELECT id, username, password FROM user_login WHERE username = ?";
        try (Connection c = Database.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    LOGGER.log(Level.INFO, "User found by username: {0}", u);
                    return Optional.of(user);
                }
                LOGGER.log(Level.INFO, "No user found by username: {0}", u);
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DB error in findByUsername", e);
            throw new DataAccessException("Unable to fetch user by username", e);
        }
    }

    
}
