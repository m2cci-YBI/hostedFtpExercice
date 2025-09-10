package util;

import config.ConfigurationProperties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public final class JwtUtil {
    private JwtUtil() {}

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(ConfigurationProperties.getJwtSecret());
    private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM).withIssuer("auth0").build();

    public static String generateToken(int userId, String username) {
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("userId", userId)
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + ConfigurationProperties.getJwtExpirationMs()))
                .sign(ALGORITHM);
    }

    public static DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return VERIFIER.verify(token);
    }
}
