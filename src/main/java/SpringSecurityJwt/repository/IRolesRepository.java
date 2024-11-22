package SpringSecurityJwt.repository;

import SpringSecurityJwt.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String roleName);
}
