package dao;

import model.UserInfo;
import datasource.Database;
import exception.DataAccessException;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcUserInfoDao implements UserInfoDao {
    private static final Logger LOGGER = Logger.getLogger(JdbcUserInfoDao.class.getName());
    @Override
    public Optional<UserInfo> findByUserId(int userId) {
        String sql = "SELECT upi.first_name, upi.last_name, upf.company_name, upf.salary, upf.start_date " +
                     "FROM user_personal_info upi " +
                     "JOIN user_professional_info upf ON upi.user_id = upf.user_id " +
                     "WHERE upi.user_id = ?";
        try (Connection c = Database.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserInfo ui = new UserInfo();
                    ui.setUserId(userId);
                    ui.setFirstName(rs.getString("first_name"));
                    ui.setLastName(rs.getString("last_name"));
                    ui.setCompanyName(rs.getString("company_name"));
                    ui.setSalary(rs.getBigDecimal("salary"));
                    ui.setStartDate(rs.getDate("start_date"));
                    LOGGER.log(Level.INFO, "User personal/professional info found for userId={0}", userId);
                    return Optional.of(ui);
                }
                LOGGER.log(Level.INFO, "No personal/professional info found for userId={0}", userId);
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DB error in findByUserId", e);
            throw new DataAccessException("Unable to fetch user info", e);
        }
    }
}
