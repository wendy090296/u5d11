package wendydeluca.u5d11.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wendydeluca.u5d11.entities.User;

import java.util.Date;
@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *24))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(this.secret.getBytes()
                ))
                .compact();
    }


    public void verifyToken(String token){}
}
