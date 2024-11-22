package SpringSecurityJwt.service;

import SpringSecurityJwt.dto.DtoAuthRespuesta;
import SpringSecurityJwt.dto.DtoLogin;
import SpringSecurityJwt.model.Usuarios;

import java.util.List;

public interface IUserService {
    public List<Usuarios> listar();
    public String saveUser(Usuarios user, String typeUser);
    public DtoAuthRespuesta login(DtoLogin dtoLogin);
}
