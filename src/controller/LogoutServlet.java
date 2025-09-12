package controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogoutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie jwtCookie = new Cookie("jwt", "");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(req.isSecure());
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        resp.addCookie(jwtCookie);

        LOGGER.log(Level.INFO, "User logged out and JWT cookie cleared");
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
