package SpringSecurityJwt.service;

import SpringSecurityJwt.dto.DtoAuthRespuesta;
import SpringSecurityJwt.dto.DtoLogin;
import SpringSecurityJwt.mapper.Mapper;
import SpringSecurityJwt.model.Roles;
import SpringSecurityJwt.model.Usuarios;
import SpringSecurityJwt.repository.IRolesRepository;
import SpringSecurityJwt.repository.IUsuarioRepository;
import SpringSecurityJwt.security.JwtTokenProvider;
import SpringSecurityJwt.utils.Fecha;
import SpringSecurityJwt.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRolesRepository rolesRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public List<Usuarios> listar(){
        return usuarioRepository.findAll();
    }
    @Override
    public String saveUser(Usuarios user, String typeUser) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Roles roles = rolesRepository.findByName(typeUser).get();
        user.setRoles(Collections.singletonList(roles));

        Fecha created = new Fecha();

        user.setCreated(created.GetNow());
        user.setIsactive(true);
        try {
            usuarioRepository.save(user);
        }catch(Exception e){
            return "Error al guardar los datos " + e.getMessage();
        }
        return "Usuario guardado con exito";
    }

    @Override
    public DtoAuthRespuesta login(DtoLogin dtoLogin) {
        DtoAuthRespuesta dtoAuthRespuesta = new DtoAuthRespuesta();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dtoLogin.getUsername(), dtoLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generarToken(authentication);
            Usuarios userUpdate = new Usuarios();
            userUpdate = usuarioRepository.findByUsername(dtoLogin.getUsername());
            userUpdate.setToken(token);
            Fecha created = new Fecha();
            userUpdate.setLastLogin(created.GetNow());
            usuarioRepository.save(userUpdate);

            Usuarios creado = usuarioRepository.findByUsername(userUpdate.getUsername());
            Mapper myMapper = new Mapper();
            dtoAuthRespuesta = myMapper.userToDto(creado);

        }catch(Exception e){

            return dtoAuthRespuesta;
        }
        return dtoAuthRespuesta;
    }
}
