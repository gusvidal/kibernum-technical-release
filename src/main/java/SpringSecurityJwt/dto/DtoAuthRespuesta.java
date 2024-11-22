package SpringSecurityJwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Devuelve la información con el token y el tipo de token
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoAuthRespuesta {
    private Long id;
    private String created;
    private String modified;
    private String lastLogin;
    private String accessToken;
    private boolean isActive;
}
