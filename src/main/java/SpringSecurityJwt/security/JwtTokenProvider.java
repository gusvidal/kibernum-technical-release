package SpringSecurityJwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    // metodo para generar el token por medio de autenticacion
    public String generarToken(Authentication authentication){
        String userName = authentication.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);

        // Linea para generar el token
        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiracionToken)
                .signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.JWT_FIRMA)
                .compact();
        return token;
    }

    // metodo para extraer un username a partir de un token
    public String obtenerUsernameDeJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(ConstantesSeguridad.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // metodo para validar el token
    public Boolean validarToken(String token){
        try{
            Jwts.parser().setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("Jwt ha expirado o está incorrecto!!");
        }
    }
}
