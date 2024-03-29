package abhinav.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JwtUtils {

    // 1 year in milliseconds
    private static final long EXPIRATION_TIME_IN_MILLIS = 365L * 24 * 60 * 60 * 1000;

    private static final String ROLE_CLAIM_KEY = "role";

    private static final byte[] SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded();


    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }


    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }



    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE_CLAIM_KEY, role);
        return createToken(claims, username);
    }

    public String extractUserRole(String token) {
        return extractClaims(token, claims -> claims.get(ROLE_CLAIM_KEY, String.class));
    }


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }


    public Claims extractAllClaims(String token) {

        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

    }

}