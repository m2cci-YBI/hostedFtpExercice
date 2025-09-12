package controller;

import dao.UserDao;
import dao.JdbcUserDao;
import model.User;
import util.PasswordUtil;
import util.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {
    private final UserDao userDao = new JdbcUserDao();
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        if (u != null) u = u.trim();
        if (p != null) p = p.trim();

        if (u == null || u.isEmpty() || p == null || p.isEmpty()) {
            req.setAttribute("message", "Username and password are required");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        Optional<User> userOpt = userDao.findByUsername(u);
        if (userOpt.isPresent() && PasswordUtil.checkPassword(p, userOpt.get().getPassword())) {
            LOGGER.log(Level.INFO, "Successful login for user={0}", u);
            String token = JwtUtil.generateToken(userOpt.get().getId(), userOpt.get().getUsername());

            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(req.isSecure());
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(60 * 60);
            resp.addCookie(jwtCookie);

            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/welcome"));
        } else {
            LOGGER.log(Level.WARNING, "Failed login attempt for user={0}", u);
            req.setAttribute("message", "Invalid credentials");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
