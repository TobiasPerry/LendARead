package ar.edu.itba.paw.webapp.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private final SecretKey jwtSecret ;

    private final int JWT_VALID_PIRIOD =  24 * 60 * 60 *  1000;
    private final int JWT_REFRESH_VALID_PIRIOD = 7 * 24 * 60 * 60 *  1000;

    public static final String JWT_HEADER = "X-JWT";
    public static final String JWT_REFRESH_HEADER = "X-Refresh-Token";

    @Autowired
    public JwtTokenUtil(@Value("classpath:jwt.key") Resource jwtKeyResource) throws IOException {
        this.jwtSecret = Keys.hmacShaKeyFor(
                FileCopyUtils.copyToString(new InputStreamReader(jwtKeyResource.getInputStream()))
                        .getBytes(StandardCharsets.UTF_8)
        );

    }
    public String generateJwtToken(Authentication authentication, String userReference) {
        PawUserDetails userPrincipal = (PawUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_VALID_PIRIOD))
                .claim("userReference", userReference)
                .claim("refresh", false)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(Authentication authentication,String userReference) {
        PawUserDetails userPrincipal = (PawUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_VALID_PIRIOD))
                .claim("userReference", userReference)
                .claim("refresh", true)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return jwtSecret;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    public boolean isRefreshToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().get("refresh", Boolean.class);
    }

    public boolean validateJwtToken(String authToken) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken).getBody();
        return claims.get("refresh", Boolean.class) != null && !(new Date(System.currentTimeMillis()).after(claims.getExpiration()));
    }
    public static String getBaseUrl(HttpServletRequest request){
        return  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
