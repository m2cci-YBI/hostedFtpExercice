package authentication;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import authentication.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authentication implements Filter {
    private static final Logger LOGGER = Logger.getLogger(Authentication.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getRequestURI().startsWith(req.getContextPath() + "/login") ||
            req.getRequestURI().startsWith(req.getContextPath() + "/logout")) {
            LOGGER.log(Level.INFO, "Bypassing JWT check for public path: {0}", req.getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        if (jwtToken == null) {
            LOGGER.log(Level.WARNING, "JWT cookie missing; redirecting to login");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            DecodedJWT decodedJWT = JwtUtil.verifyToken(jwtToken);
            Integer userId = decodedJWT.getClaim("userId").asInt();
            String username = decodedJWT.getClaim("username").asString();

            req.setAttribute("userId", userId);
            req.setAttribute("username", username);

            chain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            LOGGER.log(Level.WARNING, "Invalid/expired JWT; redirecting to login", e);
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}
