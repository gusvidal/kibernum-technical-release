package SpringSecurityJwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuario")
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    private String username;
    private String password;
    private String email;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private boolean isactive;

    // EAGER: Cuando consultamos por un usuario, se trae todos los roles que tiene
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(
                    name = "usuario_id", referencedColumnName = "id_usuario"
            ),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role")
    )
    private List<Roles> roles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private List<Phones> phones = new ArrayList<>();
}
