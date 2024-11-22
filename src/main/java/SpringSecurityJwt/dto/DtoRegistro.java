package SpringSecurityJwt.dto;

import SpringSecurityJwt.model.Phones;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class DtoRegistro {
    private String username;
    private String password;
    private String email;
    private String created;
    private String modified;
    private Boolean isactive;
    private Date token;
    private Set<Phones> phones;

}
