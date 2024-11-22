package SpringSecurityJwt.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// la funcion de esta clase será validar la informacion del token
// en caso de ser exitoso, estable la autentication de un usuario en la solicitud (en postman por ejemplo)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUsersDetailsService customUsersDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String obtenerTokenDesdeSolicitud(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = obtenerTokenDesdeSolicitud(request);
        if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)){
            String userName = jwtTokenProvider.obtenerUsernameDeJwt(token);
            UserDetails userDetails = customUsersDetailsService.loadUserByUsername(userName);
            List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if(userRoles.contains("USER") || userRoles.contains("ADMIN")){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
