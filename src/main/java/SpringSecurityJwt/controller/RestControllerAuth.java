package SpringSecurityJwt.controller;

import SpringSecurityJwt.dto.DtoAuthRespuesta;
import SpringSecurityJwt.dto.DtoLogin;
import SpringSecurityJwt.model.Usuarios;
import SpringSecurityJwt.repository.IRolesRepository;
import SpringSecurityJwt.repository.IUsuarioRepository;
import SpringSecurityJwt.security.JwtTokenProvider;
import SpringSecurityJwt.service.IUserService;
import SpringSecurityJwt.utils.EmailValidator;
import SpringSecurityJwt.utils.PassValidator;
import SpringSecurityJwt.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/auth/")
@Tag(name = "Usuarios", description = "Endpoints para la creación y autenticación de usuarios")
public class RestControllerAuth {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolesRepository rolesRepository;
    private IUsuarioRepository usuarioRepository;
    private JwtTokenProvider jwtTokenProvider; // tambien se llama JwtGenerator

    @Autowired
    private IUserService iUserService;

    private final Logger LOGGER = LoggerFactory.getLogger(RestControllerAuth.class);
    @Value("${regex.email}")
    private String regexEmail;

    @Value("${regex.password}")
    private String regexPassword;

    final String AUTHORITY_USER = "USER";
    final String AUTHORITY_ADMIN = "ADMIN";
    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository rolesRepository, IUsuarioRepository usuarioRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usuarioRepository = usuarioRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("registerUser")
    public ResponseEntity<Response> registrarUser(@RequestBody Usuarios usuario){
        LOGGER.info("[Begin :: controller :: registrarUser()]");
        Response resp = new Response();
        if(usuarioRepository.existsByUsername(usuario.getUsername())){
            resp.response = "El usuario ya existe";
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            resp.response = "El correo ya se encuentra registrado";
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.validaEmail(usuario.getEmail(), regexEmail)){
            resp.response = "Email invalido";
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }

        PassValidator passValidator = new PassValidator();
        if (!passValidator.validaPassword(usuario.getPassword(), regexPassword)){
            resp.response = "El password debe contener al menos una letra mayuscula, numeros y caracteres alfanumericos";
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("[Validaciones :: ok]");
        resp.response = iUserService.saveUser(usuario,this.AUTHORITY_USER);
        LOGGER.info("[DB save :: ok()]");
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }

    @PostMapping("registerAdmin")
    public ResponseEntity<Response> registerAdmin(@RequestBody Usuarios usuario){
        LOGGER.info("[Begin :: controller :: registerAdmin()]");
        Response resp = new Response();
        if(usuarioRepository.existsByUsername(usuario.getUsername())){
            resp.response = "El usuario ya existe";
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            resp.response = "El correo ya se encuentra registrado";
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.validaEmail(usuario.getEmail(), regexEmail)){
            resp.response = "Email invalido";
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }

        PassValidator passValidator = new PassValidator();
        if (!passValidator.validaPassword(usuario.getPassword(), regexPassword)){
            resp.response = "El password debe contener al menos una letra mayuscula, numeros y caracteres alfanumericos";
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("[Validaciones :: ok]");
        resp.response = iUserService.saveUser(usuario,this.AUTHORITY_ADMIN);
        LOGGER.info("[DB save :: ok()]");
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody DtoLogin dtoLogin){
        Response resp = new Response();
        DtoAuthRespuesta responseEntity = new DtoAuthRespuesta();
        try {
            responseEntity = iUserService.login(dtoLogin);
            if (responseEntity.getId() == null){
                resp.response = "Error en proceso de login, favor revise sus credenciales. ";

                return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            resp.response="Error en controlador de logeo " + e.getMessage();
            return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<DtoAuthRespuesta>(responseEntity, HttpStatus.OK);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> getTodos() {
        List<Usuarios> lista = new ArrayList<>();
        Response resp = new Response();
        try {
            lista = iUserService.listar();
        } catch (Exception e) {
            resp.response="Sin existencias";
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Usuarios>>(lista, HttpStatus.OK);
    }
}