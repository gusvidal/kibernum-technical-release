package SpringSecurityJwt.security;

import SpringSecurityJwt.model.Roles;
import SpringSecurityJwt.model.Usuarios;
import SpringSecurityJwt.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUsersDetailsService implements UserDetailsService {
    private IUsuarioRepository iUsuarioRepo;


    // inyeccion de dependencias a traves del constructor de la clase
    @Autowired
    public CustomUsersDetailsService(IUsuarioRepository iUsuarioRepo) {
        this.iUsuarioRepo = iUsuarioRepo;
    }

    public Collection<GrantedAuthority> mapToAuthotiries(List<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = iUsuarioRepo.findByUsername(username);//.orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuarios.getUsername(), usuarios.getPassword(), mapToAuthotiries(usuarios.getRoles()));
    }
}
