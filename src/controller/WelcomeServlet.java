package controller;

import dao.UserInfoDao;
import dao.JdbcUserInfoDao;
import model.UserInfo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WelcomeServlet extends HttpServlet {
    private final UserInfoDao userInfoDao = new JdbcUserInfoDao();
    private static final Logger LOGGER = Logger.getLogger(WelcomeServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userIdAttr = req.getAttribute("userId");
        Object usernameAttr = req.getAttribute("username");
        if (userIdAttr == null || usernameAttr == null) {
            LOGGER.log(Level.WARNING, "No authenticated user in request; redirecting to login");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int userId = (Integer) userIdAttr;
        String username = (String) usernameAttr;

        Optional<UserInfo> info = userInfoDao.findByUserId(userId);
        if (!info.isPresent()) {
            LOGGER.log(Level.WARNING, "No profile data found for userId={0}", userId);
            req.setAttribute("message", "No profile data found.");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("username", username);
        req.setAttribute("company", info.get().getCompanyName());
        req.setAttribute("salary", info.get().getSalary());
        req.setAttribute("startDate", info.get().getStartDate());

        req.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(req, resp);
    }
}
