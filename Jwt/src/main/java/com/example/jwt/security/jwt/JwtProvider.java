package com.example.jwt.security.jwt;

import com.example.jwt.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

// tao ra toker
@Component
public class JwtProvider {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(JwtProvider.class);
    // tao cay
    private String jwtSecret = "huynh280119999";
    // thoi gian  song
    private int jwtExpiration = 86400;
    public String createToken(Authentication authentication){
        UserPrinciple userPrinciple =(UserPrinciple) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token){
        try{
           Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token);
        }catch(SignatureException e){
            logger.error("Invalid JWT signature -> Massage: {",e);
        }catch(MalformedJwtException e){
            logger.error("Invalid format token -> Massage: {",e);
        }catch(ExpiredJwtException e){
            logger.error("Expired JWT signature -> Massage: {",e);
        }catch(UnsupportedJwtException e){
            logger.error("Unsupported  JWT token -> Massage: {",e);
        }catch (IllegalArgumentException e){
            logger.error("JWTG claims string is empty -> Massage {}",e);
        }
        return  false;
    }
    // ham tim nguoc lai token
    public String getUseNameFromToken(String token){
        String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }
}
